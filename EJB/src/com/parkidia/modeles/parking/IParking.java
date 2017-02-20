/*
 * IParking.java
 */
package com.parkidia.modeles.parking;

import com.parkidia.modeles.localisation.ILocalisation;
import com.parkidia.modeles.place.IPlace;

import java.util.List;

/**
 * Définis un parking composées de places.
 */
public interface IParking extends ILocalisation {

    /**
     * Ajoute une place à ce parking (prend en compte la realtion
     * bidirectionnelle {@code Parking <--> Place}.
     * @param place la place à ajouter à ce parking.
     * @return True si l'ajout a bien été exécuté, False sinon.
     */
    boolean ajouterPlace(IPlace place);

    /**
     * Supprime une place à ce parking (prend en compte la realtion
     * bidirectionnelle {@code Parking <--> Place}.
     * @param place la place à supprimer à ce parking.
     * @return True si la suppression a bien été exécuté, False sinon.
     */
    boolean supprimerPlace(IPlace place);

    /**
     * Calcule et met à jour le nombre de places et le nombre de places libres.
     */
    void calculerPlaces();

    /**
     * @return l'identifiant du parking.
     */
    int getId();

    /**
     * Modifie l'identifiant du parking.
     * @param id le nouvel identifiant du parking.
     */
    void setId(int id);

    /**
     * @return le nom du parking.
     */
    String getNom();

    /**
     * Modifie le nom du parking.
     * @param nom le nouveau nom du parking.
     */
    void setNom(String nom);

    /**
     * @return la clé pour pouvoir modifier le parking.
     */
    String getCle();

    /**
     * Modifie la clé pour pouvoir modifier le parking.
     * @param cle la nouvelle clé pour pouvoir modifier le parking.
     */
    void setCle(String cle);

    /**
     * @return les places du parking.
     */
    List<IPlace> getPlaces();

    /**
     * Modifie les places du parking.
     * @param places les nouvelles places du parking.
     */
    void setPlaces(List<IPlace> places);

    /**
     * @return le nombre de places que possède le parking.
     */
    int getNbPlaces();

    /**
     * @return le nombre de places libres que possède le parking actuellement.
     */
    int getNbPlacesLibres();
}
