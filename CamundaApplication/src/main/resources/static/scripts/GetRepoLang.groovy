import wslite.rest.RESTClient
import wslite.rest.RESTClientException

def repoOwner = execution.getVariable("repoOwner")
def repoName = execution.getVariable("repoName")

def java = false
def javaScript = false
def python = false
def ruby = false
def closure = false
def scala = false

def programingLanguages


RESTClient client = new RESTClient("https://api.github.com/")
def path = "/repos/"+ repoOwner +"/"+ repoName +"/languages"
def response



try {
    println(path)
    response = client.get(path: path)
    println(response.contentAsString)


    if(response.contentAsString.contains("Java")){
        java = true;
        programingLanguages = programingLanguages + " Java "
    }
    if(response.contentAsString.contains("JavaScript")){
        javaScript = true;
        programingLanguages = programingLanguages + " JavaScript "
    }
    if(response.contentAsString.contains("Python")){
        python = true;
        programingLanguages = programingLanguages + " Python "
    }
    if(response.contentAsString.contains("Ruby")){
        ruby = true;
        programingLanguages = programingLanguages + " Ruby "
    }
    if(response.contentAsString.contains("Closure")){
        closure = true;
        programingLanguages = programingLanguages + " Closure "
    }
    if(response.contentAsString.contains("Scala")){
        scala = true;
        programingLanguages = programingLanguages + " Scala "

        throw new org.camunda.bpm.engine.delegate.BpmnError("error-scala-detected");
    }
    //return variables
    execution.setVariable("programingLanguages", programingLanguages)
    execution.setVariable("java", java)
    execution.setVariable("javaScript", javaScript)
    execution.setVariable("python", python)
    execution.setVariable("ruby", ruby)
    execution.setVariable("closure", closure)
    execution.setVariable("scala", scala)

} catch (RESTClientException e) {
    println(e)
    throw new Exception(e)
}