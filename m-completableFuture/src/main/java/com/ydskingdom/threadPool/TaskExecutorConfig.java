package com.ydskingdom.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean(name = "simpleTaskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        /*
        setCorePoolSize() 개수만큼 스레드가 일을 처리 함
        setCorePoolSize() 개수보다 task가 많아지게 되면
        setQueueCapacity()에 설정한 크기의 LinkedBlockingQueue를 생성하여 Task를 대기 시킴
        setQueueCapacity()를 따로 설정하지 않는 경우 Default 크기만큼 생성됨
        LinkedBlockingQueue도 꽉차게 되면
        setMaxPoolSize() 개수만큼 스레드를 추가 생성하여 진행
        */

        taskExecutor.setCorePoolSize(5);    //기본 스레드 수, Default는 1
        taskExecutor.setMaxPoolSize(10);    //최대 스레드 수, Deafult는 Integer.MAX_VALUE
        taskExecutor.setQueueCapacity(100);	//Queue 사이즈, Deafult는 Integer.MAX_VALUE
        taskExecutor.setThreadNamePrefix("async-task"); // 스레드 명 prefix

        /*
        작업 queue가 가득차서 새로운 작업을 더 이상 받지 못할 때 RejectedExecutionException 발생
        거부된 작업을 처리하기 위해 setRejectedExecutionHandler()를 사용한다.
        1. AbortPolicy() -> 기본설정, 거부된 작업을 버린다.
        2. DiscardPolicy() -> 큐의 가장 오래된 작업을 제거하고 거부된 작업을 큐에 추가한다
        3. DiscardOldestPolicy() -> 큐의 가장 오래된 작업을 제거하고 거부된 작업을 큐에 추가한다
        4. CallerRunsPolicy() -> 거부된 작업을 제출한 스레드에서 직접 실행한다.
        */

        // 기본 설정, RejectedExecutionException을 발생
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        // shutdown 상태가 아니라면 ThreadPoolTaskExecutor에 요청한 thread에서 직접 처리
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 오래된 작업을 skip한다. 모든 task가 무조건 처리되어야 할 필요가 없을 경우 사용
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        // 처리하려는 작업을 skip한다. 모든 task가 무조건 처리되어야 할 필요가 없을 경우 사용
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        // true일 경우 애플리케이션이 종료되어도 queue에 남아 있는 모든 작업이 완료될 때까지 기다림
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        // 모든 작업이 처리되길 기다리기 힘든 경우라면 최대 종료 대기 시간 설정 가능, shutdown 최대 60초 대기
        taskExecutor.setAwaitTerminationSeconds(60);

        return taskExecutor;
    }

    @Bean(name = "taskExecutor1")
    public ThreadPoolTaskExecutor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

    @Bean(name = "taskExecutor2")
    public ThreadPoolTaskExecutor taskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        return executor;
    }
}
