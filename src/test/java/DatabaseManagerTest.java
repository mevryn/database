import hibernate.model.Player;
import hibernate.queries.Queries;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DatabaseManagerTest {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;
    DatabaseManager manager;
    Queries queries;

  public DatabaseManagerTest() throws Exception {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("hibernate-dynamic");
        entityManager = entityManagerFactory.createEntityManager();
        queries = new Queries(entityManager);
        manager = new DatabaseManager(entityManager, queries);
    }

    @Test
    public void pageQuery() {
      List<Player> playersPages = queries.getAllPlayersWithPages(0);
      for(Player player:playersPages){
          out.println(player.getId());
      }
      out.println(queries.findAllPlayers());
    }
    @Test
    public void savePlayersToJSON() throws Exception{
        entityManager.getTransaction().begin();
        manager.loadPlayersFromJSON("PlayersInput.json");
        manager.saveMatchesToJson(queries.findAllMatches());
        entityManager.close();
    }
    @Test
    public void loadPlayersFromJSON() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadPlayersFromJSON("PlayersInput.json");
        assertEquals(194, queries.findAllPlayers().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
     @Test
    public void saveMatchesToJSON() throws Exception{
        entityManager.getTransaction().begin();
        manager.loadDecksFromJSON("MatchesInput.json");
        manager.saveMatchesToJson(queries.findAllMatches());
        entityManager.close();
    }

    @Test
    public void loadMatchesFromJSON() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadMatchesFromJSON("MatchesInput.json");
        assertEquals(1, queries.findAllMatches().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void saveDecksToJSON() throws Exception{
        entityManager.getTransaction().begin();
        manager.loadDecksFromJSON("DecksInput.json");
        manager.saveDecksToJson(queries.findAllDecks());
        entityManager.close();
    }
    @Test
    public void loadDecksFromJSON() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadDecksFromJSON("DecksInput.json");
        assertEquals(6, queries.findAllStrategies().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void saveDecksToXML() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadDecksFromJSON("DecksInput.json");
        manager.saveDecksToXML(queries.findAllDecks());
        entityManager.close();
    }

    @Test
    public void loadDecksFromXML() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadDecksFromXML("DecksInput.xml");
        assertEquals(6, queries.findAllStrategies().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
      @Test
    public void loadPlayersFromXML() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadPlayersFromXML("PlayersInput.xml");
        assertEquals(194, queries.findAllPlayers().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
 @Test
    public void saveMatchesToXML() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadMatchesFromXML("MatchesInput.xml");
        manager.saveMatchesToXML(queries.findAllMatches());
        entityManager.close();
    }

    @Test
    public void loadMatchesFromXML() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadMatchesFromXML("MatchesInput.xml");
        assertEquals(1, queries.findAllMatches().size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}