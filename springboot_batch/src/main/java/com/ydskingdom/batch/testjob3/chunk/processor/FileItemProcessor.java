package com.ydskingdom.batch.testjob3.chunk.processor;

import com.ydskingdom.batch.testjob3.domain.Product;
import com.ydskingdom.batch.testjob3.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(item, Product.class);


        return product;
    }
}
