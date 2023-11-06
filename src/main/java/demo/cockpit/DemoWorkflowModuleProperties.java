package demo.cockpit;

import io.vanillabp.springboot.modules.WorkflowModuleIdAwareProperties;
import io.vanillabp.springboot.modules.WorkflowModuleProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = DemoWorkflowModuleProperties.WORKFLOW_MODULE_ID)
public class DemoWorkflowModuleProperties  implements WorkflowModuleIdAwareProperties {
    public static final String WORKFLOW_MODULE_ID = "demo";

    @Bean
    public WorkflowModuleProperties DemoWorkflowModuleProperties() {
        return new WorkflowModuleProperties(DemoWorkflowModuleProperties.class, WORKFLOW_MODULE_ID);
    }

    @Override
    public String getWorkflowModuleId() {
        return WORKFLOW_MODULE_ID;
    }
}
