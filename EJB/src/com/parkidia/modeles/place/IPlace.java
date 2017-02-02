/*
 * IPlace.java
 */
package com.parkidia.modeles.place;

import com.parkidia.modeles.IEntity;
import com.parkidia.modeles.localisation.ILocalisable;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.statut.IStatut;

/**
 * Représente une place de parking.
 */
public interface IPlace extends IEntity, ILocalisable {

    /** Retourne le nom de la place. */
    String getNom();

    /**
     * Modifie le nom de la place de parking.
     * @param nom le nouveau nom de la place.
     */
    void setNom(String nom);

    /**
     * @return si cette place est une place handicapée ou non.
     */
    boolean getHandicapee();

    /**
     * Modifie si cette place est une place handicapée ou non.
     * @param handicapee si cette place est une place handicapée ou non.
     */
    void setHandicapee(boolean handicapee);

    /**
     * @return l'orientation de la place de parking par rapport à la photo du
     * parking.
     */
    int getOrientation();

    /**
     * Modifie l'orientation de la place par rapport à la photo du
     * parking.
     * @param orientation la nouvelle orientation.
     */
    void setOrientation(int orientation);

    /**
     * @return le parking dans laquelle est présente cette place.
     */
    IParking getParking();

    /**
     * Modifie le parking dans lequel cette place est présente (tient en compte
     * la relation bidirectionnelle => {@code Parking <--> Place}, ajoute cette
     * place au parking).
     * @param parking le parking ou cette place est présente maintenant.
     */
    void setParking(IParking parking);

    IStatut getDernierStatut();

    void setDernierStatut(IStatut statut);
}
