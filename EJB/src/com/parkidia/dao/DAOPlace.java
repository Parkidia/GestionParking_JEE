/*
 * DAOTache.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.IPlaceId;
import com.parkidia.modeles.place.Place;

import javax.ejb.Singleton;

/**
 * DAO permettant de gérer les places de parking dans la base de données.
 */
@Singleton
public class DAOPlace extends AbstractDAO<IPlace> {

    /**
     * Recherche une place dont l'identifiant est passé en argument.
     * @param id l'identifiant de la place.
     * @return la place trouvée ou <code>null</code> si aucune place n'a été
     * trouvée.
     */
    public IPlace rechercher(IPlaceId id) {
        return em.find(Place.class, id);
    }
}
