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
}
