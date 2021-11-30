package com.example.web.cliente.WebCliente;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import openApi.OpenApiWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servicios.ServiciosWebClient;

public class MainVerticle extends AbstractVerticle {
  public static Vertx vertx = Vertx.vertx();

  private static final Logger LG = LoggerFactory.getLogger(OpenApiWebClient.class);
  private HttpServer server;
  private OpenApiWebClient openApiWebClient = new OpenApiWebClient();
  private final int PORT = 8888;


  /************* DENTRO DE POSTMAN http://localhost:8888/web
   {
   "host": "google.com",
   "port": 443,
   "ssl": true,
   "uri": "/"
   }
   ****************/

  public static void main(String[] args) {

    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    //2)- RouterBuilder
    RouterBuilder.create(vertx, "src/main/resources/api_proxy/openapi.yaml")
      .onFailure(x -> {
        System.out.println(x.getLocalizedMessage());
      })
      .onSuccess(routerBuilder -> {
        routerBuilder.operation("webClienId").handler(routingContext -> {
          openApiWebClient.webClient(routingContext);

        });


        Router router = routerBuilder.createRouter();
        server = vertx
          .createHttpServer(new HttpServerOptions().setPort(PORT).setHost("localhost"))
          .requestHandler(router);
        server.listen();

      });


    LG.info(" Server started on port :  {}" , this.PORT);

  }


}
