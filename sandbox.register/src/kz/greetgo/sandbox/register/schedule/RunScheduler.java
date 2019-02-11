package kz.greetgo.sandbox.register.schedule;

import java.util.List;
import java.util.Map;
import kz.greetgo.scheduling.ExecutionPool;
import kz.greetgo.scheduling.Scheduler;
import kz.greetgo.scheduling.Task;
import kz.greetgo.scheduling.TaskCollector;
import kz.greetgo.scheduling.ThrowableCatcher;

public class RunScheduler {

  public static void main(String[] args) {

    TaskCollector taskCollector = new TaskCollector(
        System.getProperty("user.home") + "/sandbox.d/conf/");
    taskCollector.throwableCatcher = new ThrowableCatcher() {
      @Override
      public void catchThrowable(Throwable throwable) {
        System.out.println("wow " + throwable);
      }
    };

    MigrationScheduledClass x = new MigrationScheduledClass();

    taskCollector.collect(x);

    List<Task> tasks = taskCollector.getTasks();

    Map<String, ExecutionPool> executionPoolMap = ExecutionPool.poolsForTasks(tasks);

    Scheduler scheduler = new Scheduler(tasks, executionPoolMap);

    scheduler.startup();
  }
}
