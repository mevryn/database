package hibernate.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement(name="deck")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "DECK")
public class Deck implements Serializable {

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", nameOfDeck='" + nameOfDeck + '\'' +
                ", strategy=" + strategy +
                ", owner=" + owner +
                '}';
    }

    @Id @GeneratedValue
    @Column(name="id")
    private int id;
    @Column(name="nameofdeck")
    private String nameOfDeck;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="Strategy",referencedColumnName = "id")
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfDeck() {
        return nameOfDeck;
    }

    public void setNameOfDeck(String nameOfDeck) {
        this.nameOfDeck = nameOfDeck;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch =FetchType.LAZY)
    private Player owner;
}
