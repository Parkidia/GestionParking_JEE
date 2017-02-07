/*
 * WebServiceParkidia.java
 */
package com.parkidia.application;

import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Classe permettant de définir une nouvelle application de web services
 * pour Jersey (implémentation de JAX-RS).
 */
@ApplicationPath(WebServiceParkidia.RACINE)
public class WebServiceParkidia extends ResourceConfig {

    /** Le chemin racine de l'application. */
    public static final String RACINE = "/";

    /** Le chemin absolu du dossier où sont chargées les images des parkings. */
    public static final String DOSSIER_IMAGE = "E:\\Parking\\";

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

    /**
     * Code d'erreur HTTP envoyé quand une erreur est survenue lors du
     * chargement d'une image sur le serveur.
     */
    public static final int HTTP_ERR_UPLOAD_IMAGE = 515;

    /**
     * Code d'erreur HTTP envoyé quand une erreur est survenue lors de la
     * lecture d'une image sur le serveur ou si l'image à lire n'existe pas.
     */
    public static final int HTTP_ERR_CHARGEMENT_IMAGE = 516;

    /** Créé le web service Parkidia. */
    public WebServiceParkidia() {
        // Définis la package contenant les classes de Web Services.
        packages("com.parkidia.webServices");

        // On charge le module permettant de gérer des formulaires multiparts.
        register(MultiPartFeature.class);

        // On charge le module avancé pour transformer des objets en JSON.
        register(Jackson1Feature.class);
    }
}
