/*
 * DAOParking.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;

import javax.ejb.Singleton;
import java.util.List;

/**
 * DAO permettant de gérer les parkings dans la base de données.
 */
@Singleton
public class DAOParking extends AbstractDAO<IParking> {

    /**
     * Recherche un parking dont l'identifiant est passé en argument.
     * @param id l'identifiant du parking.
     * @return le parking trouvé.
     */
    public IParking rechercher(int id) {
        return em.find(Parking.class, id);
    }

    /**
     * Recherche tous les parkings gérés.
     * @return la liste des parkings.
     */
    public List<IParking> rechercherTous() {
        return em.createQuery("select p from Parking p", IParking.class)
                 .getResultList();
    }
}
