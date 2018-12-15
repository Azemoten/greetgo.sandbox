package kz.greetgo.sandbox.controller.report;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import kz.greetgo.msoffice.xlsx.gen.Sheet;
import kz.greetgo.msoffice.xlsx.gen.Xlsx;
import kz.greetgo.sandbox.controller.register.ReportView;

public class ReportViewExcel implements ReportView {

  private final OutputStream outputStream;
  private Xlsx xlsx;
  private Sheet sheet;

  public ReportViewExcel(OutputStream outputStream) {this.outputStream = outputStream;}

  @Override
  public void start() throws DocumentException, IOException {
    xlsx = new Xlsx();
    sheet = xlsx.newSheet(true);
    sheet.row().start();
    sheet.cellStr(1, "Отчет");
    sheet.skipRow();
    sheet.row().start();
    sheet.cellStr(1, "FIO");
    sheet.cellStr(2, "Характер");
    sheet.cellStr(3, "Возраст");
    sheet.cellStr(4, "Общий остаток");
    sheet.cellStr(5, "Минимальный остаток");
    sheet.cellStr(6, "Максимальный остаток");
    sheet.row().finish();
    sheet.skipRow();

  }

  @Override
  public void addRow(ReportRow row) throws DocumentException {
    sheet.row().start();
    sheet.cellStr(1, row.fio);
    sheet.cellStr(2, row.charmName);
    sheet.cellStr(3, Integer.toString(row.age));
    sheet.cellStr(4, Double.toString(row.commonMoney));
    sheet.cellStr(5, Double.toString(row.maxMoney));
    sheet.cellStr(6, Double.toString(row.minMoney));
    sheet.row().finish();
  }

  @Override
  public void finish(ReportFootData footData) throws DocumentException, IOException {
    sheet.skipRow();
    sheet.row().start();
    if(Objects.nonNull(footData.generatedBy)) {
      sheet.cellStr(1, "Сформирован " + footData.generatedBy + " " + footData.generatedAt);
    }
    sheet.row().finish();

    xlsx.complete(outputStream);
  }
}
