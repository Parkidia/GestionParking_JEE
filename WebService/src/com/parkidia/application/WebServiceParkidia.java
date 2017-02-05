/*
 * WebServiceParkidia.java
 */
package com.parkidia.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Classe permettant de définir une nouvelle application de web services
 * pour Jersey (implémentation de JAX-RS).
 */
@ApplicationPath(WebServiceParkidia.RACINE)
public class WebServiceParkidia extends Application {

    /** Le chemin racine de l'application. */
    public static final String RACINE = "/";

    /**
     * Code d'erreur HTTP envoyé quand on essaie d'accéder à un parking
     * inexistant.
     */
    public static final int HTTP_ERR_PARKING_INEXISTANT = 510;

    /**
     * Code d'erreur HTTP envoyé quand on essaie d'accéder à une place de
     * parking inexistante.
     */
    public static final int HTTP_ERR_PLACE_INEXISTANTE = 512;

    /**
     * Code d'erreur HTTP envoyé quand on essaie de créer une place qui existe
     * déjà.
     */
    public static final int HTTP_ERR_PLACE_EXISTANTE = 513;

    /**
     * Code d'erreur HTTP envoyé quand on essaie de modifier un parking mais la
     * clé donnée est invalide.
     */
    public static final int HTTP_ERR_CLE_INVALIDE = 514;
}
