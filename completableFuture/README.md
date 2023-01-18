# CompletableFuture

## CompletableFuture가 나온 이유
    Java5에 Future가 추가되면서 비동기 작업에 대한 결과값을 반환 받을 수 있게 되었다.
    하지만 Future는 다음과 같은 한계점이 있었다
    - 외부에서 완료시킬 수 없고, get의 타임아웃 설정으로만 완료 가능
    - 블로킹 코드(get)을 통해서만 이후의 결과를 처리할 수 있음
    - 여러 Future를 조합할 수 없음 ex) 회원 정보를 가져오고, 알림을 발송하는 등
    
    Future는 외부에서 작업을 완료시킬 수 없고, 작업 완료는 오직 get 호출 시에 타임아웃으로만 가능하다.
    또한 비동기 작업의 응답에 추가 작업을 하려면 get을 호출해야 하는데, get은 블로킹 호출이므로 좋지 않다.
    또한 여러 Future들을 조합할 수도 없으며, 예외가 발생한 경우에 이를 위한 예외처리도 불가능 하다.
    
    그래서 Java 8에서는 이러한 문제를 모두 해결한 CompletableFuture가 등장하게 되었다

## CompletableFuture 기능
- ## 비동기 작업 실행
  - ### runAsync()
    - 반환값이 없는 경우
    - 비동기로 작업 실행
    - ```java
      @Test
      void runAsync() throws ExecutionException, InterruptedException {
          CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
              System.out.println("Thread: " + Thread.currentThread().getName());
          });

          future.get();
          System.out.println("Thread: " + Thread.currentThread().getName());
      }
      ```
  - ### supplyAsync()
    - 반환값이 있는 경우, get()으로 반환값을 받아올 수 있다
    - 비동기로 작업 실행
    - ```java
      @Test
      void supplyAsync() throws ExecutionException, InterruptedException {

          CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
              return "Thread: " + Thread.currentThread().getName();
          });

          System.out.println(future.get());
          System.out.println("Thread: " + Thread.currentThread().getName());
      }
      ```
- ## 작업 콜백
  - ### thenApply()
    - 반환 값을 받아서 다른 값을 반환함
    - 함수형 인터페이스 Function을 파마리터로 받음
    - thenApplyAsync -> 같은 쓰레드풀의 다른 쓰레드에서 수행, 다른 쓰레드풀을 사용하고 싶다면 인자로 쓰레드풀을 명시해야함
    - ```java
      @Test
      void thenApply() throws ExecutionException, InterruptedException {
          CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
              return "Thread: " + Thread.currentThread().getName();
          }).thenApply(s -> {
              return s.toUpperCase();
          });

          System.out.println(future.get());
      }
      ```
  - ### thenAccept()
    - 반환 값을 받아 처리하고 값을 반환하지 않음
    - 함수형 인터페이스 Consumer를 파라미터로 받음
    - thenAcceptAsync -> 같은 쓰레드풀의 다른 쓰레드에서 수행, 다른 쓰레드풀을 사용하고 싶다면 인자로 쓰레드풀을 명시해야함
    - ```java
      @Test
      void thenAccept() throws ExecutionException, InterruptedException {
          CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
              return "Thread: " + Thread.currentThread().getName();
          }).thenAccept(s -> {
              System.out.println(s.toUpperCase());
          });

          future.get();
      }
      ```
  - ### thenRun()
    - 반환 값을 받지 않고 다른 작업을 실행함
    - 함수형 인터페이스 Runnable을 파라미터로 받음
    - ```java
      @Test
      void thenRun() throws ExecutionException, InterruptedException {
          CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
              return "Thread: " + Thread.currentThread().getName();
          }).thenRun(() -> {
              System.out.println("Thread: " + Thread.currentThread().getName());
          });

          future.get();
      }
      ```
