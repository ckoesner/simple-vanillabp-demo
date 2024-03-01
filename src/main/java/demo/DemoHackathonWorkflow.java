package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vanillabp.spi.cockpit.usertask.PrefilledUserTaskDetails;
import io.vanillabp.spi.cockpit.usertask.UserTaskDetails;
import io.vanillabp.spi.cockpit.usertask.UserTaskDetailsProvider;
import io.vanillabp.spi.cockpit.workflow.PrefilledWorkflowDetails;
import io.vanillabp.spi.cockpit.workflow.WorkflowDetails;
import io.vanillabp.spi.cockpit.workflow.WorkflowDetailsProvider;
import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.TaskId;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@WorkflowService(workflowAggregateClass = DemoAggregate.class)
public class DemoHackathonWorkflow {

    private static final Logger logger = LoggerFactory.getLogger(DemoHackathonWorkflow.class);
    
    @Autowired
    private ProcessService<DemoAggregate> processService;
        
    public void startDemo(final String id) throws Exception {
        
        var demo = new DemoAggregate();
        demo.setId(id);
        demo = processService.startWorkflow(demo);

        logger.info("Run: {}", demo.getId());

    }
    
    public void requestDemo(final String id) throws Exception {
        
        var demo = new DemoAggregate();
        demo.setId(id);
        demo = processService.correlateMessage(demo, "Start");

        logger.info("Run: {}", demo.getId());

    }
    
    public void continueDemo(final DemoAggregate demo) throws Exception {
        
        processService.correlateMessage(demo, "Continue");

        logger.info("Continue: {}", demo.getId());

    }
    
    @WorkflowTask
    public void processTask(
            final DemoAggregate demo,
            @TaskId final String taskId) {
        
        logger.info("UserTask ID: '{}'", taskId);
        
    }
    
    public void completeUserTask(
            final DemoAggregate demo,
            final String taskId) {

        demo.setSuccess(
                Integer.parseInt(demo.getId()) % 2 == 0);
        
        processService.completeUserTask(demo, taskId);
        
    }
    
    @WorkflowTask(taskDefinition = "logError")
    public void logErrorOnFailure() {
        
        logger.info("error");
        
    }


    @UserTaskDetailsProvider(taskDefinition = "processTask")
    public UserTaskDetails userTaskDetails(DemoAggregate demoAggregate, PrefilledUserTaskDetails prefilledUserTaskDetails){
        List<String> a = List.of("a1", "a2", "a3");
        prefilledUserTaskDetails.setDetails(Map.of("a", a, "b", "b"));
        prefilledUserTaskDetails.setDetailsFulltextSearch("c");
        return prefilledUserTaskDetails;
    }


    @WorkflowDetailsProvider
    public WorkflowDetails workflowDetails(DemoAggregate demoAggregate, PrefilledWorkflowDetails prefilledWorkflowDetails){
        List<String> a = List.of("a1", "a2", "a3");
        prefilledWorkflowDetails.setDetails(Map.of("a", a, "b", "b"));
        prefilledWorkflowDetails.setDetailsFulltextSearch("c");
        return prefilledWorkflowDetails;
    }

    /// new Stuff

    @WorkflowTask(taskDefinition = "jokeEvaluation")
    public void jokeEvaluation() {
        logger.info("UserTask: jokeEvaluation");
    }

    @Data
    @NoArgsConstructor
    public static class Choice {
        public int index;
        public Message message;
        public Object logprobs;
        public String finish_reason;
    }

    @Data
    @NoArgsConstructor
    public static class Message {
        public String role;
        public String content;
    }

    @Data
    @NoArgsConstructor
    public static class OpenApiResponse {
        public String id;
        public String object;
        public int created;
        public String model;
        public ArrayList<Choice> choices;
        public Usage usage;
        public String system_fingerprint;
    }

    @Data
    @NoArgsConstructor
    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }

    @WorkflowTask(taskDefinition = "evaluateJoke")
    public void evaluateJoke(DemoAggregate demoAggregate) {
        logger.info("ServiceTask: EvaluateJoke");

        // getJokeData
        logger.info("demoAggregate: {}", demoAggregate.getJokeData());

        // use openAI -> query joke score 1 - 10
        try {
            String body = "{\n" +
                    "    \"model\": \"gpt-4-turbo-preview\",\n" +
                    "    \"messages\": [\n" +
                    "      {\n" +
                    "        \"role\": \"user\",\n" +
                    "        \"content\": \"Wie findest du diesen Witz von einer Skala von 1 bis 10 wo 10 das lustigste ist und antworte nur mit der skala als zahl:\\n\\n" +
                    demoAggregate.getJokeData() +
                    "       \"" +
                    "      }\n" +
                    "    ]\n" +
                    "  }";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + "")
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("geht nicht");
            }
            // logger.info("Response: {} ", response.body());

            ObjectMapper om = new ObjectMapper();
            OpenApiResponse openApiResponse = om.readValue(response.body(), OpenApiResponse.class);

            // Optional[DemoHackathonWorkflow.Choice(index=0, message=DemoHackathonWorkflow.Message(role=assistant, content=5), logprobs=null, finish_reason=stop)]
            logger.info("openApiResponse(1): {} ", openApiResponse.choices.stream().findFirst());
            Integer i = openApiResponse.choices
                    .stream()
                    .findFirst()
                    .map(Choice::getMessage)
                    .map(Message::getContent)
                    .map(Integer::parseInt)
                    .orElse(-1);
            demoAggregate.setJokeScore(i);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void updateJokeData(
            final DemoAggregate demo,
            final String taskId,
            final String jokeData) {

        demo.setJokeData(jokeData);
        processService.completeUserTask(demo, taskId);
    }

    public int getJokeScore(
            final DemoAggregate demo,
            final String taskId) {

        return demo.getJokeScore();
    }

    public void completeJokeEvaluation(
            final DemoAggregate demo,
            final String taskId) {

        processService.completeUserTask(demo, taskId);
    }

}
