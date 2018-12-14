import hibernate.model.Match;
import hibernate.model.Player;
import hibernate.model.Result;
import hibernate.queries.Queries;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.crypto.Data;

import java.time.ZonedDateTime;

import static java.lang.System.out;
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
    public void loadStrategiesFromJSON() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadStrategiesFromJSON("StrategiesInput.json");
        assertEquals(1,queries.findAllStrategies().size());
        entityManager.close();
    }
    @Test
    public void loadMatchesFromJSON() throws Exception{
        entityManager.getTransaction().begin();
        manager.loadMatchesFromJSON("MatchesInput.json");
        assertEquals(1,queries.findAllMatches().size());
        entityManager.close();
    }
    @Test
    public void loadDecksFromJSON() throws Exception {
        entityManager.getTransaction().begin();
        manager.loadDecksFromJSON("DecksInput.json");
        assertEquals(1,queries.findAllStrategies().size());
        entityManager.close();
    }
    @Test
    public void getResultsFromDatabase(){
        out.println(queries.findAllResults());
    }
}