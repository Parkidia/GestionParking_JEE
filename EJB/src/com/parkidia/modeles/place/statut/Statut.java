/*
 * Statut.java
 */
package com.parkidia.modeles.place.statut;

import com.parkidia.modeles.AbstractEntity;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Représente le statut d'une place.
 */
@Entity
@IdClass(StatutId.class)
public class Statut extends AbstractEntity implements IStatut {

    /** La date où ce statut à été enregistré. */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    /** La place dont ce statut porte sur. */
    @Id
    @ManyToOne(targetEntity = Place.class)
    @JsonBackReference
    private IPlace place;

    /** Si la place été disponible à ce moment là. */
    @Column(nullable = false)
    private boolean disponible;

    /**
     * La couleur de la voiture qui été garée si la place n'était pas
     * disponible (RGB).
     */
    @Column
    private String couleurVoiture;

    /** Créé un nouveau statut de place. */
    public Statut() {
        this.date = Calendar.getInstance();
    }

    /**
     * Créé un nouveau statut de place.
     * @param place la place dont ce statut porte sur.
     * @param disponibilite si la place été disponible à ce moment là.
     * @param couleurVoiture la couleur de la voiture qui été garée si la place
     * n'était pas disponible (RGB).
     */
    public Statut(IPlace place, boolean disponibilite, String couleurVoiture) {
        this.place = place;
        this.date = Calendar.getInstance();
        this.disponible = disponibilite;
        this.couleurVoiture = couleurVoiture;
    }

    @Override
    public IPlace getPlace() {
        return place;
    }

    @Override
    public void setPlace(IPlace place) {
        if (! place.getStatuts().contains(this)) {
            place.getStatuts().add(this);
        }
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
    public boolean getDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String getCouleurVoiture() {
        return couleurVoiture;
    }

    @Override
    public void setCouleurVoiture(String couleur) {
        this.couleurVoiture = couleur;
    }

}
