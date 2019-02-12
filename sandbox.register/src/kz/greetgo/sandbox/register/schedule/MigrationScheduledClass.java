package kz.greetgo.sandbox.register.schedule;

import kz.greetgo.sandbox.register.migration.InMigration;
import kz.greetgo.scheduling.ExecutionPool;
import kz.greetgo.scheduling.Scheduled;
import kz.greetgo.scheduling.Scheduler;
import kz.greetgo.scheduling.Task;
import kz.greetgo.scheduling.TaskCollector;
import kz.greetgo.scheduling.ThrowableCatcher;
import kz.greetgo.scheduling.FromConfig;
import kz.greetgo.scheduling.UsePool;

public class MigrationScheduledClass {
//  @FromConfig("Таска номер 1")
  @Scheduled("repeat every 10 min")
  public void migrateData() throws Exception {
    InMigration inMigration = new InMigration();
    inMigration.execute();
    System.out.println("HI IT WORKS");
  }
}
