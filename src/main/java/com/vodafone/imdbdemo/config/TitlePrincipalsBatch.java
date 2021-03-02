package com.vodafone.imdbdemo.config;

import com.vodafone.imdbdemo.model.TitlePrincipalsModel;
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
 * Date : 2/27/2021
 **/

@Configuration
public class TitlePrincipalsBatch {

    private final BatchService batchService;

    public TitlePrincipalsBatch(BatchService batchService) {
        this.batchService = batchService;
    }

    @Value("${titleprincipals.file.path}")  //todo  unzip files dynamically
    private Resource inputResource;

    @Bean
    public Job readTitlePrincipalsJob() {
        return batchService.jobBuilderFactory
                .get("readTitlePrincipalsJob")
                .incrementer(new RunIdIncrementer())
                .start(stepTP())
                .build();
    }

    @Bean
    public SkipPolicy fileVerificationSkipperTP() {
        return new FileVerificationSkipper();
    }

    @Bean
    public TaskExecutor taskExecutorTP() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(5);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded3");
        return executor;
    }

    @Bean
    public Step stepTP() {
        return batchService.stepBuilderFactory
                .get("stepTP")
                .<TitlePrincipalsModel, TitlePrincipalsModel>chunk(30000)
                .reader(readerTP())
                .faultTolerant().skipPolicy(fileVerificationSkipperTP())
                .processor(processorTP())
                .writer(writerTP())
                .taskExecutor(taskExecutorTP())
                .build();
    }

    @Bean
    public ItemProcessor<TitlePrincipalsModel, TitlePrincipalsModel> processorTP() {
        return new TitlePrincipalsProcessor();
    }

    @Bean
    public FlatFileItemReader<TitlePrincipalsModel> readerTP() {
        FlatFileItemReader<TitlePrincipalsModel> itemReader = new FlatFileItemReader<TitlePrincipalsModel>();
        itemReader.setLineMapper(lineMapperTP());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<TitlePrincipalsModel> lineMapperTP() {
        DefaultLineMapper<TitlePrincipalsModel> lineMapper = new DefaultLineMapper<TitlePrincipalsModel>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
        lineTokenizer.setNames("tconst", "ordering", "nconst", "category", "job", "characters");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5);
        BeanWrapperFieldSetMapper<TitlePrincipalsModel> fieldSetMapper = new BeanWrapperFieldSetMapper<TitlePrincipalsModel>();
        fieldSetMapper.setTargetType(TitlePrincipalsModel.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<TitlePrincipalsModel> writerTP() {
        JdbcBatchItemWriter<TitlePrincipalsModel> itemWriter = new JdbcBatchItemWriter<TitlePrincipalsModel>();
        try {
            itemWriter.setDataSource(batchService.dataSource.getDataSource());
            itemWriter.setSql("INSERT INTO `titleprincipals` VALUES(:ordering,:tconst,:category,:characters,:job,:nconst)");
            itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TitlePrincipalsModel>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemWriter;
    }

}
