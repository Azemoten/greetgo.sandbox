package kz.greetgo.sandbox.controller.register;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import kz.greetgo.sandbox.controller.report.ReportFootData;
import kz.greetgo.sandbox.controller.report.ReportRow;

public interface ReportView {
  void start() throws DocumentException, IOException;

  void addRow(ReportRow row) throws DocumentException;

  void finish(ReportFootData footData) throws DocumentException, IOException;
}
