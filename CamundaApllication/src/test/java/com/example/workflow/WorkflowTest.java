package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

  @Autowired
  public RuntimeService runtimeService;

  @Test
  public void shouldExecuteScriptTask() {
    String processDefinitionKey = "Git-Repo-Popularity-Checker";

    ProcessInstance processInstance = runtimeService.createProcessInstanceByKey(processDefinitionKey)
            .startBeforeActivity("Activity_0u7shp8")
            .setVariable("repoOwner", "camunda")
            .setVariable("repoName", "camunda-bpm-platform")
            .execute();

    assertThat(processInstance).isActive();

  }

  @Test
  public void shouldExecuteHappyPath() {
    // given
   // String processDefinitionKey = "Git-Repo-Popularity-Checker";

    // when
   // ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);

    // then
//    assertThat(processInstance).isStarted()
//        .task()
//        .hasDefinitionKey("say-hello")
//        .hasCandidateUser("demo")
//        .isNotAssigned();
  }

}
