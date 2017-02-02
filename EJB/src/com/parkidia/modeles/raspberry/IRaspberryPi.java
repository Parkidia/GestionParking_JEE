/*
 * IRaspberryPi.java
 */
package com.parkidia.modeles.raspberry;

import com.parkidia.modeles.IEntity;

/**
 * Représente la Raspberry Pi sur laquelle le serveur doit demander les
 * informations sur l'état d'un parking.
 */
public interface IRaspberryPi extends IEntity {

    /**
     * @return l'identifiant de cette Raspberry dans la base de données.
     */
    int getId();

    /**
     * Modifie l'identifiant de cette Raspberry dans la base de données.
     * @param id le nouvel identifiant.
     */
    void setId(int id);

    /**
     * @return le nom de de cette Raspberry.
     */
    String getNom();

    /**
     * Modifie le nom de cette Raspberry.
     * @param nom le nouveau nom de cette Raspberry.
     */
    void setNom(String nom);

    /**
     * @return l'adresse hôte pour contacter la Raspberry.
     */
    String getHote();

    /**
     * Modifie l'adresse hôte pour contacter la Raspberry.
     * @param hote la nouvelle adresse hôte pour contacter la Raspberry.
     */
    void setHote(String hote);

    /**
     * @return le port sur lequel contacter la Raspberry.
     */
    int getPort();

    /**
     * Modifie le port sur lequel contacter la Raspberry.
     * @param port le nouveau port sur lequel contacter la Raspberry.
     */
    void setPort(int port);
}
