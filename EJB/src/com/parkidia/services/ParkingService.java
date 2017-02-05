/*
 * ParkingService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOParking;
import com.parkidia.dao.DAOPlace;
import com.parkidia.dao.DAOStatut;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.modeles.place.IPlace;

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
    private DAOParking daoParking;

    /**
     * DAO permettant de récupèrer / insérer / modifier des éléments Statut
     * dans la base de données.
     */
    @Inject
    private DAOStatut daoStatut;

    /**
     * Créé un nouveau parking dans la base de données.
     * @param parking le parking a créer.
     */
    public void creerParking(IParking parking) {
        daoParking.creer(parking);
    }

    /**
     * Retourne le parking avec l'identifiant passé en argument.
     * @param id l'identifiant du parking.
     * @return le parking dont l'identifiant correspond à celui passé en
     * argument, {@code null} si rien n'a été trouvé.
     */
    public IParking getParking(int id) {
        IParking parking = new Parking(id);
        parking = daoParking.rechercher(parking);

        // On ajoute les dernier statut.
        for (IPlace place : parking.getPlaces()) {
            place.setDernierStatut(daoStatut.dernierStatut(place));
        }

        // Calcule le nombre de places. places et les places libres.
        parking.calculerPlaces();

        return daoParking.rechercher(parking);
    }

    /**
     * Retourne la liste des parkings gérés.
     * @return la liste des parkings gérés.
     */
    public List<IParking> listeParkings() {
        List<IParking> parkings = daoParking.rechercherTous();

        for (IParking parking : parkings) {

            // On ajoute les dernier statut.
            for (IPlace place : parking.getPlaces()) {
                place.setDernierStatut(daoStatut.dernierStatut(place));
            }

            // Calcule le nombre de places. places et les places libres.
            parking.calculerPlaces();
        }

        return parkings;
    }
}
