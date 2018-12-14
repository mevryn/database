import hibernate.queries.Queries;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Logger logger =Logger.getLogger("name");
        logger.info("message");
        EntityManager entityManager=null;
        Queries queries=new Queries(entityManager);
        EntityManagerFactory entityManagerFactory=null;
        try {
            entityManagerFactory =
                    Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();
            new DatabaseManager(entityManager,queries);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
        } finally {
            entityManagerFactory.close();
        }
    }
}
