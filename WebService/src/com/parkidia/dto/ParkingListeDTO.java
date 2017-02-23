/*
 * ParkingDTO.java
 */
package com.parkidia.dto;

import com.parkidia.modeles.parking.IParking;

/**
 * Objet représentant un parking sans ces places pour être renvoyé
 * dans la liste des parkings.
 */
public class ParkingListeDTO {

    /** L'identifiant du parking. */
    private int id;

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
     * Créé un nouveau ParkingListeDTO avec le parking qu'il représente.
     * @param parking le parking qu'il représente.
     */
    public ParkingListeDTO(IParking parking) {
        id = parking.getId();
        nom = parking.getNom();
        nbPlaces = parking.getNbPlaces();
        nbPlacesLibres = parking.getNbPlacesLibres();
        latitude = parking.getLatitude();
        longitude = parking.getLongitude();
    }

    /**
     * @return l'identifiant du parking.
     */
    public int getId() {
        return id;
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
