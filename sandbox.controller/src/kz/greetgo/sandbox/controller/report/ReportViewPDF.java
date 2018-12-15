package kz.greetgo.sandbox.controller.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import kz.greetgo.sandbox.controller.register.ReportView;

public class ReportViewPDF implements ReportView {

  private static final String RESULT = "build/my_report/result3.pdf";
  private static Document document = new Document();
  private final OutputStream outputStream;

  public ReportViewPDF(OutputStream outputStream) {
    this.outputStream = outputStream;
  }


  @Override
  public void start() throws DocumentException {
    PdfWriter.getInstance(document, outputStream);
    document.setPageSize(PageSize.A4);
    document.setMargins(36, 72, 100, 100);
    document.setMarginMirroring(true);
    document.open();
    Font bold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    Paragraph preface = new Paragraph(new Phrase("Report", bold));
    preface.setAlignment(Element.ALIGN_CENTER);
    preface.setSpacingAfter(10);
    document.add(preface);
    PdfPTable columns = new PdfPTable(6);
    columns.addCell(new PdfPCell(new Phrase("FIO", bold)));
    columns.addCell(new PdfPCell(new Phrase("Charm", bold)));
    columns.addCell(new PdfPCell(new Phrase("Age", bold)));
    columns.addCell(new PdfPCell(new Phrase("Common Money", bold)));
    columns.addCell(new PdfPCell(new Phrase("Minimum Money", bold)));
    columns.addCell(new PdfPCell(new Phrase("Maximum money", bold)));
    document.add(columns);
  }

  @Override
  public void addRow(ReportRow row) throws DocumentException {
    PdfPTable values = new PdfPTable(6);
    values.addCell(new PdfPCell(new Phrase(row.fio)));
    values.addCell(new PdfPCell(new Phrase(row.charmName)));
    values.addCell(new PdfPCell(new Phrase(Integer.toString(row.age))));
    values.addCell(new PdfPCell(new Phrase(Double.toString(row.commonMoney))));
    values.addCell(new PdfPCell(new Phrase(Double.toString(row.minMoney))));
    values.addCell(new PdfPCell(new Phrase(Double.toString(row.maxMoney))));
    document.add(values);
  }

  @Override
  public void finish(ReportFootData footData) throws DocumentException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String foot = "Author: " + footData.generatedBy + " Date: " + sdf.format(footData.generatedAt);
    Paragraph paragraph = new Paragraph(foot);
    paragraph.setSpacingBefore(10);
    document.add(paragraph);
    document.close();
  }

}
