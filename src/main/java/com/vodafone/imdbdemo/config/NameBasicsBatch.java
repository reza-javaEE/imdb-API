package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.NameBasics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * *     Using spring batch to read data fron TSV files, process and log them and write datas to DataBase.
 * Created By : rezaee_r
 * Date : 2/27/2021
 **/

@Configuration
public class NameBasicsBatch {

    private final BatchService batchService;

    public NameBasicsBatch(BatchService batchService) {
        this.batchService = batchService;
    }

    @Value("${namebasics.file.path}") //todo  unzip files dynamically
    private Resource inputResource;

    private static final Logger logger = LoggerFactory.getLogger("NameBasicsBatch");

    @Bean
    public Job readNameBasicsJob() {
        return batchService.jobBuilderFactory
                .get("readNameBasicsJob")
                .incrementer(new RunIdIncrementer())
                .start(stepNB())
                .build();
    }

    @Bean
    public Step stepNB() {
        return batchService.stepBuilderFactory
                .get("stepNB")
                .<NameBasics, NameBasics>chunk(10000)
                .reader(readerNB())
                .faultTolerant().skipPolicy(fileVerificationSkipperNB())
                .processor(processorNB())
                .writer(writerNB())
                .taskExecutor(taskExecutorNB())
                .build();
    }

    @Bean
    public SkipPolicy fileVerificationSkipperNB() {
        return new FileVerificationSkipper();
    }

    @Bean
    public TaskExecutor taskExecutorNB() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        logger.info(" taskExecutorNB() run .....");
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded2");
        return executor;
    }

    /**
     * <p> processor() method creates an instance of the NameBasicsProcessor that we defined earlier
     * </p>
     *
     * @return the new instance of NameBasicsProcessor to process data
     */
    @Bean
    public ItemProcessor<NameBasics, NameBasics> processorNB() { // todo - create a GeneralLogger ( create a parent model to pass)
        return new NameBasicsProcessor();
    }

    /*
     * <p> We use readerNB for reading the TSV file.
     * we will use it’s standard configuration involving DefaultLineMapper, DelimitedLineTokenizer classes.
     * </p>
     * * @return ItemReader. It is the entity of a step (of a batch process) which reads data.
     *  An ItemReader reads one item a time.
     */
    @Bean
    public FlatFileItemReader<NameBasics> readerNB() {
        FlatFileItemReader<NameBasics> itemReader = new FlatFileItemReader<NameBasics>();
        logger.info(" FlatFileItemReader<NameBasics>  run .....");
        itemReader.setLineMapper(lineMapperNB());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }


    @Bean
    public LineMapper<NameBasics> lineMapperNB() {
        DefaultLineMapper<NameBasics> lineMapper = new DefaultLineMapper<NameBasics>();
        logger.info(" LineMapper<NameBasics>  run .....");
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
        lineTokenizer.setNames("nconst", "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5);
        BeanWrapperFieldSetMapper<NameBasics> fieldSetMapper = new BeanWrapperFieldSetMapper<NameBasics>();
        fieldSetMapper.setTargetType(NameBasics.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    /*
     * <p> We use writerNB For writing the records in DataBase,
     * we will use JdbcBatchItemWriter which is standard writer for executing batch queries
     * in database for Spring batch jobs.
     * </p>
     *  * @return ItemReader. It is the entity of a step (of a batch process) which reads data. An ItemReader reads one item a time.
     */
    @Bean
    public JdbcBatchItemWriter<NameBasics> writerNB() {
        JdbcBatchItemWriter<NameBasics> itemWriter = new JdbcBatchItemWriter<NameBasics>();
        logger.info(" JdbcBatchItemWriter<NameBasics>  run .....");
        itemWriter.setDataSource(batchService.dataSource.getDataSource());
        itemWriter.setSql("INSERT INTO `namebasics` (`nconst`, `birthYear`, `deathYear`, `knownForTitles`, `primaryName`, `primaryProfession`) VALUES(:nconst,:birthYear,:deathYear,:knownForTitles,:primaryName,:primaryProfession)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<NameBasics>());
        return itemWriter;
    }

}
