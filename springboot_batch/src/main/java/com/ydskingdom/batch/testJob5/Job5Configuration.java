package com.ydskingdom.batch.testJob5;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Job5Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final String JOB_NAME = "testJob5";

    @Bean
    public Job job5() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(job5_step1())
                .build();
    }

    @Bean
    public Step job5_step1() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<String, String>chunk(10)
                .reader(new Job5ItemReader())
                .processor(new Job5ItemProcessor())
                .writer(new Job5ItemWriter())
                .build();
    }
}
