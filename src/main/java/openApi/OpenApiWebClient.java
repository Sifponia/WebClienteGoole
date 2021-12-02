package openApi;


import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import model.WebCliente;
import servicios.ServiciosWebClient;

public class OpenApiWebClient {


  public void webClient(RoutingContext routingContext) {
    JsonObject json = routingContext.getBodyAsJson();

    WebCliente webCliente = new WebCliente();
    webCliente.setHost(json.getString("host"));
    webCliente.setPort(json.getInteger("port"));
    webCliente.setUri(json.getString("uri"));
    webCliente.setSsl(json.getBoolean("ssl"));

    System.out.println(">>>>>> " + webCliente.toString());

    ServiciosWebClient serviciosWebClient = new ServiciosWebClient();


    serviciosWebClient.webClientServicios2(webCliente, a -> {
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "text/html").end(a);
    });

  }


}
