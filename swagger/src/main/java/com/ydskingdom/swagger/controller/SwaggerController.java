package com.ydskingdom.swagger.controller;

import com.ydskingdom.swagger.dto.RestResponse;
import com.ydskingdom.swagger.dto.Test01RequestDto;
import com.ydskingdom.swagger.dto.Test02ResponseDto;
import com.ydskingdom.swagger.dto.Test03ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/swagger")
@RestController
public class SwaggerController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "@ApiResponse description", content = @Content(schema = @Schema(implementation = Test02ResponseDto.class)))})
    @Operation(summary = "@Operation summary", description = "@Operation description")
    @GetMapping("/test02")
    public ResponseEntity<?> test02() {
        Test02ResponseDto test02ResponseDto = Test02ResponseDto.builder()
                .aaaa("aaa")
                .bbbb("bbb")
                .cccc(111)
                .dddd(false)
                .eeee(100L)
                .build();
        return new ResponseEntity<>(new RestResponse<>(1, "asdf", test02ResponseDto), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "@ApiResponse description", content = @Content(schema = @Schema(implementation = Test03ResponseDto.class)))})
    @Operation(summary = "@Operation summary", description = "@Operation description")
    @GetMapping("/test03")
    public ResponseEntity<?> test03() {
        Test03ResponseDto test03ResponseDto = Test03ResponseDto.builder()
                .a("aaa")
                .b("bbb")
                .c(111)
                .build();
        return new ResponseEntity<>(new RestResponse<>(1, "asdf", test03ResponseDto), HttpStatus.OK);
    }



//
//    @GetMapping("/test01")
//    public Test02ResponseDto test01(@RequestBody Test01RequestDto test01RequestDto) {
//        return Test02ResponseDto.builder()
//                .aaaa("aaa")
//                .bbbb("bbb")
//                .cccc(111)
//                .dddd(false)
//                .eeee(100L)
//                .build();
//    }
//
//    @GetMapping("/test033")
//    public Test03ResponseDto test033() {
//        return Test03ResponseDto.builder()
//                .a("aaa")
//                .b("bbb")
//                .c(111)
//                .build();
//    }
}
