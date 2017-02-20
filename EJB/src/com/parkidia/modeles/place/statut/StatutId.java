/*
 * StatutId.java
 */
package com.parkidia.modeles.place.statut;

import com.parkidia.modeles.place.PlaceId;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Représente la clé primaire d'un statut.
 */
public class StatutId implements IStatutId, Serializable {

    /** La date où ce statut à été enregistré. */
    private Calendar date;

    /** L'identifiant de la place dont ce statut porte sur. */
    private PlaceId place;

    @Override
    public PlaceId getPlace() {
        return place;
    }

    @Override
    public void setPlace(PlaceId place) {
        this.place = place;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        // Même instance.
        if (obj == this) {
            return true;
        }

        // Même type.
        if (! (obj instanceof StatutId)) {
            return false;
        }

        // Cast.
        StatutId id = (StatutId) obj;

        return id.getDate().equals(getDate()) &&
               id.getPlace().equals(getPlace());
    }
}
