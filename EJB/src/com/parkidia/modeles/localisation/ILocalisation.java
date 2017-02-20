/*
 * ILocalisation.java
 */
package com.parkidia.modeles.localisation;

import com.parkidia.modeles.IEntity;

/**
 * Définis la localisation d'un parking par sa latitude et longitude.
 */
public interface ILocalisation extends IEntity {

    /**
     * @return la latitude de ce parking.
     */
    double getLatitude();

    /**
     * Modifie la latitude de ce parking.
     * @param latitude la nouvelle latitude de ce parking.
     */
    void setLatitude(double latitude);

    /**
     * @return la latitude de ce parking.
     */
    double getLongitude();

    /**
     * Modifie la longitude de ce parking.
     * @param longitude la nouvelle longitude de ce parking.
     */
    void setLongitude(double longitude);
}
