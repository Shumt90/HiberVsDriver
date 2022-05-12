package com.example.hibervsdriver.service;

import com.example.hibervsdriver.model.Entity1;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestRunner {

  private final DriverTestService driverTestService;
  private final HiberTestService hiberTestService;
  private final AtomicInteger atomicInteger=new AtomicInteger();

  @PostConstruct
  void init(){

    log.info("start generation of data");

    var dataForHiber = generateDataWithoutId();
    var dataForPostgres = generateDataWithId();

    log.info("start hiber test");

    var time=System.currentTimeMillis();
    hiberTestService.test(dataForHiber);
    System.out.println("hiberTestService: "+(System.currentTimeMillis()-time));

    log.info("start driver test");

    time=System.currentTimeMillis();
    driverTestService.test(dataForPostgres);
    System.out.println("driverTestService: "+(System.currentTimeMillis()-time));

  }

  private List<Entity1> generateDataWithoutId() {
    return Stream
        .generate(atomicInteger::incrementAndGet)
        .limit(10_000)
        .map(id -> Entity1.builder()
            .field2(UUID.randomUUID().toString())
            //.field3(id)
            .field4(Instant.now())
            .build())
        .collect(Collectors.toList());
  }

  private List<Entity1> generateDataWithId() {
    return Stream
        .generate(atomicInteger::incrementAndGet)
        .limit(10_000)
        .map(id -> Entity1.builder()
            .field2(UUID.randomUUID().toString())
            .field3(id)
            .field4(Instant.now())
            .build())
        .collect(Collectors.toList());
  }

}
