/*
 * IParking.java
 */
package com.parkidia.modeles.parking;

import com.parkidia.modeles.IEntity;
import com.parkidia.modeles.localisation.ILocalisable;
import com.parkidia.modeles.place.IPlace;

import java.util.List;

/**
 * Représente un parking.
 */
public interface IParking extends IEntity, ILocalisable {

    /**
     * @return l'identifiant de ce parking dans la base de données.
     */
    int getId();

    /**
     * Modifie l'identifiant de ce parking dans la base de données.
     * @param id le nouvel identifiant.
     */
    void setId(int id);

    /**
     * @return le nom de ce parking.
     */
    String getNom();

    /**
     * Modifie le nom de ce parking.
     * @param nom le nouveau nom du parking.
     */
    void setNom(String nom);

    /**
     * Retourne les places du parking.
     * @return les places de ce parking.
     */
    List<IPlace> getPlaces();

    /**
     * Modifie les places de ce parking.
     * @param places les nouvelle places de ce parking.
     */
    void setPlaces(List<IPlace> places);

    /**
     * @return la clé de connexion à ce parking pour une RaspBerry Pi.
     */
    String getCle();

    /**
     * Modifie la clé de connexion à ce parking pour une RaspBerry Pi.
     * @param cle la nouvelle clé de connexion.
     */
    void setCle(String cle);

    /**
     * @return le nombre de places que possède ce parking.
     */
    int getNbPlaces();

    /**
     * @return le nombre de places libres que le parking compte actuellement.
     */
    int getNbPlacesLibres();

    /**
     * Ajoute une place à ce parking (tient en compte de la relation
     * bidirectionnelle => {@code Parking <--> Place}, c'est-à-dire ajoute ce
     * parking comme le parking de la place).
     * @param place la place à ajouter à ce parking.
     * @return True si l'ajout a bien eu lieu, False sinon.
     */
    boolean ajouterPlace(IPlace place);

    /**
     * Supprime une place à ce parking.
     * @param place la place à supprimer à ce parking.
     * @return True si la suppression a bien eu lieu, False sinon.
     */
    boolean supprimerPlace(IPlace place);

    /**
     * Calcule le nombre de places du parking ainsi que le nombre de places
     * libres.
     */
    void calculerPlaces();
}
