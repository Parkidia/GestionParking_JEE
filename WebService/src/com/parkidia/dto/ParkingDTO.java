/*
 * ParkingDTO.java
 */
package com.parkidia.dto;

import com.parkidia.modeles.parking.IParking;

/**
 * Objet représentant un parking sans ces places pour être renvoyé
 * dans la liste des parkings.
 */
public class ParkingDTO {

    /** Le nom du parking. */
    private String nom;

    /** Le nombre de places du parking. */
    private int nbPlaces;

    /** Le nombre de places libres actuellement dans le parking. */
    private int nbPlacesLibres;

    /** La latitude du parking. */
    private double latitude;

    /** La longitude du parking. */
    private double longitude;

    /**
     * Créé un nouveau ParkingDTO avec le parking qu'il représente.
     * @param parking le parking qu'il représente.
     */
    public ParkingDTO(IParking parking) {
        nom = parking.getNom();
        nbPlaces = parking.getNbPlaces();
        nbPlacesLibres = parking.getNbPlacesLibres();
        latitude = parking.getLatitude();
        longitude = parking.getLongitude();
    }

    /**
     * @return le nom de ce parking.
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return le nombre de places que possède le parking.
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * @return le nombre de places libres que possède le parking actuellement.
     */
    public int getNbPlacesLibres() {
        return nbPlacesLibres;
    }

    /**
     * @return la latitude de ce parking.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return la latitude de ce parking.
     */
    public double getLongitude() {
        return longitude;
    }
}
