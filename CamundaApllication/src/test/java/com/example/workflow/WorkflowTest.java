package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

  @Autowired
  public RuntimeService runtimeService;

  @Test
  public void shouldExecuteScriptTask() {
    String processDefinitionKey = "Git-Repo-Popularity-Checker";

    ProcessInstance processInstance = runtimeService.createProcessInstanceByKey(processDefinitionKey)
            .startBeforeActivity("Activity_0u7shp8" /*Get Repo Languages*/)
            .setVariable("repoOwner", "camunda")
            .setVariable("repoName", "camunda-bpm-platform")
            .execute();

    assertThat(processInstance).isActive()
        .variables()
          .contains(
              entry("programingLanguages", "null Java  JavaScript  Python  Ruby "), 
              entry("java", true),
              entry("closure", false),
              entry("javaScript", true),
              entry("python", true),
              entry("ruby", true),
              entry("scala", false));
  }
  
  @Test
  public void testConnectorCallSuccesscully() {
    ProcessInstance processInstance = runtimeService
        .createProcessInstanceByKey("Git-Repo-Popularity-Checker")
        .startBeforeActivity("Activity_0as6i8e" /*Get community profile*/)
        .setVariables(withVariables(
            "repoName", "camunda-bpm-platform", 
            "repoOwner", "camunda"))
        .execute();
    
    assertThat(processInstance).isWaitingAt("Activity_0scbww2" /*Display Results*/)
        .variables().contains(entry("healthPercentage", Integer.valueOf(71)));
  }
  
  @Test
  public void testConnectorCallBpmnError() {
    ProcessInstance processInstance = runtimeService
        .createProcessInstanceByKey("Git-Repo-Popularity-Checker")
        .startBeforeActivity("Activity_0as6i8e" /*Get community profile*/)
        .setVariables(withVariables(
            "repoName", "camunda-bpm-examples", 
            "repoOwner", "camunda"))
        .execute();
    
    assertThat(processInstance).isWaitingAt("Activity_0hbvr03" /*Look at error*/)
        .variables().contains(entry("healthPercentage", Integer.valueOf(57)));
  }

}
