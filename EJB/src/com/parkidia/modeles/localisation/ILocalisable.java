/*
 * ILocalisable.java
 */
package com.parkidia.modeles.localisation;

/**
 * Représente un objet localisable sur Terre.
 */
public interface ILocalisable {

    /**
     * @return la localisation de l'objet.
     */
    ILocalisation getLocalisation();

    /**
     * Modifie la localisation de l'objet.
     * @param localisation la nouvelle localisation de l'objet.
     */
    void setLocalisation(ILocalisation localisation);
}
