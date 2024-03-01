package demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEMO")
public class DemoAggregate {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "SUCCESS")
    private boolean success;

    @Column(name = "JOKE_DATA")
    private String jokeData;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJokeData() {
        return jokeData;
    }

    public void setJokeData(String jokeData) {
        this.jokeData = jokeData;
    }
}
