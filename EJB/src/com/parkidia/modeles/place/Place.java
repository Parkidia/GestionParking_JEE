/*
 * Place.java
 */
package com.parkidia.modeles.place;

import com.parkidia.modeles.AbstractEntity;
import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.modeles.place.statut.IStatut;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

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
    private int orientation;

    /** Si cette place est handicapée. */
    @Column(nullable = false)
    private boolean handicapee;

    @Transient
    @JsonManagedReference
    private IStatut dernierStatut;

    /** Le parking où est située cette place. */
    @Id
    @ManyToOne(targetEntity = Parking.class, fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, optional = false)
    @JsonBackReference
    private IParking parking;

    @OneToOne(targetEntity = Localisation.class, optional = false,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private ILocalisation localisation;

    /** Créé une nouvelle place. */
    public Place() {
    }

    /**
     * Créé une nouvelle place de parking.
     * @param nom le nom de la place.
     * @param orientation l'orientation de la place.
     * @param handicapee si cette place est une place handicapée.
     * @param parking le parking dans lequel cette place est présente.
     * @param localisation la localisation de cette place.
     */
    public Place(String nom, int orientation, boolean handicapee,
                 IParking parking,
                 ILocalisation localisation) {
        this.nom = nom;
        this.orientation = orientation;
        this.handicapee = handicapee;
        this.parking = parking;
        this.localisation = localisation;
    }

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
    public int getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(int orientation) {
        this.orientation = orientation;
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

    @Override
    public IStatut getDernierStatut() {
        return dernierStatut;
    }

    @Override
    public void setDernierStatut(IStatut dernierStatut) {
        this.dernierStatut = dernierStatut;
    }
}
