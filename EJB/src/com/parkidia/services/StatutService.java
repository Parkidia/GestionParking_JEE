/*
 * StatutService.java
 */
package com.parkidia.services;

import com.parkidia.dao.DAOStatut;
import com.parkidia.modeles.place.statut.IStatut;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Le service permettant de gérer les statuts de places.
 */
@Stateless
public class StatutService {

    /**
     * DAO permettant de récupèrer / insérer / modifier des statuts de places
     * dans la base de données.
     */
    @Inject
    private DAOStatut daoStatut;

    /**
     * Créé un nouveau statut pour une place dans la base de données.
     * @param statut le statut a créer.
     */
    public void creerStatut(IStatut statut) {
        daoStatut.creer(statut);
    }
}
