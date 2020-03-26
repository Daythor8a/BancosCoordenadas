package com.ibm.example.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ibm.example.model.Banco;

public interface BancosRepository extends MongoRepository<Banco, Double[]>{
  
  List<Banco> findByGeometryCoordinates(Double x, Double y);

}
