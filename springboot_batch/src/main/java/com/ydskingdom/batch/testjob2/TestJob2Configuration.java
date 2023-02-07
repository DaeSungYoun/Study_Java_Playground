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
    public Job testJob2() {
        System.out.println("testJob2 bean create");
        return jobBuilderFactory.get("testJob2")
                .start(testJob2_Step1())
                .next(testJob2_Step2())
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
    public Step testJob2_Step1() {
        System.out.println("testJob2_Step1 bean create");
        return stepBuilderFactory.get("testJob2_Step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("testJob2_step1 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step testJob2_Step2() {
        System.out.println("testJob2_Step2 bean create");
        return stepBuilderFactory.get("testJob2_Step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("testJob2_Step2 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
