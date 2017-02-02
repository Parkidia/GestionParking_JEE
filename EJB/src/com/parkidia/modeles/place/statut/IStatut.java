/*
 * IStatut.java
 */
package com.parkidia.modeles.place.statut;

import com.parkidia.modeles.IEntity;
import com.parkidia.modeles.place.IPlace;

import java.util.Calendar;

/**
 * Classe représentant le statut d'une place de parking à un instant T.
 */
public interface IStatut extends IEntity {

    /**
     * @return l'identifiant du statut.
     */
    int getId();

    /**
     * Modifie l'identifiant du statut dans la base de données.
     * @param id le nouvelle identifiant du statut.
     */
    void setId(int id);

    /**
     * @return la place sur laquelle le statut porte.
     */
    IPlace getPlace();

    /**
     * Modifie la place sur laquelle le statut porte.
     * @param place le nouvelle place.
     */
    void setPlace(IPlace place);

    /**
     * @return la date où ce statut à été enregistré.
     */
    Calendar getDate();

    /**
     * Modifie la date de ce statut.
     * @param calendar la nouvelle date du statut.
     */
    void setDate(Calendar calendar);

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
