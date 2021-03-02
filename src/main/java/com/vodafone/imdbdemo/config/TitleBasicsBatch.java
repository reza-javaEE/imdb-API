package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.TitleBasics;
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
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

@Configuration
public class TitleBasicsBatch {

    private final BatchService batchService;

    public TitleBasicsBatch(BatchService batchService) {
        this.batchService = batchService;
    }

    @Value("${titlebasics.file.path}")  //todo  unzip files dynamically
    private Resource inputResource;


    @Bean
    public Job readTitleBasicsJob() {
        return batchService.jobBuilderFactory
                .get("readTitleBasicsJob")
                .incrementer(new RunIdIncrementer())
                .start(stepTB())
                .build();
    }

    @Bean
    public Step stepTB() {
        return batchService.stepBuilderFactory
                .get("stepTB")
                .<TitleBasics, TitleBasics>chunk(20000)
                .reader(readerTB())
                .faultTolerant().skipPolicy(fileVerificationSkipperTB())
                .processor(processorTB())
                .writer(writerTB())
                .taskExecutor(taskExecutorTB())
                .build();
    }

    @Bean
    public SkipPolicy fileVerificationSkipperTB() {
        return new FileVerificationSkipper();
    }

    @Bean
    public TaskExecutor taskExecutorTB() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(4);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded1");
        return executor;
    }

    @Bean
    public ItemProcessor<TitleBasics, TitleBasics> processorTB() {
        return new TitleBasicsProcessor();
    }

    @Bean
    public FlatFileItemReader<TitleBasics> readerTB() {
        FlatFileItemReader<TitleBasics> itemReader = new FlatFileItemReader<TitleBasics>();
        itemReader.setLineMapper(lineMapperTB());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<TitleBasics> lineMapperTB() {
        DefaultLineMapper<TitleBasics> lineMapper = new DefaultLineMapper<TitleBasics>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
        lineTokenizer.setNames("tconst", "titleType", "primaryTitle", "originalTitle", "isAdult", "startYear", "endYear", "runtimeMinutes", "genres");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7, 8);
        BeanWrapperFieldSetMapper<TitleBasics> fieldSetMapper = new BeanWrapperFieldSetMapper<TitleBasics>();
        fieldSetMapper.setTargetType(TitleBasics.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<TitleBasics> writerTB() {
        JdbcBatchItemWriter<TitleBasics> itemWriter = new JdbcBatchItemWriter<TitleBasics>();
        itemWriter.setDataSource(batchService.dataSource.getDataSource());
        itemWriter.setSql("INSERT INTO `titlebasics` VALUES(:tconst,:endYear,:genres,:isAdult,:originalTitle,:primaryTitle,:runtimeMinutes,:startYear,:titleType)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TitleBasics>());
        return itemWriter;
    }

}
