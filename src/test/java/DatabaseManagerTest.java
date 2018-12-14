import hibernate.model.Match;
import hibernate.model.Player;
import hibernate.model.Result;
import hibernate.queries.Queries;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.crypto.Data;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;
    DatabaseManager manager;
    Queries queries;
    public DatabaseManagerTest() throws Exception{
        entityManagerFactory =
                    Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();
            queries = new Queries(entityManager);
            manager = new DatabaseManager(entityManager,queries);
    }
    @Test
    public void saveToXML() throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(new Match(entityManager.find(Player.class,1),entityManager.find(Player.class,2),new Result("Jurkowska"),ZonedDateTime.now()));
        manager.saveDatabase();
        entityManager.getTransaction().commit();
    }
    @Test
    public void loadFromXML()throws Exception {
        entityManager.getTransaction().begin();
        manager.loadPlayersFromXML("PlayersInput.xml");
        manager.loadDecksFromXML("DecksInput.xml");
        manager.loadResultsFromXML("ResultsInput.xml");
        manager.loadMatchesFromXML("MatchesInput.xml");
        entityManager.getTransaction().commit();
        assertEquals(154, queries.findAllPlayers().size());
        assertEquals(2,queries.findAllDecks().size());
         assertEquals(1,queries.findAllResults().size());
        assertEquals(1,queries.findAllMatches().size());
        entityManager.close();
    }


}