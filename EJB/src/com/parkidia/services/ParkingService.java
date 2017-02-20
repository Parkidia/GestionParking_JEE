/*
 * ParkingService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOParking;
import com.parkidia.modeles.parking.IParking;

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
     * Créé un nouveau parking dans la base de données.
     * @param parking le parking a créer.
     */
    public void creerParking(IParking parking) {
        daoParking.creer(parking);
    }

    /**
     * Met à jour dans la base de données le parking passé en argument.
     * @param parking le parking à mettre à jour.
     * @return le parking mis à jour.
     */
    public IParking majParking(IParking parking) {
        return daoParking.maj(parking);
    }

    /**
     * Supprime le parking passé en argument de la base de données.
     * @param parking le parking à supprimer.
     */
    public void supprimerParking(IParking parking) {
        daoParking.supprimer(parking);
    }

    /**
     * Retourne le parking avec l'identifiant passé en argument.
     * @param id l'identifiant du parking.
     * @return le parking dont l'identifiant correspond à celui passé en
     * argument, {@code null} si rien n'a été trouvé.
     */
    public IParking getParking(int id) {
        return daoParking.rechercher(id);
    }

    /**
     * Retourne la liste des parkings gérés.
     * @return la liste des parkings gérés.
     */
    public List<IParking> listeParkings() {
        return daoParking.rechercherTous();
    }
}
