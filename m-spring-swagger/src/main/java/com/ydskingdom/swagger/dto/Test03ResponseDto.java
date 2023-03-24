package com.ydskingdom.swagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Test03 Response")
@Builder
@Setter
@Getter
public class Test03ResponseDto {
    private String a;
    private String b;
    private int c;
}
