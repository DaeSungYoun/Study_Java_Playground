
- Swaager 3.0 


    - SpringFox, SpringDoc 2가지 방식 존재

    - 접속 주소 : http://localhost:8080/swagger-ui/

    - 설정


      - 의존성 추가


        - ```java
          implementation 'io.springfox:springfox-boot-starter:3.0.0'
          ```


      - Config 설정


          - ```java
              package com.ydskingdom.swagger.config;
              
              import org.springframework.context.annotation.Bean;
              import org.springframework.context.annotation.Configuration;
              import springfox.documentation.builders.ApiInfoBuilder;
              import springfox.documentation.builders.PathSelectors;
              import springfox.documentation.builders.RequestHandlerSelectors;
              import com.fasterxml.classmate.TypeResolver;
              import springfox.documentation.service.ApiInfo;
              import springfox.documentation.spi.DocumentationType;
              import springfox.documentation.spring.web.plugins.Docket;
              
              @Configuration
              public class SwaggerConfig {
              
                  @Bean
                  public Docket api(TypeResolver typeResolver) {
                      return new Docket(DocumentationType.OAS_30)
                              .select()
                              .apis(RequestHandlerSelectors.any())
                              .paths(PathSelectors.any())
                              .build()
                              .apiInfo(apiInfo()); //Swagger UI로 노출할 정보
                  }
              
                  private ApiInfo apiInfo() {
                      return new ApiInfoBuilder()
                              .title("Swagger 문서 제목")
                              .description("Swagger 문서 설명")
                              .version("1.0")
                              .build();
                  }
              }
              ```

              ![img](https://github.com/ParkJiwoon/PrivateStudy/blob/master/spring/images/swagger-02.png?raw=true)

      - Annotation


        - @ApiOperation


          - ```java
            @ApiOperation(
                    value = "사용자 정보 조회"
                    , notes = "사용자의 ID를 통해 사용자의 정보를 조회한다.")
                @GetMapping("/user/{id}")
                @ResponseBody
                public UserDTO getUser(@PathVariable(name = "id") String id) {
                    return userService.findUserInfoById(id);
                }
            ```

        - @ApiImplicitParam


          - ```java
            @ApiOperation(
                    value = "사용자 정보 조회"
                    , notes = "사용자의 ID를 통해 사용자의 정보를 조회한다.")
                @ApiImplicitParam(
                    name = "id"
                    , value = "사용자 아이디"
                    , required = true
                    , dataType = "string"
                    , paramType = "path"
                    , defaultValue = "None")
                @GetMapping("/user/{id}")
                @ResponseBody
                public UserDTO getUser(@PathVariable(name = "id") String id) {
                    return userService.findUserInfoById(id);
                }
            ```

          - ```java
             @ApiOperation(
                    value = "자격증 정보 조회"
                    , notes = "자격증의 ID를 통해 자격증의 정보를 조회한다.")
                @ApiImplicitParams(
                    {
                        @ApiImplicitParam(
                            name = "id"
                            , value = "자격증 아이디"
                            , required = true
                            , dataType = "string"
                            , paramType = "path"
                            , defaultValue = "None"
                        )
                    ,
                        @ApiImplicitParam(
                            name = "fields"
                            , value = "응답 필드 종류"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                        )
                    })
                @GetMapping("/licenses/{id}")
                @ResponseBody
                public UserDTO getLicense(@PathVariable(name = "id") String id, @RequestParam(name = "fields", required = false) String fields) {
                    return userService.findUserInfoById(id);
                }
            ```

        - @ApiResponses


          - ```java
            @ApiResponses({
                    @ApiResponse(code = 200, message = "성공입니다.")
                    , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
                })
                @GetMapping("/notices/{id}")
                public String getNotice() {
                    return "notice";
                }
            ```

        - @ApiParam


          - ```java
            @Getter
            @Setter
            @NoArgsConstructor
            public class UserDTO {
                
                @ApiModelProperty(
                    name = "id"
                    , example = "gillog"
                )
                @ApiParam(value = "사용자 ID", required = true)
                private String id;
            
                @ApiParam(value = "사용자 이름")
                private String name;
            
                @ApiParam(value = "token 갱신 값")
                private String tokenRefresh;
            }
            ```

        - @ApiModelProperty


          - ```java
            @Getter
            @Setter
            @NoArgsConstructor
            public class UserDTO {
                
                @ApiModelProperty(
                    name = "id"
                    , example = "gillog"
                )
                @ApiParam(value = "사용자 ID", required = true)
                private String id;
                
                @ApiModelProperty(example = "길로그")
                @ApiParam(value = "사용자 이름")
                private String name;
            }
            ```

        - @ApiIgnore


          - ```java
            @ApiResponses({
                    @ApiResponse(code = 200, message = "성공입니다.")
                    , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
                })
                @ApiImplicitParam(
                    name = "id"
                    , value = "사용자 아이디"
                    , required = true
                    , dataType = "string"
                    , paramType = "path"
                    , defaultValue = "None")
                @GetMapping("/notices/{id}")
                public String getNotice(@ApiIgnore UserDTO userDTO) {
                    return "notice";
                }
            ```

        - 

      - Controller 설정


        - ```java
          @RestController
          public class HelloController {
          
              @Operation(summary = "test hello", description = "hello api example")
              @ApiResponses({
                      @ApiResponse(responseCode = "200", description = "OK !!"),
                      @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
                      @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
                      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
              })
              @GetMapping("/hello")
              public ResponseEntity<String> hello(@Parameter(description = "이름", required = true, example = "Park") @RequestParam String name) {
                  return ResponseEntity.ok("hello " + name);
              }
          }
          ```

          ![img](https://github.com/ParkJiwoon/PrivateStudy/blob/master/spring/images/swagger-03.png?raw=true)

        - ```java
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
          ```

          ![Swagger_ApiResponse](https://user-images.githubusercontent.com/38457303/223900381-e9c0a22b-6d2b-417b-88e2-0f0f30f5393e.png)

      - Schema 설정

        - ```java
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
          
          ```

        - ![Swagger_Schemas](https://user-images.githubusercontent.com/38457303/223901970-79b7c722-b53f-4ae9-8294-5a04c3496157.png)

    - 이슈

      - @ApiResponses, @ApiResponse, content를 사용하여 responseCode 별로 응답 모델을 Swagger에 표현하고 싶었음

      - ```java
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "@ApiResponse description", content = @Content(schema = @Schema(implementation = Test02ResponseDto.class)))})
        ```

      - 위처럼 설정하면 "Could not resolve reference: undefined undefined" 에러 발생

      - ```java
        return new ResponseEntity<>(new RestResponse<>(1, "asdf", test02ResponseDto), HttpStatus.OK);
        ```

        RestResponse 공통 응답 모델을 사용하려다보니 발생하는 문제

      - ```java
        @Bean
        public Docket api(TypeResolver typeResolver) {
          return new Docket(DocumentationType.OAS_30)
                 .additionalModels(
                        typeResolver.resolve(Test02ResponseDto.class),
                        typeResolver.resolve(Test03ResponseDto.class)
                  )
                  .select()
                  .apis(RequestHandlerSelectors.any())
                  .paths(PathSelectors.any())
                  .build()
                  .apiInfo(apiInfo());
        }
        ```

        .additionalModels()를 추가하여 해결

    

    # Reference

    - https://blog.jiniworld.me/91
    - https://bcp0109.tistory.com/326
    - https://velog.io/@gillog/Swagger-UI-Annotation-%EC%84%A4%EB%AA%85