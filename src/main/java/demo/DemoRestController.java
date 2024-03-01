package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DemoRestController {

    @Autowired
    private DemoHackathonWorkflow demoHackathonWorkflow;
    
    @Autowired
    private DemoAggregateRepository demoAggregates;

    @GetMapping("/demo/{id}/process-task-completed/{taskId}")
    public ResponseEntity<Void> completeUserTask(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId) throws Exception {
        
        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        String convertedTaskId;
        if (taskId.startsWith("0x")) {
            convertedTaskId = taskId.substring(2);
        } else {
            try {
                convertedTaskId = Long.toHexString(Long.valueOf(taskId));
            } catch (Exception e) {
                convertedTaskId = taskId;
            }
        }
        
        demoHackathonWorkflow.completeUserTask(
                demoAggregate.get(),
                convertedTaskId);
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/request-demo/{id}")
    public void requestDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {
        
        demoHackathonWorkflow.requestDemo(id);
    }

    @GetMapping("/demo/{id}")
    public void runDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {
        
        demoHackathonWorkflow.startDemo(id);
    }

    @GetMapping("/continue-demo/{id}")
    public void continueDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {

        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        demoHackathonWorkflow.continueDemo(demoAggregate.get());
    }

    @PostMapping("/demo/{id}/task/{taskId}/update")
    public ResponseEntity<Void> updateTask(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId,
            @RequestBody final String jokeData) throws Exception {

        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        demoHackathonWorkflow.updateJokeData(
                demoAggregate.get(),
                taskId,
                jokeData);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/demo/{id}/task/{taskId}/score")
    public ResponseEntity<Integer> getScore(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId) throws Exception {

        final var demoAggregate = demoAggregates.findById(id);

        if (demoAggregate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(demoHackathonWorkflow.getJokeScore(demoAggregate.get(), taskId));
    }

    @PostMapping("/demo/{id}/task/{taskId}/complete")
    public ResponseEntity<Void> updateTask(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId) throws Exception {

        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        demoHackathonWorkflow.completeJokeEvaluation(
                demoAggregate.get(),
                taskId);

        return ResponseEntity.ok().build();
    }

}
