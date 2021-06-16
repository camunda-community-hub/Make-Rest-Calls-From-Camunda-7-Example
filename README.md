
# Make REST calls from Camunda Example

This project shows the various ways that exist to make REST calls from Camunda. The project covers the implementation of Service Tasks for:
* [Java Delegates](https://docs.camunda.org/manual/latest/user-guide/process-engine/delegation-code/)
* [External Task](https://docs.camunda.org/manual/latest/user-guide/process-engine/external-tasks/)
* [Connectors](https://docs.camunda.org/manual/latest/user-guide/process-engine/connectors/)

It shows for the various implementations how to deal with the response Status code as well as with the response body.

Important: If a REST call returns not a 200 status Code, this does not automatically lead to an incident or a BPMN error. Instead this can be implemented and handled depending on the requirements for each project. For more information read the explanation section.

## Run the project
The project contains a Camunda Spring Boot application and a [JavaScript External Task Worker](https://github.com/camunda/camunda-external-task-client-js). Download the project and open the Camunda Spring Boot application in your IDE. Start the application.

Start a process instance of the process in Tasklist, by using the predefined start form or start the instance via REST. If you [start the instance via REST](https://docs.camunda.org/manual/latest/reference/rest/process-definition/post-start-process-instance/) make sure the needed variables are included ```(repoName and repoOwner)```

Navigate to the folder of the Javascript External Task client. Install the following packages:
```
npm install camunda-external-task-client-js
npm install node-fetch
```


Now you can run the worker
```
node service.js
```



## Explanation of the implementations
This section provides more information on the single implementations. It shows how the status code and the information from the response body can be used differently. In general there are the following options for Service Tasks:
* complete the task (use the information from the REST call to route the further process)
* throw a BPMN error (information gained from the REST call lead to an error that should be handled within the logic of the process)
* create an incident (information from the REST call lead to a fail and create an incident in the workflow engine, which require an administrator to solve)

### Java Delegate
If you
#### complete the task

#### throw a BPMN error

#### create an incident

### External Task
#### complete the task

#### throw a BPMN error

#### create an incident

### Connectors
#### complete the task

#### throw a BPMN error

#### create an incident

