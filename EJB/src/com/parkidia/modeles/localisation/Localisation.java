/*
 * Localisation.java
 */
package com.parkidia.modeles.localisation;

import com.parkidia.modeles.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

/**
 * Représente la localisation d'un objet sur Terre par sa latitude et longitude.
 */
@Entity
public class Localisation extends AbstractEntity implements ILocalisation {

    /** L'identifiant de la localisation en base de données. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    /** La latitude de la localisation. */
    @Column(nullable = false)
    private double latitude;

    /** La longitude de la localisation. */
    @Column(nullable = false)
    private double longitude;

    /** Créé une nouvelle localisation. */
    public Localisation() {
    }

    /**
     * Créé une nouvelle localisation avec sa latitude et longitude.
     * @param latitude la latitude.
     * @param longitude la longitude.
     */
    public Localisation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
