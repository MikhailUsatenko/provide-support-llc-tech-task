package com.usatenko.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TaskScheduler {

  public static final int THREAD_POOL_SIZE = 30;

  private ExecutorService executorService;
  private ScheduledExecutorService scheduledExecutorService;

  @PostConstruct
  private void initScheduledExecutorService() {
    log.info("Init Task Scheduler...");
    scheduledExecutorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
  }

  public void submit(Runnable task) {
    // TODO: Surround with try/catch
    executorService.submit(task);
  }

  public void schedule(Runnable task, long delay, TimeUnit timeUnit) {
    // TODO: Surround with try/catch
    scheduledExecutorService.schedule(task, delay, timeUnit);
  }
}
