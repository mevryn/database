package hibernate.temp;
import hibernate.model.Player;
import hibernate.model.Result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
@XmlRootElement(name="match")
@XmlAccessorType(XmlAccessType.FIELD)
public class PreparedMatch {
    private int id;
    private Player player;
    private Player opponent;
    private Result result;
    private String parsedDateTime;

    public String getDateTime(){
        return  parsedDateTime;
    }
    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public String getParsedDateTime(){
        return parsedDateTime;
    }
    public ZonedDateTime timeToZoned() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(parsedDateTime);
        return zonedDateTime;
    }

    public void setParsedDateTime(ZonedDateTime parsedDateTime) {
        this.parsedDateTime = new String();
        String dateTime = DateTimeFormatter.ISO_DATE_TIME.format(parsedDateTime);
        this.parsedDateTime = dateTime;
    }
}
