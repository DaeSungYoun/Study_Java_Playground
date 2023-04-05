package com.ydskingdom.batch.job1;

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
public class Job1Configuration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Bean
    public Job job1() throws Exception {
        System.out.println("job1 bean create");
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .repository(job1Repository())
                .start(job1_step())
                .build();
    }

    @Bean
    public Step job1_step() {
        System.out.println("job1_step bean create");
        return stepBuilderFactory.get("job1_step")            //Batch Step을 생성하고 builder를 통해 이름 지정
                .tasklet((contribution, chunkContext) -> {        //Step 안에서 수행될 기능을 명시
                    for (int i = 0; i < 100; i++) {            //	- Tasklet은 Step 안에서 단일로 수행될 커스텀 기능 선언
                        log.info(">>>>>  " + (i + 1) + "  <<<<<");
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public JobRepository job1Repository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_aaa");
        return factory.getObject();
    }
}
