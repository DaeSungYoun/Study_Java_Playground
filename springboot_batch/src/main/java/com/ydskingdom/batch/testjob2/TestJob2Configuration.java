package com.ydskingdom.batch.testjob2;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TestJob2Configuration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        System.out.println("Job");
        return jobBuilderFactory.get("testJob2")
                .start(step1())
                .next(step2())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("before Job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("after Job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1() {
        System.out.println("step1");
        return stepBuilderFactory.get("testStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hi");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        System.out.println("step2");
        return stepBuilderFactory.get("testStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hello");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
