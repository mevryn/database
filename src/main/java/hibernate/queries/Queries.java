package hibernate.queries;

import hibernate.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Queries {
    EntityManager entityManager;
    public Queries(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    public List<Player> getPlayersByName(String name){
        TypedQuery<Player> query = entityManager.createQuery("SELECT p from Player p where p.surname LIKE :name", Player.class);
        return query.setParameter("name","%"+name+"%").getResultList();
    }
    public List<Player> getAllPlayersWithPages(int pagenr) {
        Query queryTotal = entityManager.createQuery
                ("Select count(p) from Player p");
        long countResult = (long)queryTotal.getSingleResult();
        Query query = entityManager.createQuery("Select p FROM Player p");
        int pageSize = 10;
        int pageNumber = (int) ((countResult / pageSize) + 1);
        if (pagenr > pageNumber) pagenr = pageNumber;
        query.setFirstResult((pagenr-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    public List<Result> findAllResults(){
        Query query = entityManager.createQuery("select r from Result r");
        return  query.getResultList();
    }
     public List<Strategy> findAllStrategies(){
        Query query = entityManager.createQuery("select s from Strategy s");
        return  query.getResultList();
    }
    public List<Match> findAllMatches(){
        Query query = entityManager.createQuery("select m from Match m");
        return  query.getResultList();
    }
     public List<Player> findAllPlayers(){
        Query query = entityManager.createQuery("select p from Player p");
        return  query.getResultList();
    }
     public List<Deck> findAllDecks(){
        Query query = entityManager.createQuery("select d from Deck d");
        return  query.getResultList();
    }
    public List<Match> getMatchesBetweenTwoPlayers (Player player1,Player player2){
        Query query = entityManager.createQuery("Select m from Match m where m.player ="
                +player1.getId()+
                " AND m.opponent = "+player2.getId()+" OR "+ "m.player ="
                +player2.getId()+
                " AND m.opponent = "+player1.getId());
        return query.getResultList();
    }
}
