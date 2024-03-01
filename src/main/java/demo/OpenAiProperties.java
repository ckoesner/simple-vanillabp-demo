package demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {
    private String hostName;
    private String apiEndpoint;
    private String apiKeyBearerToken;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getApiKeyBearerToken() {
        return apiKeyBearerToken;
    }

    public void setApiKeyBearerToken(String apiKeyBearerToken) {
        this.apiKeyBearerToken = apiKeyBearerToken;
    }
}
