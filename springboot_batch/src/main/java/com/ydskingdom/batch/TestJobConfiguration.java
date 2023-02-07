package com.ydskingdom.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job simpleJob1() {
        log.info("========== START simpleJob");

        return jobBuilderFactory.get("testJob")	//Batch Job을 생성하고 builder를 통해 이름 지정
                .incrementer(new RunIdIncrementer())
                .start(simpleStep1())				//execute 할 step or sequence of steps
                .build();
    };



    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")			//Batch Step을 생성하고 builder를 통해 이름 지정
                .tasklet((contribution, chunkContext) -> {		//Step 안에서 수행될 기능을 명시
                    for(int i = 0; i < 100 ; i ++) {			//	- Tasklet은 Step 안에서 단일로 수행될 커스텀 기능 선언
                        log.info(">>>>>  "+(i+1)+"  <<<<<");
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    };
}
