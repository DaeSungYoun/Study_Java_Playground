package com.ydskingdom.batch.job3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Job3Configuration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job3() {
        System.out.println("job3 bean create");
        return jobBuilderFactory.get("job3")
                .incrementer(new RunIdIncrementer())
                .start(job3_step())
                .build();
    }

    @Bean
    public Step job3_step() {
        System.out.println("job3_step bean create");
        return stepBuilderFactory.get("job3_step")
                .tasklet((contribution, chunkContext) -> {
                    for (int i = 0; i < 100; i++) {
                        log.info(">>>>>  " + (i + 1) + "  <<<<<");
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
