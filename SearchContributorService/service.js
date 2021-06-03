const got = require('got');
const fetch = require('node-fetch');
const { Client, logger } = require("camunda-external-task-client-js");
const { Variables } = require("camunda-external-task-client-js");

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
const config = { baseUrl: "http://localhost:8080/engine-rest", use: logger };

// create a Client instance with custom configuration
const client = new Client(config);

// susbscribe to the topic: 'creditScoreChecker'
client.subscribe("searchContributors", async function({ task, taskService }) {

    const repoName = task.variables.get("repoName");
    const repoOwner = task.variables.get("repoOwner");
  
const url = "https://api.github.com/repos/"+ repoName +"/" + repoName + "/contributors"
console.log(url)

try{
    const contributors = await fetch(url).then(response => response.json());
    var numberContributors = Object.keys(contributors).length;

    const processVariables = new Variables();
    processVariables.set("contributors", numberContributors);
    console.log(numberContributors);

    await taskService.complete(task, processVariables);

}catch (error){
    await taskService.handleBpmnError(task, "BPMNError_Code", "statusCode");
}
});