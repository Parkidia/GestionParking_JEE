/*
 * Parking.java
 */
package com.parkidia.modeles.parking;

import com.parkidia.modeles.AbstractEntity;
import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;

import javax.persistence.*;
import java.util.List;

/**
 * Représente un parking géré par l'application.
 */
@Entity
public class Parking extends AbstractEntity implements IParking {

    /** L'identifiant du parking dans la base de données. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Le nom du parking. */
    @Column(nullable = false)
    private String nom;

    /** Les coordonnées GPS su parking. */
    @OneToOne(targetEntity = Localisation.class, optional = false,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private ILocalisation localisation;

    /** Les places de ce parking. */
    @OneToMany(targetEntity = Place.class, mappedBy = "parking",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<IPlace> places;

    /** Créé un parking. */
    public Parking() {
    }

    /**
     * Créé un nouveau parking avec son nom.
     * @param nom le nom du parking.
     */
    public Parking(String nom) {
        this.nom = nom;
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
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public List<IPlace> getPlaces() {
        return places;
    }

    @Override
    public void setPlaces(List<IPlace> places) {
        this.places = places;
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
    public boolean ajouterPlace(IPlace place) {
        if (! place.getParking().equals(this)) {
            place.setParking(this);
        }
        return getPlaces().add(place);
    }

    @Override
    public boolean supprimerPlace(IPlace place) {
        if (place.getParking().equals(this)) {
            place.setParking(null);
        }
        return getPlaces().remove(place);
    }
}
