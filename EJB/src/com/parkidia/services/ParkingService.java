/*
 * ParkingService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOParking;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Le service permettant de gérer les parkings.
 */
@Stateless
public class ParkingService {

    /**
     * DAO permettant de récupèrer / insérer / modifier des éléments Parking
     * dans la base de données.
     */
    @Inject
    private DAOParking dao;

    /**
     * Créé un nouveau parking dans la base de données.
     * @param parking le parking a créer.
     */
    public void creerParking(IParking parking) {
        dao.creer(parking);
    }

    /**
     * Retourne le parking avec l'identifiant passé en argument.
     * @param id l'identifiant du parking.
     * @return le parking dont l'identifiant correspond à celui passé en
     * argument, {@code null} si rien n'a été trouvé.
     */
    public IParking getParking(int id) {
        IParking parking = new Parking();
        parking.setId(id);

        return dao.rechercher(parking);
    }

    /**
     * Retourne la liste des parkings gérés.
     * @return la liste des parkings gérés.
     */
    public List<IParking> listeParkings() {
        return dao.rechercherTous();
    }
}
