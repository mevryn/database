package hibernate.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Entity
@Table(name = "MATCH")
public class Match implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="result",referencedColumnName = "id")
    private Result result;
    @Column(name = "date")
    private ZonedDateTime dateTime;

    public LocalDateTime getParsedDateTime() {
        return parsedDateTime;
    }

    public void setParsedDateTime(LocalDateTime parsedDateTime) {
        this.parsedDateTime = parsedDateTime;
    }

    private LocalDateTime parsedDateTime;

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
    public void setParsedDateTime(){
        parsedDateTime = dateTime.toLocalDateTime();
    }
    public Match() {
        dateTime=ZonedDateTime.now();
    }
    public void setDateTime(ZonedDateTime dateTime){
        this.dateTime = dateTime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Player opponent;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Match(Player player, Player opponent, Result result, ZonedDateTime dateTime) {
        this.player = player;
        this.result = result;
        this.dateTime = dateTime;
        this.opponent = opponent;
    }
}
