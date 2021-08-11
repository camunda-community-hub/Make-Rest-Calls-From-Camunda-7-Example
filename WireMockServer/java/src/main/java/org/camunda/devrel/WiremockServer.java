package org.camunda.devrel;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

public class WiremockServer {

  public static void main(String[] args) {
    WireMockServer wireMockServer = new WireMockServer(options().port(8089));
    wireMockServer.start();
    
    wireMockServer.stubFor(get(urlEqualTo("/some/thing"))
        .willReturn(okJson("{ \"message\": \"Hello\" }")));
    
    wireMockServer.stubFor(get(urlEqualTo("/delay"))
        .willReturn(aResponse()
            .withFixedDelay(10000)
            .withHeader("Content-Type", "application/json")
            .withBody("{ \"message\": \"this is 10 seconds late\" }")));
    
    wireMockServer.stubFor(get(urlEqualTo("/does/not/exist")).willReturn(notFound()));
    
    wireMockServer.stubFor(get(urlEqualTo("/server/breakdown"))
        .willReturn(aResponse()
            .withFixedDelay(2000)
            .withFault(Fault.CONNECTION_RESET_BY_PEER)));
  }

}
