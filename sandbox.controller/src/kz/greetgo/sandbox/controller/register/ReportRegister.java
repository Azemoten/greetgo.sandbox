package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientFilter;

public interface ReportRegister {

  void genReport(ReportView reportView, ClientFilter clientFilter) throws Exception;
}
