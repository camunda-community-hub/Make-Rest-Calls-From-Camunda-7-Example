package com.example.workflow;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import  org.json.*;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named
public class FindGitHubRepo implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String repoOwner = (String) execution.getVariable("repoOwner");
        String repoName = (String) execution.getVariable("repoName");


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://api.github.com/repos/" + repoOwner +  "/" + repoName)
                .asString();


        if(response.getStatus() != 200){
            throw new BpmnError("NO_REPO_FOUND", "Error making call - Repose Code: "+response.getStatus());
        }else{
            //getStatusText
            String body = response.getBody();
            JSONObject obj = new JSONObject(body);
            String issues = obj.getString("open_issues");
            int open_issues = Integer.parseInt(issues);

            execution.setVariable("openIssues", open_issues);

        }
    }
}
