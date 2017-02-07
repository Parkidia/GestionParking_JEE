/*
 * PlaceService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOPlace;
import com.parkidia.dao.DAOStatut;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.statut.IStatut;
import com.parkidia.modeles.place.statut.Statut;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Le service permettant de gérer les places de parking.
 */
@Stateless
public class PlaceService {

    /**
     * DAO permettant de récupèrer / insérer / modifier des éléments Place
     * dans la base de données.
     */
    @Inject
    private DAOPlace daoPlace;

    /**
     * DAO permettant de récupèrer / insérer / modifier des éléments Statut
     * dans la base de données.
     */
    @Inject
    private DAOStatut daoStatut;

    /**
     * Créé une nouvelle place de parking dans la base de données.
     * @param place la place de parking a créer.
     */
    public void creerPlace(IPlace place) {
        daoPlace.creer(place);

        // On créé un statut de base.
        IStatut dernierStatut = new Statut(place, true, null);
        place.setDernierStatut(dernierStatut);
        daoStatut.creer(dernierStatut);
    }

    /**
     * Ajoute un nouveau statut à une place.
     * @param statut le nouveau statut de la place.
     */
    public void ajouterStatut(IStatut statut) {
        daoStatut.creer(statut);
    }
}
