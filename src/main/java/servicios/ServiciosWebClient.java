package servicios;


import com.example.web.cliente.WebCliente.MainVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import model.WebCliente;
import openApi.OpenApiWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServiciosWebClient {
  private static final Logger LG = LoggerFactory.getLogger(OpenApiWebClient.class);

  public void webClientServicios2(WebCliente webClient) {
    WebClientOptions options = new WebClientOptions()
      .setSsl(webClient.isSsl());

    options.setKeepAlive(true);
    WebClient client = WebClient.create(MainVerticle.vertx, options);

    client
      .get(webClient.getPort(),
        webClient.getHost(),
        webClient.getUri())
      .expect(ResponsePredicate.contentType("text/html"))
      .send(ar -> {
        if (ar.succeeded()) {

          HttpResponse<Buffer> response = ar.result();
          LG.info("Response to Google: {}", response.statusCode() + " con datos " +
            response.body().toString("ISO-8859-1"));

        } else {
          ar.cause().printStackTrace();
        }

      });


  }


}

