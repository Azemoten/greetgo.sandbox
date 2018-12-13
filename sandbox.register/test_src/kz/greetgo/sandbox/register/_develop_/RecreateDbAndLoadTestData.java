package kz.greetgo.sandbox.register._develop_;

import kz.greetgo.sandbox.register.test.util.TestsBeanContainer;
import kz.greetgo.sandbox.register.test.util.TestsBeanContainerCreator;

/**
 * <p>
 * see --> Инициация приложения на рабочем месте разработчика c загрйзкой в БД тестовых данных из
 * стэнда
 * </p>
 * <p>
 * Этот скрипт запускается для иницииации приложения: здесь автоматически настраиваются конфиги и
 * инициируется БД
 * </p>
 * <p>
 * Также этот скрипт загружает в БД тестовые данные из стэнда
 * </p>
 */
public class RecreateDbAndLoadTestData {

  public static void main(String[] args) throws Exception {
    new RecreateDbAndLoadTestData().run();
  }

  private void run() throws Exception {
    TestsBeanContainer bc = TestsBeanContainerCreator.create();

    bc.dbWorker().recreateAll();
    bc.dbLoader().loadTestData();
  }
}
