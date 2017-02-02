/*
 * RaspBerryPi.java
 */
package com.parkidia.modeles.raspberry;

import com.parkidia.modeles.AbstractEntity;

import javax.persistence.*;

/**
 * Représente la Raspberry Pi sur laquelle le serveur doit demander les
 * informations sur l'état d'un parking.
 */
@Entity
public class RaspBerryPi extends AbstractEntity implements IRaspberryPi {

    /** L'identifiant de la Raspberry dans la base de données. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Le nom de la Raspberry. */
    @Column(nullable = false)
    private String nom;

    /** Le nom d'hôte sur lequel contacter de la Raspberry. */
    @Column(nullable = false)
    private String hote;

    /** Le port sur lequel contacter de la Raspberry. */
    @Column(nullable = false)
    private int port;

    /** Créé une nouvelle Raspberry Pi pour un parking. */
    public RaspBerryPi() {
    }

    /**
     * Créé une nouvelle Raspberry Pi pour un parking.
     * @param nom le nom de la Raspberry.
     * @param hote le nom d'hôte sur lequel contacter de la Raspberry.
     * @param port le port sur lequel contacter de la Raspberry.
     */
    public RaspBerryPi(String nom, String hote, int port) {
        this.nom = nom;
        this.hote = hote;
        this.port = port;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getHote() {
        return hote;
    }

    @Override
    public void setHote(String hote) {
        this.hote = hote;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }
}
