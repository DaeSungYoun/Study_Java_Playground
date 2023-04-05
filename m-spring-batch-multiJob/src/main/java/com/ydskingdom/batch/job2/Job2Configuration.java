package com.ydskingdom.batch.job2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Job2Configuration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Bean
    public Job job2() throws Exception {
        System.out.println("job2 bean create");
        return jobBuilderFactory.get("job2")
                .incrementer(new RunIdIncrementer())
                .repository(job2Repository())
                .start(job2_step())
                .build();
    }

    @Bean
    public Step job2_step() {
        System.out.println("job2_step bean create");
        return stepBuilderFactory.get("job2_step")
                .tasklet((contribution, chunkContext) -> {
                    for (int i = 0; i < 100; i++) {
                        log.info(">>>>>  " + (i + 1) + "  <<<<<");
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public JobRepository job2Repository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_bbb");
        return factory.getObject();
    }
}
