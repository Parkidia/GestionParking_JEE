/*
 * IPlace.java
 */
package com.parkidia.modeles.place;

import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.statut.IStatut;

import java.util.List;

/**
 * Définis une place de parking.
 */
public interface IPlace extends ILocalisation {

    /**
     * Ajoute un nouveau statut à cette place.
     * @param statut le statut à ajouter.
     * @return True si l'ajout a été exécuté avec succès, False sinon.
     */
    boolean ajouterStatut(IStatut statut);

    /**
     * Détermine le dernier statut de cette place.
     */
    void calculerDernierStatut();

    /** Retourne le nom de la place. */
    String getNom();

    /**
     * Modifie le nom de la place de parking.
     * @param nom le nouveau nom de la place.
     */
    void setNom(String nom);

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

    /**
     * @return les statuts des places.
     */
    List<IStatut> getStatuts();

    /**
     * Modifie les statuts de cette place.
     * @param statuts les nouveaux statuts.
     */
    void setStatuts(List<IStatut> statuts);


    /**
     * @return le dernier statut de cette place.
     */
    IStatut getDernierStatut();

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
     * @return l'orientation de la place sur le parking.
     */
    int getOrientation();

    /**
     * Modifie l'orientation de la place sur le parking.
     * @param orientation la nouvelle orientation de la place.
     */
    void setOrientation(int orientation);
}
