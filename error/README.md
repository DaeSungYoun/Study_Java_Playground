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
          
 


