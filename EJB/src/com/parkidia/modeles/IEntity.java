/*
 * IEntity.java
 */
package com.parkidia.modeles;

/**
 * Représente une entité persistable dans la base de données.
 */
public interface IEntity {

    /**
     * @return la version de l'entitté.
     */
    int getVersion();

    /**
     * Modifie la version de l'entité.
     * @param version le nouvelle version de l'entité.
     */
    void setVersion(int version);
}
