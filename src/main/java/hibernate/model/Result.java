package hibernate.model;

import javax.persistence.*;

@Entity
@Table(name="RESULT")
public class Result {
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "Winner")
    private String winner;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", winner='" + winner + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public Result() {
    }

    public Result(String winner) {
        this.winner = winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
