package kz.greetgo.sandbox.controller.controller;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.mvc.annotations.ParamsTo;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.register.ReportView;
import kz.greetgo.sandbox.controller.report.ReportViewExcel;
import kz.greetgo.sandbox.controller.report.ReportViewPDF;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@ControllerPrefix("/report")
public class ReportController implements Controller {

  public BeanGetter<ReportRegister> reportRegister;


  @PublicAccess
  @OnGet("/{type}")
  public void report(@ParPath("type") String type, RequestTunnel requestTunnel,
                     @ParamsTo ClientFilter clientFilter) throws Exception {
    ReportView reportView = null;
    String fileName = "";
    if (type.equals("pdf")) {
      fileName = "Report.pdf";
    } else if (type.equals("xlsx")) {
      fileName = "Report.xlsx";
    }

    requestTunnel.setResponseHeader("Content-Disposition", "attachment; filename=" + fileName);
    OutputStream out = requestTunnel.getResponseOutputStream();

    try (PrintStream printStream = new PrintStream(out, false, "utf-8")) {
      if (Objects.equals(type, "pdf")) {
        reportView = new ReportViewPDF(printStream);
      }
      if (Objects.equals(type, "xlsx")) {
        reportView = new ReportViewExcel(printStream);
      }
      reportRegister.get().genReport(reportView, clientFilter);

      printStream.flush();
      requestTunnel.flushBuffer();
    }


  }
}
