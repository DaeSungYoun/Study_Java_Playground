package com.ydskingdom.batch.testjob3.job.file;

import com.ydskingdom.batch.testjob3.chunk.processor.FileItemProcessor;
import com.ydskingdom.batch.testjob3.domain.Product;
import com.ydskingdom.batch.testjob3.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        System.out.println("job");
        return jobBuilderFactory.get("fileJob")
                .start(fileStep1())
                .build();
    }

    @Bean
    public Step fileStep1() {
        System.out.println("fileStep");
        return stepBuilderFactory.get("fileStep1")
                .<ProductVO, Product>chunk(10)
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        System.out.println("fileItemReader");
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate +".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("id","name","price","type")
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        System.out.println("fileItemProcessor");
        return new FileItemProcessor();
    }

    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        System.out.println("fileItemWriter");
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
