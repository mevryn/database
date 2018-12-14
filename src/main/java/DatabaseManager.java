import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import hibernate.model.*;
import hibernate.queries.Queries;
import hibernate.temp.PreparedMatch;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;

import javax.persistence.EntityManager;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class DatabaseManager {

    EntityManager entityManager;
    Queries queries;

    public DatabaseManager(EntityManager entityManager,Queries queries) throws Exception {
        this.entityManager = entityManager;
        this.queries = queries;
        this.entityManager.getTransaction().begin();
        this.loadPlayersFromXML("PlayersInput.xml");
        this.loadResultsFromXML("ResultsInput.xml");
        this.entityManager.getTransaction().commit();
    }

    public Strategy addStrategy(String strategy) {
        Strategy definedStrategy = new Strategy();
        definedStrategy.setStrategy(strategy);
        return definedStrategy;
    }

    public void saveDatabase() throws Exception {
        saveMatchesToJson(queries.findAllMatches());
        savePlayerToJson(queries.findAllPlayers());
        saveDecksToJson(queries.findAllDecks());
        saveStrategiesToJson(queries.findAllStrategies());
        saveResultsToJson(queries.findAllResults());
        savePlayersToXML(queries.findAllPlayers());
        saveResultsToXML(queries.findAllResults());
        saveStrategiesToXML(queries.findAllStrategies());
        saveMatchesToXML(queries.findAllMatches());
        saveDecksToXML(queries.findAllDecks());
    }

    public Match addMatch(Player player1, Player player2, Result result) {
        Match match = new Match();
        match.setPlayer(player1);
        match.setOpponent(player2);
        match.setResult(result);
        return match;
    }

    public Deck addDeck(String nameOfDeck) {
        Deck deck = new Deck();
        deck.setNameOfDeck(nameOfDeck);
        return deck;
    }

    public Player addPlayerToDatabase(String name, String surname) {
        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        return player;
    }

    public void saveResultsToJson(List<Result> results) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(results);
        FileWriter fileWriter = new FileWriter("ResultsOutput.json");
        fileWriter.append(json);
        fileWriter.close();
    }

    public void saveStrategiesToJson(List<Strategy> strategies) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(strategies);
        FileWriter fileWriter = new FileWriter("StrategiesOutput.json");
        fileWriter.append(json);
        fileWriter.close();
    }

    public void saveMatchesToJson(List<Match> matches) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(matches);
        FileWriter fileWriter = new FileWriter("MatchesOutput.json");
        fileWriter.append(json);
        fileWriter.close();
    }

    public void savePlayerToJson(List<Player> player) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        FileWriter fileWriter = new FileWriter("PlayersOutput.json");
        fileWriter.append(json);
        fileWriter.close();
    }

    public void saveDecksToJson(List<Deck> decks) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(decks);
        FileWriter fileWriter = new FileWriter("DecksOutput.json");
        fileWriter.append(json);
        fileWriter.close();
    }

    public void savePlayersToXML(List<Player> players) throws Exception {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("PlayersOutput.xml")));
        encoder.writeObject(players);
        encoder.close();
    }

    public void saveDecksToXML(List<Deck> decks) throws Exception {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("DecksOutput.xml")));
        encoder.writeObject(decks);
        encoder.close();
    }

    public void saveResultsToXML(List<Result> results) throws Exception {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ResultsOutput.xml")));
        encoder.writeObject(results);
        encoder.close();
    }

    public void saveMatchesToXML(List<Match> matches) throws Exception {
        List<PreparedMatch> preparedMatches = new ArrayList<>();
        for (Match match : matches) {
            PreparedMatch preparedMatch = new PreparedMatch();
            preparedMatch.setId(match.getId());
            preparedMatch.setPlayer(match.getPlayer());
            preparedMatch.setOpponent(match.getOpponent());
            preparedMatch.setResult(match.getResult());
            preparedMatch.setParsedDateTime(match.getDateTime());
            preparedMatches.add(preparedMatch);
        }
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("MatchesOutput.xml")));
        encoder.writeObject(preparedMatches);
        encoder.close();
    }

    public void saveStrategiesToXML(List<Strategy> strategies) throws Exception {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("StrategiesOutput.xml")));
        encoder.writeObject(strategies);
        encoder.close();
    }

    public void loadPlayersFromXML(String filePath) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
        List<Player> players = (List<Player>) decoder.readObject();
        for (Player player : players) {
            entityManager.merge(player);
        }
    }

    public void loadDecksFromXML(String filePath) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
        List<Deck> decks = (List<Deck>) decoder.readObject();
        for (Deck deck : decks) {
            entityManager.merge(deck);
        }

    }

    public void loadMatchesFromXML(String filePath) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
        List<PreparedMatch> preparedMatches = (List<PreparedMatch>) decoder.readObject();
        List<Match> matches = new ArrayList<>();
        for (PreparedMatch preparedMatch : preparedMatches) {
            Match match = new Match();
            match.setId(preparedMatch.getId());
            match.setPlayer(preparedMatch.getPlayer());
            match.setOpponent(preparedMatch.getOpponent());
            match.setResult(preparedMatch.getResult());
            if (preparedMatch.getDateTime() == null) {
                match.setDateTime(ZonedDateTime.now().minus(Period.ofDays(3).minus(Period.ofMonths(4))));
            } else {
                match.setDateTime(preparedMatch.timeToZoned());
            }
            matches.add(match);
        }

        for (Match match : matches) {
            entityManager.merge(match);
        }

    }

    public void loadResultsFromXML(String filePath) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
        List<Result> results = (List<Result>) decoder.readObject();
        for (Result result : results) {
            entityManager.merge(result);
        }

    }
    public void loadStrategiesFromJSON(String filePath) throws Exception{
        Gson gson =new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type targetClassType = new TypeToken<ArrayList<Strategy>>(){}.getType();
        ArrayList<Strategy> strategies = gson.fromJson(reader,targetClassType);
        for (Strategy strategy:strategies){
            entityManager.merge(strategy);
        }
        out.println(queries.findAllStrategies());
    }
    public void loadMatchesFromJSON(String filePath) throws Exception {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type targetClassType = new TypeToken<ArrayList<PreparedMatch>>() {
        }.getType();
        ArrayList<PreparedMatch> preparedMatches = gson.fromJson(reader, targetClassType);
        List<Match> matches = new ArrayList<>();
        for(PreparedMatch preparedMatch: preparedMatches){
            entityManager.merge(preparedMatch.getResult());
        }
        entityManager.flush();
        out.println(queries.findAllResults());
        for (PreparedMatch preparedMatch : preparedMatches) {
            Match match = new Match();
            match.setId(preparedMatch.getId());
            match.setPlayer(preparedMatch.getPlayer());
            match.setOpponent(preparedMatch.getOpponent());
            match.setResult(preparedMatch.getResult());
            if (preparedMatch.getDateTime() == null) {
                match.setDateTime(ZonedDateTime.now().minus(Period.ofDays(3).minus(Period.ofMonths(4))));
            } else {
                match.setDateTime(preparedMatch.timeToZoned());
            }
            matches.add(match);
        }

        for (Match match : matches) {
            entityManager.merge(match);
        }
    }
    public void loadDecksFromJSON(String filePath) throws  Exception{
        Gson gson =new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type targetClassType = new TypeToken<ArrayList<Deck>>(){}.getType();
        ArrayList<Deck> decks = gson.fromJson(reader,targetClassType);
        for (Deck deck:decks){
            entityManager.merge(deck);
        }
    }
}
