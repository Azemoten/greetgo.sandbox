package kz.greetgo.sandbox.debug.launchers;

import java.util.List;
import java.util.Map;
import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
import kz.greetgo.sandbox.controller.util.Modules;
import kz.greetgo.sandbox.debug.bean_containers.DebugBeanContainer;
import kz.greetgo.sandbox.register.schedule.MigrationScheduledClass;
import kz.greetgo.scheduling.ExecutionPool;
import kz.greetgo.scheduling.Scheduler;
import kz.greetgo.scheduling.Task;
import kz.greetgo.scheduling.TaskCollector;
import kz.greetgo.scheduling.ThrowableCatcher;

public class LaunchDebugServer {
  public static void main(String[] args) throws Exception {
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
    new LaunchDebugServer().run();
  }

  private void run() throws Exception {
    DepinjectUtil.implementAndUseBeanContainers(
      "kz.greetgo.sandbox.debug",
      Modules.standDir() + "/build/src_bean_containers");

    DebugBeanContainer container = Depinject.newInstance(DebugBeanContainer.class);

    container.server().start().join();
  }
}
