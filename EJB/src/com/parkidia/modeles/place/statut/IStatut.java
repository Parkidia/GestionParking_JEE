/*
 * IStatut.java
 */
package com.parkidia.modeles.place.statut;

import com.parkidia.modeles.IEntity;
import com.parkidia.modeles.place.IPlace;

import java.util.Calendar;

/**
 * Définis le statut d'une place.
 */
public interface IStatut extends IEntity {

    /**
     * @return la place dont ce statut porte sur.
     */
    IPlace getPlace();

    /**
     * Modifie la place de ce statut.
     * @param place le nouvelle place de ce statut.
     */
    void setPlace(IPlace place);

    /**
     * @return la date où ce statut à été enregistré.
     */
    Calendar getDate();

    /**
     * Modifie la date de ce statut.
     * @param date la nouvelle date du statut.
     */
    void setDate(Calendar date);

    /**
     * @return si la place été disponible à ce moment là.
     */
    boolean getDisponible();

    /**
     * Modifie la disponibilité de la place à ce moment là.
     * @param disponible le nouvelle disponibilité de la place.
     */
    void setDisponible(boolean disponible);

    /**
     * @return la couleur de la voiture qui été garée si la place n'était pas
     * disponible (RGB).
     */
    String getCouleurVoiture();

    /**
     * Modifie la couleur de la voiture qui été garée si la place n'était pas
     * disponible (RGB).
     * @param couleur la nouvelle couleur de la voiture.
     */
    void setCouleurVoiture(String couleur);
}
