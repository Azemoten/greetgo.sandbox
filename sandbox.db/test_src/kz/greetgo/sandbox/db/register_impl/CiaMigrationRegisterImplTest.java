package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.Test;

public class CiaMigrationRegisterImplTest extends ParentTestNg {

	public BeanGetter<MigrationRegister> migrationRegister;

	@Test
	public void asd(){
		migrationRegister.get().getMirgtaion();
	}
}