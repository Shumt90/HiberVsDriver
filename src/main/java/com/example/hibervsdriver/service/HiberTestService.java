package com.example.hibervsdriver.service;

import com.example.hibervsdriver.model.Entity1;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HiberTestService {

  private final EntityManager entityManager;

  @Transactional
  public void test(List<Entity1> data){
    data.forEach(entityManager::persist);
    entityManager.flush();

   log.info("id of first {}",entityManager.find(Entity1.class, data.get(0).getField3()));
    log.info("id of last {}",entityManager.find(Entity1.class, data.get(0).getField3()));

  }

}
