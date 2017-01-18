/*
 * ApplicationGestionParking.java
 */
package iut.lpmms.gestionParking;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Classe permettant de définir une nouvelle application de web services
 * pour Jersey (implémentation de JAX-RS).
 */
@ApplicationPath(ApplicationGestionParking.RACINE)
public class ApplicationGestionParking extends Application {

    /** Le chemin racine de l'application. */
    public static final String RACINE = "/";
}
