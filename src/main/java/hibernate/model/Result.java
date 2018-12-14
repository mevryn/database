package hibernate.model;

import javax.persistence.*;

@Entity
@Table(name="RESULT")
public class Result {
    @Id @GeneratedValue
    @Column(name = "id")
    int id;
    @Column(name = "Winner")
    String winner;

    public int getId() {
        return id;
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

    public void setWinner(Player winner) {
        this.winner = winner.getSurname();
    }
}
