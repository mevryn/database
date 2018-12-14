package hibernate.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@Entity
@Table(name="STRATEGY")
public class Strategy {
    @Id @GeneratedValue
    private int id;
    @Column(name = "strategy")
    private String strategy;

    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", strategy='" + strategy + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }

}
