package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.NoImplementor;
import kz.greetgo.depinject.gen.DepinjectUtil;

import java.util.Date;

public class TestsBeanContainerCreator {

  public static TestsBeanContainer create() {
    try {
      return Depinject.newInstance(TestsBeanContainer.class);
    } catch (NoImplementor ignore) {

      try {
        DepinjectUtil.implementAndUseBeanContainers(
            "kz.greetgo.sandbox.register.test",
            "build/create/recreate_src/" + new Date().getTime()
        );
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      return Depinject.newInstance(TestsBeanContainer.class);
    }
  }
}
