/*
 * DAOParking.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * DAO permettant de gérer les parkings dans la base de données.
 */
@Singleton
public class DAOParking extends AbstractDAO<IParking> {

    /** L'objet gérant la persistance des données. */
    @PersistenceContext(unitName = "Parking")
    private EntityManager em;

    @Override
    public void creer(IParking entite) {
        em.persist(entite);
    }

    @Override
    public IParking maj(IParking entite) {
        return em.merge(entite);
    }

    @Override
    public void supprimer(IParking entite) {
        em.detach(entite);
    }

    @Override
    public IParking rechercher(IParking entite) {
        return em.find(Parking.class, entite.getId());
    }

    @Override
    public List<IParking> rechercherTous() {
        return em.createQuery("select p from Parking p", IParking.class)
                 .getResultList();
    }
}
