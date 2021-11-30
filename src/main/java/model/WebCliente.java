package model;


import lombok.Data;

@Data
public class WebCliente {

  private String host;
  private int port;
  private boolean ssl;
  private String uri;


}
