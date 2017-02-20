/*
 * IPlaceId.java
 */
package com.parkidia.modeles.place;

/**
 * Définis la classe permettant de modéliser la clé primaire d'une place.
 */
public interface IPlaceId {

    /** Retourne le nom de la place. */
    String getNom();

    /**
     * Modifie le nom de la place de parking.
     * @param nom le nouveau nom de la place.
     */
    void setNom(String nom);

    /**
     * @return l'identifiant du parking dans laquelle est présente cette place.
     */
    int getParking();

    /**
     * Modifie l' identifiant du parking dans lequel cette place est présente
     * (tient en compte la relation bidirectionnelle => {@code Parking <-->
     * Place}, ajoute cette place au parking).
     * @param parking le nom du parking ou cette place est présente maintenant.
     */
    void setParking(int parking);
}
