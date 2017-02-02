/*
 * Parking.java
 */
package com.parkidia.modeles.parking;

import com.parkidia.modeles.AbstractEntity;
import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import com.parkidia.modeles.raspberry.IRaspberryPi;
import com.parkidia.modeles.raspberry.RaspBerryPi;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;

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
            fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<IPlace> places;

    /** La RaspBerry qui surveille ce parking. */
    @OneToOne(targetEntity = RaspBerryPi.class, optional = false,
            orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private IRaspberryPi raspberry;

    /** Créé un parking. */
    public Parking() {
        places = new Vector<>();
    }

    /**
     * Créé un nouveau parking avec son identifiant.
     * @param id l'identifiant du parking.
     */
    public Parking(int id) {
        this();
        this.id = id;
    }

    /**
     * Créé un nouveau parking avec son nom, sa localisation et la RaspBerry Pi
     * qui le filme.
     * @param nom le nom du parking.
     * @param localisation la localisation du parking.
     * @param raspberry la RaspBerry Pi qui le filme.
     */
    public Parking(String nom, ILocalisation localisation,
                   IRaspberryPi raspberry) {
        this();
        this.nom = nom;
        this.localisation = localisation;
        this.raspberry = raspberry;
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
    public IRaspberryPi getRaspberry() {
        return raspberry;
    }

    @Override
    public void setRaspberry(IRaspberryPi raspberry) {
        this.raspberry = raspberry;
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
