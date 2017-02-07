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

@Entity
public class Statut extends AbstractEntity implements IStatut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(targetEntity = Place.class, optional = false)
    @JsonBackReference
    private IPlace place;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    @Column(nullable = false)
    private boolean disponibilite;

    @Column
    private String couleurVoiture;

    /** Créé un nouveau statut. */
    public Statut() {
    }

    /**
     * Créé un nouveau statut avec son identifiant.
     * @param id l'identifiant du statut.
     */
    public Statut(int id) {
        this.id = id;
    }

    /**
     * Créé un nouveau statut pour une place de parking, avec comme date
     * maintenant.
     * @param place la place concernée.
     * @param disponibilite la disponibilité de la place.
     * @param couleurVoiture la couleur de la voiture présent sur la place ou
     * <code>null</code> si la place est libre.
     */
    public Statut(IPlace place, boolean disponibilite,
                  String couleurVoiture) {
        this.place = place;
        this.date = Calendar.getInstance();
        this.disponibilite = disponibilite;
        this.couleurVoiture = couleurVoiture;
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
    public IPlace getPlace() {
        return place;
    }

    @Override
    public void setPlace(IPlace place) {
        this.place = place;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public void setDate(Calendar calendar) {
        this.date = date;
    }

    @Override
    public boolean getDisponible() {
        return disponibilite;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponibilite = disponible;
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
