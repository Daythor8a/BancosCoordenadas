package com.ibm.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm.example.model.Banco;
import com.ibm.example.model.BancoModel;
import com.ibm.example.repository.BancosRepository;
import com.ibm.example.service.BancosCoordenadasService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@Slf4j
@Service
public class BancosCoordenadasServiceImpl implements BancosCoordenadasService{

  @Autowired
  BancosRepository repository;
  
  @Override
  public List<BancoModel> findBancos(Double x, Double y) {
    log.info("Searching by coordintes {}", x, y);
    
    List<Banco> bancos = repository.findByGeometryCoordinates(x, y);
    
    List<BancoModel> response = new ArrayList<>();
    
    bancos.forEach(bank ->{
      BancoModel banco = new BancoModel();
      banco.setDireccion(bank.getPropiedades().getDireccion());
      banco.setEstado(bank.getPropiedades().getEstado());
      banco.setHoraApertura(bank.getPropiedades().getHora_apertura());
      banco.setHoraCierre(bank.getPropiedades().getHora_cierre());
      banco.setNombre(bank.getPropiedades().getNombre());
      banco.setTelefono(bank.getPropiedades().getTelefono());
      banco.setTipoSucursal(bank.getPropiedades().getTipo_sucursal());
      response.add(banco);      
    });
    
    return response;
  }
  
  public String getCoordenadasDeUsuario(String urlToRead) {

    URL url;
    HttpURLConnection conn;
    BufferedReader rd;
    String line;
    String result = "";
    try {
        url = new URL(urlToRead);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }

    //En el result viene los datos con una estructura llamada JSON (que es eso de las llaves { y dentro propiedades:valores, etc...
    //aqui abajo voy navegando por el objeto result y transformandolo hasta que llego a la "location" y ahi leo las propiedades lat y lng
    HashMap properties = new Gson().fromJson(result, HashMap.class);
    List resultados = (List) properties.get("results");
    LinkedTreeMap informacion = (LinkedTreeMap) resultados.get(0); //solo debe venir un elemento y estara en la posicion 0
    LinkedTreeMap geometryInfo = (LinkedTreeMap) informacion.get("geometry");
    LinkedTreeMap locationInfo = (LinkedTreeMap) geometryInfo.get("coordinates");
    System.out.println("La latitud es: " + locationInfo.get("0")); //LAtitud
    System.out.println("la longitud es: " +locationInfo.get("1")); //longitud
    return "La latitud es: " + locationInfo.get("0")+ ", "+"la longitud es: " +locationInfo.get("1");
}
  
//  public List<Geometry> CoordenadasUsuario(String latitude, String longitude){
//     return null;
//  }

}
