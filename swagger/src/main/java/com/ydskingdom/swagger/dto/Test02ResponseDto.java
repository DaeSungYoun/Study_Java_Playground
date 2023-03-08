package com.ydskingdom.swagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Test02 Response")
@Builder
@Setter
@Getter
public class Test02ResponseDto {

    private String aaaa;
    private String bbbb;
    private int cccc;
    private boolean dddd;
    private long eeee;
}
