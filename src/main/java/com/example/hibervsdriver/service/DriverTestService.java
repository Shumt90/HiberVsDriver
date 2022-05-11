package com.example.hibervsdriver.service;

import com.example.hibervsdriver.model.Entity1;
import java.io.CharArrayWriter;
import java.io.StringReader;
import java.time.Instant;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.hibernate.Session;
import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc.PgConnection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverTestService {

  private final EntityManager entityManager;

  @Transactional
  public void test(List<Entity1> data){
    var dataS=toCsv(data);
    insert(dataS);

    entityManager.flush();

    log.info("id of first {}",entityManager.find(Entity1.class, data.get(0).getField3()));
    log.info("id of last {}",entityManager.find(Entity1.class, data.get(data.size()-1).getField3()));
  }


  public void insert(String data) {
    Session hibernateSession = entityManager.unwrap(Session.class);
    hibernateSession.doWork(connection -> {
      var pgConnection = connection.unwrap(PgConnection.class);
      copyIn(data, pgConnection);
    });
  }

  @SneakyThrows
  private long copyIn(String data, PgConnection pgConnection) {
    return new CopyManager(pgConnection)
        .copyIn(
            "COPY entity1"
                + "(field2, field3, field4) "
                + "FROM STDIN WITH( FORMAT csv)",
            new StringReader(data)
        );
  }

  @SneakyThrows
  private String toCsv(List<Entity1> data) {

    var res = new CharArrayWriter();
    var csvData = new CSVPrinter(res, CSVFormat.DEFAULT);

    for (var entry : data) {

      csvData.printRecord(
          entry.getField2(),
          entry.getField3(),
          entry.getField4()
      );

    }
    return res.toString();
  }

}
