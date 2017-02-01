/*
 * ILocalisation.java
 */
package com.parkidia.modeles.localisation;

import com.parkidia.modeles.IEntity;

/**
 * Représente la localisation d'un objet sur Terre par sa longitude et sa
 * latitude.o
 */
public interface ILocalisation extends IEntity {

    /**
     * @return l'identifiant de cette localisation dans la base de données.
     */
    int getId();

    /**
     * Modifie l'identifiant de cette localisation dans la base de données.
     * @param id le nouvel identifiant.
     */
    void setId(int id);

    /**
     * @return la latitude de cette localisation.
     */
    double getLatitude();

    /**
     * Modifie la latitude de cette localisation.
     * @param latitude la nouvelle latitude de cette localisation.
     */
    void setLatitude(double latitude);

    /**
     * @return la latitude de cette localisation.
     */
    double getLongitude();

    /**
     * Modifie la longitude de cette localisation.
     * @param longitude la nouvelle longitude de cette localisation.
     */
    void setLongitude(double longitude);
}
