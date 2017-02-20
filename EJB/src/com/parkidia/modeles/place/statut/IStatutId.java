/*
 * IStatutId.java
 */
package com.parkidia.modeles.place.statut;

import com.parkidia.modeles.place.PlaceId;

import java.util.Calendar;

/**
 * Définis la classe permettant de modéliser la clé primaire d'un statut.
 */
public interface IStatutId {

    /**
     * @return l'identifiant de la place dont ce statut porte sur.
     */
    PlaceId getPlace();

    /**
     * Modifie l'identifiant de la place de ce statut.
     * @param place le nouvel identifiant de la place de ce statut.
     */
    void setPlace(PlaceId place);

    /**
     * @return la date où ce statut à été enregistré.
     */
    Calendar getDate();

    /**
     * Modifie la date de ce statut.
     * @param date la nouvelle date du statut.
     */
    void setDate(Calendar date);
}
