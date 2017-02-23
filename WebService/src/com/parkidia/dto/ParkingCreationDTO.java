/*
 * ParkingCreationDTO.java
 */
package com.parkidia.dto;

import com.parkidia.modeles.parking.IParking;

/**
 * Objet représentant un parking sans ces places pour être renvoyé
 * quand on créé un parking.
 */
public class ParkingCreationDTO {

    /** L'identifiant du parking. */
    private int id;

    /** Le nom du parking. */
    private String nom;

    /** La clé permettant de modifier un parking. */
    private String cle;

    /** La latitude du parking. */
    private double latitude;

    /** La longitude du parking. */
    private double longitude;

    /**
     * Créé un nouveau ParkingCreationDTO avec le parking qu'il
     * représente.
     * @param parking le parking qu'il représente.
     */
    public ParkingCreationDTO(IParking parking) {
        id = parking.getId();
        nom = parking.getNom();
        cle = parking.getCle();
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
     * @return la clé permettant de modifier le parking.
     */
    public String getCle() {
        return cle;
    }

    /**
     * @return le nom de ce parking.
     */
    public String getNom() {
        return nom;
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
