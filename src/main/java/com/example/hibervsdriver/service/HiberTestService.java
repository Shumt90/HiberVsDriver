package com.example.hibervsdriver.service;

import com.example.hibervsdriver.model.Entity1;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HiberTestService {

  private final EntityManager entityManager;

  @Transactional
  public void test(List<Entity1> data){
    data.forEach(entityManager::persist);
  }

}
