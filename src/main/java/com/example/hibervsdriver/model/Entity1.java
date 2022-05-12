package com.example.hibervsdriver.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Entity1 {


  private String field2;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blocks_seq")
  @SequenceGenerator(name = "blocks_seq", sequenceName = "blocks_seq", allocationSize = 100)
  private Integer field3;
  private Instant field4;

}
