package com.example.workflow;

import java.net.http.HttpResponse;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;

@Named
public class WireMockDelegate extends FindGitHubRepo {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String wireMockUrl = (String) execution.getVariable("wireMockUrl");
    
    HttpResponse<String> response = get(wireMockUrl);

    if (response.statusCode() != 200) {

        // create incidence if Status code is not 200
        throw new Exception("Error from REST call, Response code: " + response.statusCode());

    } 
  }
}
