package kz.greetgo.sandbox.register.impl;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.ReportFootData;
import kz.greetgo.sandbox.controller.report.ReportRow;
import kz.greetgo.sandbox.controller.register.ReportView;
import kz.greetgo.sandbox.register.dao.ReportDao;
import kz.greetgo.sandbox.register.test.beans.RandomEntity;
import kz.greetgo.sandbox.register.test.dao.ClientDaoTest;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class ReportImpTest extends ParentTestNg {

  public BeanGetter<ClientDaoTest> clientDaoTest;
  public BeanGetter<ReportRegister> reportRegister;
  public BeanGetter<RandomEntity> randomEntity;
  public BeanGetter<ReportDao> reportDao;


  private static class TestView implements ReportView {

    public ReportFootData footData = null;

    @Override
    public void start() throws DocumentException, IOException {

    }

    public final List<ReportRow> rows = new ArrayList<>();

    @Override
    public void addRow(ReportRow row) throws DocumentException {
      rows.add(row);
    }

    @Override
    public void finish(ReportFootData footData) throws DocumentException, IOException {
      this.footData = footData;
    }
  }

  @Test
  public void genReport() throws Exception {
    clientDaoTest.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm, 0);
    Client client1 = randomEntity.get().client(charm, 0);
    Client client2 = randomEntity.get().client(charm, 0);
    Client client3 = randomEntity.get().client(charm, 0);
    Client client4 = randomEntity.get().client(charm, 0);
    clientDaoTest.get().insertCharm(charm);
    clientDaoTest.get().insertForReport(client);
    clientDaoTest.get().insertForReport(client1);
    clientDaoTest.get().insertForReport(client2);
    clientDaoTest.get().insertForReport(client3);
    clientDaoTest.get().insertForReport(client4);
    TestView testView = new TestView();
    ClientFilter clientFilter = new ClientFilter();
    //
    //
    reportRegister.get().genReport(testView, clientFilter);

    //
    //

    assertThat(testView.rows.get(0).age).isEqualTo(4);
    assertThat(testView.rows.get(0).charmName).isEqualTo(charm.name);
    assertThat(testView.rows.get(0).maxMoney).isEqualTo(0.0);
    assertThat(testView.rows.get(0).fio).isEqualTo(client.surname + " " + client.name);
    assertThat(testView.rows.get(4).age).isEqualTo(4);
    assertThat(testView.rows.get(4).charmName).isEqualTo(charm.name);
    assertThat(testView.rows.get(4).maxMoney).isEqualTo(0.0);
    assertThat(testView.rows.get(4).fio).isEqualTo(client4.surname + " " + client4.name);
    assertThat(testView.rows.size()).isEqualTo(5);

  }

}
