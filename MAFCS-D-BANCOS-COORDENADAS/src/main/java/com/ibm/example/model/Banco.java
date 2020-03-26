package com.ibm.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("Bank")
public class Banco {
  
  @Id
  private String id;
  
  private Geometry geometry;

  private String type;

  private Propiedades propiedades;

}