- ## 작업조합
  - ### thenCompose()
    - 두 작업이 이어서 실행하도록 조합하며, 앞선 작업의 결과를 받아서 사용할 수 있음
    - 함수형 인터페이스 Function을 파라미터로 받음
    - ```java
      @Test
      void thenCompose() throws ExecutionException, InterruptedException {
          CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
              return "Hello";
          });

          // Future 간에 연관 관계가 있는 경우
          CompletableFuture<String> future = hello.thenCompose(this::mangKyu);
          System.out.println(future.get());
      }

      private CompletableFuture<String> mangKyu(String message) {
          return CompletableFuture.supplyAsync(() -> {
              return message + " " + "MangKyu";
          });
      }
      ```
  - ### thenCombine()
    - 두 작업을 독립적으로 실행하고, 둘 다 완료되었을 때 콜백을 실행함
    - 함수형 인터페이스 Function을 파라미터로 받음
    - ```java
      @Test
      void thenCombine() throws ExecutionException, InterruptedException {
          CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
              return "Hello";
          });

          CompletableFuture<String> mangKyu = CompletableFuture.supplyAsync(() -> {
              return "MangKyu";
          });

          CompletableFuture<String> future = hello.thenCombine(mangKyu, (h, w) -> h + " " + w);
          System.out.println(future.get());
      }
      ```
  - ### allOf()
    - 여러 작업들을 동시에 실행하고, 모든 작업 결과에 콜백을 실행함
    - ```java
      @Test
      void allOf() throws ExecutionException, InterruptedException {
          CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
              return "Hello";
          });

          CompletableFuture<String> mangKyu = CompletableFuture.supplyAsync(() -> {
              return "MangKyu";
          });

          List<CompletableFuture<String>> futures = List.of(hello, mangKyu);

          CompletableFuture<List<String>> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                  .thenApply(v -> futures.stream().
                          map(CompletableFuture::join).
                          collect(Collectors.toList()));

          result.get().forEach(System.out::println);
      }
      ```
  - ### anyOf()
    - 여러 작업들 중에 가장 빨리 끝난 하나의 결과에 콜백을 실행함
    - ```java
      @Test
      void anyOf() throws ExecutionException, InterruptedException {
          CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
              try {
                  Thread.sleep(1000L);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }

              return "Hello";
          });

          CompletableFuture<String> mangKyu = CompletableFuture.supplyAsync(() -> {
              return "MangKyu";
          });

          CompletableFuture<Void> future = CompletableFuture.anyOf(hello, mangKyu).thenAccept(System.out::println);
          future.get();
      }
      ```
- ## 예외처리
  - ### exceptionally()
    - 발생한 에러를 받아서 예외를 처리함
    - ```java
      @ParameterizedTest
      @ValueSource(booleans =  {true, false})
      void exceptionally(boolean doThrow) throws ExecutionException, InterruptedException {
          CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
              if (doThrow) {
                  throw new IllegalArgumentException("Invalid Argument");
              }

              return "Thread: " + Thread.currentThread().getName();
          }).exceptionally(e -> {
              return e.getMessage();
          });

          System.out.println(future.get());
      }

      java.lang.IllegalArgumentException: Invalid Argument
      // Thread: ForkJoinPool.commonPool-worker-19
      ```
  - ### handle(), handleAsync()
    - (결과값, 에러)를 반환받아 에러가 발생한 경우와 아닌 경우 모두를 처리할 수 있음
    - 함수형 인터페이스 BiFunction을 파라미터로 받음
    - ```java
      @ParameterizedTest
      @ValueSource(booleans =  {true, false})
      void handle(boolean doThrow) throws ExecutionException, InterruptedException {
          CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
              if (doThrow) {
                  throw new IllegalArgumentException("Invalid Argument");
              }

              return "Thread: " + Thread.currentThread().getName();
          }).handle((result, e) -> {
              return e == null
                      ? result
                      : e.getMessage();
          });

          System.out.println(future.get());
      }
      ```
    




https://brunch.co.kr/@springboot/267
https://mangkyu.tistory.com/263