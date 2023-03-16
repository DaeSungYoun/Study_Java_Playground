//https://ojt90902.tistory.com/802
package com.ydskingdom.batch.testJob4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SkipStepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job skipJob() {
        return jobBuilderFactory.get("skipJob")
                .incrementer(new RunIdIncrementer())
                .start(skipStep1())
                .build();
    }

    @Bean
    public Step skipStep1() {
        return stepBuilderFactory.get("skipStep1")
                .<String, String>chunk(5)
                .reader(customReader())
                .processor(customProcessor())
                .writer(customWriter())
                .faultTolerant()
                .skip(SkipException1.class)
                .noSkip(noSkipException.class)
                .skipLimit(3)
                .build();
    }

    private ItemReader<String> customReader() {
        return new ItemReader<>() {
            int i = 0;

            @Override
            public String read() throws Exception {
                i++;
                System.out.println("itemReader = item" + i);
                if ((i == 6) || (i == 7)) {
                    System.out.println("itemReader Exception = item" + i);
                    throw new SkipException1();
                } else {
                    return i > 20 ? null : "item" + i;
                }
            }
        };
    }

    private ItemProcessor<String, String> customProcessor() {
        return item -> {
            if (item.equals("item3")) {
                System.out.println("itemProcessor Exception = " + item);
                throw new SkipException1();
            } else {
                System.out.println("itemProcessor Completed = " + item);
                return item;
            }
        };
    }

    private ItemWriter<String> customWriter() {
        return items -> {
            for (String item : items) {
                if (item.equals("item12") || item.equals("item13")) {
                    System.out.println("itemWriter Exception = " + item);
                    throw new SkipException1();
                } else {
                    System.out.println("itemWriter Completed = " + item);
                }
            }
        };
    }
}
