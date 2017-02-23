/*
 * Parking.java
 */
package com.parkidia.modeles.parking;

import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * Représente un parking composées de places.
 */
@Entity
public class Parking extends Localisation implements IParking {

    /** L'identifiant du parking. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Le nom du parking. */
    @Column(nullable = false)
    private String nom;

    /** La clé permettant de modifier le parking. */
    @Column(nullable = false, unique = true)
    private String cle;

    /** Les places du parking. */
    @OneToMany(targetEntity = Place.class, mappedBy = "parking",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<IPlace> places;

    /** Le nombre de places du parking. */
    @Transient
    private int nbPlaces;

    /** Le nombre de places libres actuellement dans le parking. */
    @Transient
    private int nbPlacesLibres;

    /** Créé un nouveau parking. */
    public Parking() {
        this.places = new Vector<>();
        this.cle = UUID.randomUUID().toString();
        this.nbPlaces = this.nbPlacesLibres = 0;
    }

    /**
     * Créé un nouveau parking.
     * @param nom le nom du parking.
     * @param latitude la latitude du parking.
     * @param longitude la longitude du parking.
     */
    public Parking(String nom, double latitude, double longitude) {
        super(latitude, longitude);
        this.nom = nom;
        this.places = new Vector<>();
        this.cle = UUID.randomUUID().toString();
        this.nbPlaces = this.nbPlacesLibres = 0;
    }

    @Override
    public boolean ajouterPlace(IPlace place) {
        if (! place.getParking().equals(this)) {
            place.setParking(this);
        }
        boolean ajout = getPlaces().add(place);
        return ajout;
    }

    @Override
    public boolean supprimerPlace(IPlace place) {
        if (place.getParking().equals(this)) {
            place.setParking(null);
        }
        boolean suppression = getPlaces().remove(place);
        return suppression;
    }

    @Override
    public void calculerPlaces() {
        this.nbPlaces = getPlaces().size();
        this.nbPlacesLibres = 0;

        for (IPlace place : getPlaces()) {
            if (place.getDernierStatut().getDisponible()) {
                this.nbPlacesLibres++;
            }
        }
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
    public String getCle() {
        return cle;
    }

    @Override
    public void setCle(String cle) {
        this.cle = cle;
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
    public int getNbPlaces() {
        return nbPlaces;
    }

    @Override
    public int getNbPlacesLibres() {
        return nbPlacesLibres;
    }
}
