/*
 * Place.java
 */
package com.parkidia.modeles.place;

import com.parkidia.modeles.AbstractEntity;
import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;

import javax.persistence.*;

/**
 * Représente une place de d'un parking.
 */
@Entity
public class Place extends AbstractEntity implements IPlace {

    /** Le nom de la place. */
    @Id
    private String nom;

    /** L'orientationde la place par rapport à la photo du parking. */
    @Column(nullable = false)
    private double orientation;

    /** Si cette place est handicapée. */
    @Column(nullable = false)
    private boolean handicapee;

    /** Le parking où est située cette place. */
    @Id
    @ManyToOne(targetEntity = Parking.class, fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE, optional = false)
    private IParking parking;

    @OneToOne(targetEntity = Localisation.class, optional = false,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private ILocalisation localisation;

    @Override
    public ILocalisation getLocalisation() {
        return localisation;
    }

    @Override
    public void setLocalisation(ILocalisation localisation) {
        this.localisation = localisation;
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
    public double getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(double orientation) {

    }

    @Override
    public boolean getHandicapee() {
        return handicapee;
    }

    @Override
    public void setHandicapee(boolean handicapee) {
        this.handicapee = handicapee;
    }

    @Override
    public IParking getParking() {
        return parking;
    }

    @Override
    public void setParking(IParking parking) {
        this.parking = parking;
        if (! parking.getPlaces().contains(this)) {
            parking.getPlaces().add(this);
        }
    }
}
