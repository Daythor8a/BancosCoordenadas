package com.ibm.example.service;

import java.util.List;
import com.ibm.example.model.BancoModel;

public interface BancosCoordenadasService {
  
  List<BancoModel> findBancos(Double x, Double y);

}
