/*
 * LocalisationParking.java
 */
package com.parkidia.modeles.localisation;

import com.parkidia.modeles.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Représente la localisation d'un parking par sa latitude et longitude.
 */
@MappedSuperclass
public class Localisation extends AbstractEntity implements ILocalisation {

    /** La latitude de la localisation. */
    @Column(nullable = false)
    private double latitude;

    /** La longitude de la localisation. */
    @Column(nullable = false)
    private double longitude;

    /** Créé une nouvelle localisation d'un parking à (0, 0). */
    public Localisation() {
    }

    /**
     * Créé une nouvelle localisation d'un parking.
     * @param latitude la latitude du parking.
     * @param longitude la longitude du parking.
     */
    public Localisation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
