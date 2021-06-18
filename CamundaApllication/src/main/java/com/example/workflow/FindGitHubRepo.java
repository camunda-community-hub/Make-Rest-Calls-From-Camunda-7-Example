package com.example.workflow;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import  org.json.*;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class FindGitHubRepo implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String repoOwner = (String) execution.getVariable("repoOwner");
        String repoName = (String) execution.getVariable("repoName");


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://api.github.com/repos/" + repoOwner + "/" + repoName)
                .asString();


        if (response.getStatus() != 200) {

            // create incidence if Status code is not 200
            throw new Exception("Error from REST call, Response code: " + response.getStatus());

        } else {

            //getStatusText
            String body = response.getBody();
            JSONObject obj = new JSONObject(body);
            //parse for downloads
            Boolean downloads = obj.getBoolean("has_downloads");

            if (!downloads) {

                // Throw BPMN error
                throw new BpmnError("NO_DOWNLOAD_OPTION", "Repo can't be downloaded");

            } else {

                //parse for forks
                String forks = obj.getString("forks");
                int forksAsNumber = Integer.parseInt(forks);
                //Set variables to the process
                execution.setVariable("forks", forksAsNumber);

            }
        }
    }
}
