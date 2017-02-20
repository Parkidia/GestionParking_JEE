/*
 * PlaceId.java
 */
package com.parkidia.modeles.place;

import java.io.Serializable;

/**
 * Représente la clé primaire d'une place.
 */
public class PlaceId implements IPlaceId, Serializable {

    /** Le nom de la place. */
    private String nom;

    /** L'identifiant parking où la place est présente. */
    private int parking;

    /** Créé une nouvelle clé primaire pour une place. */
    public PlaceId() {
    }

    /**
     * Créé une nouvelle clé primaire pour une place.
     * @param nom le nom de la place.
     * @param parking l'identifiant du parking où cette place est présente.
     */
    public PlaceId(String nom, int parking) {
        this.nom = nom;
        this.parking = parking;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int getParking() {
        return parking;
    }

    @Override
    public void setParking(int parking) {
        this.parking = parking;
    }

    @Override
    public boolean equals(Object obj) {
        // Même instance.
        if (obj == this) {
            return true;
        }

        // Même type.
        if (! (obj instanceof PlaceId)) {
            return false;
        }

        // Cast.
        PlaceId id = (PlaceId) obj;

        return id.getNom().equals(getNom()) && id.getParking() == getParking();
    }
}
