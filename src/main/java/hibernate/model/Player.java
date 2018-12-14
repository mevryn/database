package hibernate.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
@XmlRootElement(name="player")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "PLAYER")
public class Player implements Serializable {
    @Id @GeneratedValue
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    transient private Set<Deck> decks = new HashSet<>();

    @OneToMany(mappedBy = "player",fetch = FetchType.LAZY)
    transient private Set<Match> matches =new HashSet<>();
    public Set<Deck> getDecks() {
        return decks;
    }

    public void setDecks(Set<Deck> decks) {
        this.decks = decks;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static Player copyPlayer(Player player){
        Player copiedPlayer = new Player();
        copiedPlayer.setName(player.getName()+Integer.toString(new Random().nextInt()));
        copiedPlayer.setSurname(player.getSurname());
        return copiedPlayer;
    }

    public Player(){
    }
}
