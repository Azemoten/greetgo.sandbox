package kz.greetgo.sandbox.register.impl;

import java.util.Date;
import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.ReportFootData;
import kz.greetgo.sandbox.controller.register.ReportView;

@Bean
public class ReportRegisterImpl implements ReportRegister {

  public BeanGetter<Jdbc> jdbc;
  private ReportView view;
  public void genReport(ReportView reportView) throws Exception {
    view = reportView;

    view.start();

    jdbc.get().execute(new ReportJdbc(view));

    ReportFootData reportFootData = new ReportFootData();
    reportFootData.generatedBy = "Almas";
    reportFootData.generatedAt = new Date();
    view.finish(reportFootData);

  }
}
