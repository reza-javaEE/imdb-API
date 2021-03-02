package com.vodafone.imdbdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created By : rezaee_r
 * Date : 2/25/2021
 **/

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private JobLauncher jobLauncher;

    private Job readTitleBasicsJob;

    private Job readNameBasicsJob;

    private Job readTitlePrincipalsJob;

    public SchedulerConfig(JobLauncher jobLauncher, Job readTitleBasicsJob, Job readNameBasicsJob, Job readTitlePrincipalsJob) {
        this.jobLauncher = jobLauncher;
        this.readTitleBasicsJob = readTitleBasicsJob;
        this.readNameBasicsJob = readNameBasicsJob;
        this.readTitlePrincipalsJob = readTitlePrincipalsJob;
    }

    private static final Logger logger = LoggerFactory.getLogger("SchedulerConfig");

    /**
     * The task will be executed the first time after the initialDelay value
     */
    @Scheduled(fixedDelayString = "${fixedDelay.titleBasic}", initialDelayString = "${initialDelay.titleBasic}")
    public void performTitleBasicsJob() throws Exception {
        runJob(readTitleBasicsJob);
    }

    /**
     * fixedDelay specifically controls the next execution time when the last execution finishes.
     */
    @Scheduled(fixedDelayString = "${fixedDelay.nameBasic}", initialDelayString = "${initialDelay.nameBasic}")
    public void performNameBasicsJob() throws Exception {
        runJob(readNameBasicsJob);
    }


    @Scheduled(fixedDelayString = "${fixedDelay.titlePrincipals}", initialDelayString = "${initialDelay.titlePrincipals}")
    public void performTitlePrincipalsJob() throws Exception {
        runJob(readTitlePrincipalsJob);
    }


    public void runJob(Job job) throws Exception {


        LocalDateTime start = LocalDateTime.now();
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        logger.info(job.getName() + " completed in " + duration.toMillis() + " Millis ");
    }


    /*@Bean //todo ASyncTaskExecutor to run jobs parallel
    public JobLauncher jobLauncher() throws Exception
    {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();

        return jobLauncher;
    }*/


}