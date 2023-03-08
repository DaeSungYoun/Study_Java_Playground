
- Swaager 3.0 접속 주소 : http://localhost:8080/swagger-ui/

- 의존성 추가
    ```java
    implementation 'io.springfox:springfox-boot-starter:3.0.0'
    implementation 'io.springfox:springfox-swagger-ui:3.0.0'
    ```
  - 이슈
    - ```java
      @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "@ApiResponse description", content = @Content(schema = @Schema(implementation = Test02ResponseDto.class)))})
      ```
      @ApiResponses, @ApiResponse를 사용해서 responseCode 별로 설정이 가능한데<br>
      content = @Content(schema = @Schema(implementation = Test02ResponseDto.class)) << 이걸 사용할 때
      RestResponse를 사용하보니 "Could not resolve reference: undefined undefined" 에러 발생<br>
      ```java
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
      위와 같이 .additionalModels 추가하여 해결