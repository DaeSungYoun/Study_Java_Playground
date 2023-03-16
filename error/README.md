- # spring boot 에러
  - ## 스프링부트 에러 흐름
    - ```text
      스프링부트 에러 흐름
      WAS(톰캣) -> 필터 -> 서블릿(디스패처 서블릿) -> 인터셉터 -> 컨트롤러 
      -> 컨트럴로(예외발생) -> 인터셉터 -> 서블릿(디스패처 서블릿) -> 필터 -> WAS(톰캣)
      -> WAS(톰캣) -> 필터 -> 서블릿(디스패처 서블릿) -> 인터셉터 -> 컨트롤러(BasicErrorController)
      ```
    
  - ## Whitelabel Error page
    - spring boot에서 Exception 발생 시 Whitelabel Error page를 기본으로 노출 시킨다
    - Whitelabel Error page는 properties 파일에서 설정으로 관리 할 수 있다
      - 설정 항목
        - server.error.include-exception : (`true`, `false`)
          - Whitelabel Error page에 exception의 내용 포함 여부
        - server.error.include-stacktrace : (`ALWAYS`, `NEVER`, `ON_TRACE_PARAM`)
          - Whitelabel Error page에 stacktrace 내용 포함 여부
        - server.error.path :  
          - 기본 값 /error
        - server.error.include-message: always
        - server.error.include-binding-errors: always
  - ## BasicErrorController
    - server.error.path의 경로는 BasicErrorController에서 @RequestMapping으로 설정되어있음
    -
  - ## HandlerExceptionResolver
    - 컨트롤러(핸들러) 밖으로 예외가 던져진 경우 예외를 해결하고, 동작을 새로 정의할 수 있는 방법을 제공한다
      - ### DefaultHandlerExceptionResolver
        - 스프링 내부에서 발생하는 스프링 예외를 해결한다.
      - ### ResponseStatusExceptionResolver
        - 2가지 방식 존재, @ResponseStatus가 달려있는 예외 또는 ResponseStatusException 예외
        - @ResponseStatus
          - Exception class에 @ResponseStatus 선언하여 사용
          - ```java
            @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "page not found")
             public class PageNotFoundException extends RuntimeException{
            }
            ```
          - PageNotFoundException 발생 시 ResponseStatusExceptionResolver가 http 상태코드를 404로 변경하고, 메세지를 응답한다
          - **@ResponseStatus는 개발자가 수정할 수 없는 Exception 클래스에는 적용할 수 없다 그럴 경우 ResponseStatusException을 사용**
        - ResponseStatusException
          - @ResponseStatus의 대체재로 Spring 5에 등장
          - ```java
            public ResponseStatusException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
            }         
            ```
          - ```java
            @GetMapping("/ResponseStatusExceptionResolver/ResponseStatusException2")
            public void ResponseStatusExceptionResolver_ResponseStatusException2() {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "ResponseStatusException Exception", new CustomException());
            }       
            ```
          - status: HTTP Status <br>
            reason: HTTP response Message <br>
            cause: ResponseStatusException 을 발생시킨 Exception
      - ### ExceptionHandlerExceptionResolver
        - @ExceptionHandler 어노테이션을 사용하여 Exception 관리
        - Controller에 @ExceptionHandler를 정의하는 경우, RestControllerAdvice에 @ExceptionHandler를 정의하는 경우
        - Controller에 정의한 @ExceptionHandler가 우선순위가 높음
        

