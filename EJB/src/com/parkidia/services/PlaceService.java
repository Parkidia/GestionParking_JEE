/*
 * PlaceService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOPlace;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.IPlaceId;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Le service permettant de gérer les places de parking.
 */
@Stateless
public class PlaceService {

    /**
     * DAO permettant de récupèrer / insérer / modifier des place de parking
     * dans la base de données.
     */
    @Inject
    private DAOPlace daoPlace;

    /**
     * Créé une nouvelle place dans la base de données.
     * @param place la place a créer.
     */
    public void creerPlace(IPlace place) {
        daoPlace.creer(place);
    }

    /**
     * Met à jour dans la base de données la place passé en argument.
     * @param place la place à mettre à jour.
     * @return la place mise à jour.
     */
    public IPlace majPlace(IPlace place) {
        return daoPlace.maj(place);
    }

    /**
     * Supprime la place passé en argument de la base de données.
     * @param place la place à supprimer.
     */
    public void supprimerPlace(IPlace place) {
        daoPlace.supprimer(place);
    }

    /**
     * Retourne la place avec l'identifiant passé en argument.
     * @param id l'identifiant de la place.
     * @return la place dont l'identifiant correspond à celui passé en
     * argument, {@code null} si rien n'a été trouvé.
     */
    public IPlace getPlace(IPlaceId id) {
        return daoPlace.rechercher(id);
    }
}
