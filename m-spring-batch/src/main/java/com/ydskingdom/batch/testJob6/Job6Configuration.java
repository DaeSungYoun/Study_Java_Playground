package com.ydskingdom.batch.testJob6;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@RequiredArgsConstructor
@Configuration
public class Job6Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final String JOB_NAME = "testJob6";

    @Bean
    public Job job6() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(job6_step1())
                .build();
    }

    @Bean
    public Step job6_step1() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<String, String>chunk(10)
                .reader(new Job6ItemReader())
                .processor(job6ItemProcessor())
                .writer(new Job6ItemWriter())
                .build();
    }

    @Bean
    public AsyncItemProcessor job6ItemProcessor() {
        AsyncItemProcessor<String, String> asyncItemProcessor = new AsyncItemProcessor();

        asyncItemProcessor.setDelegate(new Job6ItemProcessor());
        TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        asyncItemProcessor.setTaskExecutor(taskExecutor);

        return asyncItemProcessor;
    }
}
