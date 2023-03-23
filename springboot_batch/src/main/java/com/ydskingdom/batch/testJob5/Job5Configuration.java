package com.ydskingdom.batch.testJob5;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

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
                .processor(custom1ItemProcessor())
//                .processor(new Job5ItemProcessor())
                .writer(new Job5ItemWriter())
                .build();
    }

    public ItemProcessor<? super String, ? extends String> custom1ItemProcessor() {

        // ClassifierCompositeItemProcessor Builder 클래스 생성
        ClassifierCompositeItemProcessorBuilder<String, String> builder = new ClassifierCompositeItemProcessorBuilder<>();

        // Builder 클래스에 전달한 Classifier 클래스 생성
        ProcessorClassifier<? super String, ItemProcessor<?, ? extends String>> classifier2 = new ProcessorClassifier<>();

        //Classifier 내부에 가지고 있는 ParamMap(구분자 생성 및 초기화)
        HashMap<Integer, ItemProcessor<String, String>> paramMap = new HashMap<>();
        paramMap.put(0, new CustomItemProcessor1());
        paramMap.put(1, new CustomItemProcessor2());

        //Classifier 셋팅
        classifier2.setParamMap(paramMap);

        // Builder를 통해서 Processor 생성
        return builder.classifier(classifier2).build();
    }
}
