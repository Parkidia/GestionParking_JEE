/*
 * ParkingDetailDTO.java
 */
package com.parkidia.dto;

import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.IPlace;

import java.util.List;

/**
 * Objet représentant un parking sans ces places pour être renvoyé
 * quand accède aux détails d'un parking.
 */
public class ParkingDetailsDTO {

    /** L'identifiant du parking. */
    private int id;

    /** Le nom du parking. */
    private String nom;

    /** Le nombre de places du parking. */
    private int nbPlaces;

    /** Le nombre de places libres actuellement dans le parking. */
    private int nbPlacesLibres;

    /** Les places du parking. */
    private List<IPlace> places;

    /** La latitude du parking. */
    private double latitude;

    /** La longitude du parking. */
    private double longitude;

    /**
     * Créé un nouveau ParkingDetailsDTO avec le parking qu'il représente.
     * @param parking le parking qu'il représente.
     */
    public ParkingDetailsDTO(IParking parking) {
        id = parking.getId();
        nom = parking.getNom();
        places = parking.getPlaces();
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
     * @return les places du parking.
     */
    public List<IPlace> getPlaces() {
        return places;
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
