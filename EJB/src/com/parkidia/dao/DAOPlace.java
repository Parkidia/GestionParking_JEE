/*
 * DAOPlace.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.place.IPlace;

import javax.ejb.Singleton;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO permettant de gérer les places de parking dans la base de données.
 */
@Singleton
public class DAOPlace extends AbstractDAO<IPlace> {

    @Override
    public void creer(IPlace entite) {
        em.persist(entite);
    }

    @Override
    public IPlace maj(IPlace entite) {
        return em.merge(entite);
    }

    @Override
    public void supprimer(IPlace entite) {
        em.detach(entite);
    }

    @Override
    public IPlace rechercher(IPlace entite) {
        TypedQuery<IPlace> query = em.createQuery(
                "select pl from Place pl where pl.nom = :nom " +
                "and pl.parking.id = :id", IPlace.class)
                                     .setParameter("nom", entite.getNom())
                                     .setParameter("id",
                                                   entite.getParking().getId());
        return query.getSingleResult();
    }

    @Override
    public List<IPlace> rechercherTous() {
        return em.createQuery("select p from Place p", IPlace.class)
                 .getResultList();
    }
}
