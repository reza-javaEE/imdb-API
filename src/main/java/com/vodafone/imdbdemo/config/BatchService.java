package com.vodafone.imdbdemo.config;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created By : rezaee_r
 * Date : 2/27/2021
 **/

@Configuration
@EnableBatchProcessing
public class BatchService {

    final JobBuilderFactory jobBuilderFactory;

    final StepBuilderFactory stepBuilderFactory;

    final DataSourceConfig dataSource;


    public BatchService(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSourceConfig dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }
}
