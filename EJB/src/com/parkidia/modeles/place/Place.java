/*
 * Place.java
 */
package com.parkidia.modeles.place;

import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.modeles.place.statut.IStatut;
import com.parkidia.modeles.place.statut.Statut;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;

@Entity
@IdClass(PlaceId.class)
public class Place extends Localisation implements IPlace {

    /** Le nom de la place. */
    @Id
    private String nom;

    /** Le parking sur lequel cette place est présente. */
    @Id
    @ManyToOne
    @JsonBackReference
    private Parking parking;

    /** Si cette place est handicapée. */
    @Column(nullable = false)
    private boolean handicapee;

    /** L'orientation de la place sur la parking. */
    @Column(nullable = false)
    private int orientation;

    /** Les différents statuts de cette place. */
    @OneToMany(targetEntity = Statut.class, mappedBy = "place",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<IStatut> statuts;

    /** Le dernier statut de cette place. */
    @Transient
    private IStatut dernierStatut;

    /** Créé une nouvelle place de parking. */
    public Place() {
        this.statuts = new Vector<>();
    }

    /**
     * Créé une nouvelle place de parking.
     * @param parking le parking dans lequel cette place est présente.
     * @param handicapee si cette place est une place handicapée.
     * @param latitude la latitude de la place.
     * @param longitude la longitude de la place.
     * @param orientation l'orientation de la place.
     */
    public Place(String nom, IParking parking, boolean handicapee,
                 double latitude, double longitude, int orientation) {
        super(latitude, longitude);
        this.nom = nom;
        this.handicapee = handicapee;
        this.orientation = orientation;
        this.setParking(parking);
        this.statuts = new Vector<>();
    }

    @Override
    public boolean ajouterStatut(IStatut statut) {
        if (! statut.getPlace().equals(this)) {
            statut.setPlace(this);
        }
        this.calculerDernierStatut();
        return getStatuts().add(statut);
    }

    @PostLoad
    @Override
    public void calculerDernierStatut() {
        this.dernierStatut = getStatuts().isEmpty() ? null : getStatuts()
                .get(getStatuts().size() - 1);
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
    public Parking getParking() {
        return parking;
    }

    @Override
    public void setParking(IParking parking) {
        if (parking != null && ! parking.getPlaces().contains(this)) {
            parking.getPlaces().add(this);
        }
        this.parking = (Parking) parking;
    }

    @Override
    public List<IStatut> getStatuts() {
        return statuts;
    }

    @Override
    public void setStatuts(List<IStatut> statuts) {
        this.statuts = statuts;
    }

    @Override
    public IStatut getDernierStatut() {
        return dernierStatut;
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
    public int getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object obj) {
        // Même instance.
        if (obj == this) {
            return true;
        }

        // Même type.
        if (! (obj instanceof Place)) {
            return false;
        }

        // Cast.
        Place place = (Place) obj;

        return nom.equals(place.getNom()) && getParking() != null
               && place.getParking() != null
               && getParking().getId() == place.getParking().getId();
    }
}
