/*
 * PlacesWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.application.WebServiceParkidia;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.statut.IStatut;
import com.parkidia.modeles.place.statut.Statut;
import com.parkidia.services.ParkingService;
import com.parkidia.services.PlaceService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Représente le web service permettant de gérer les places de parking.
 */
@Path("/place")
public class PlacesWebService {

    /**
     * Le service permettant d'accéder aux parkings.
     */
    @EJB
    private ParkingService parkingService;

    /**
     * Le service permettant d'accéder aux places de parking.
     */
    @EJB
    private PlaceService placeService;

    /**
     * Ajoute un statut à la plae de parking passée en argument.
     * @param idParking l'identifiant du parking où est la place.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param nomPlace le nom de la place de parking.
     * @param dispo la disponibilité de la place.
     * @param couleurVoiture le couleur de la voiture présente sur la place.
     * @return Une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT}
     * : Si le parking n'existe pas.</li>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>{@link
     * WebServiceParkidia#HTTP_ERR_PLACE_INEXISTANTE} : Si la place n'existe pas
     * dans le parking.</li>
     *     <li>200 avec le statut créé en JSON si l'ajout
     * c'est bien passé.</li>
     * </ul>
     */
    @POST
    @Path("/ajouterStatut/{idParking}/{nomPlace}/{cle}/{dispo" +
          "}/{couleurVoiture}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterStatut(@QueryParam("idParking") int idParking,
                                  @QueryParam("cle") String cle,
                                  @QueryParam("nomPlace") String nomPlace,
                                  @QueryParam("dispo") boolean dispo,
                                  @QueryParam("couleurVoiture")
                                  @DefaultValue("null")
                                          String couleurVoiture) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Le parking avec l'identifiant \"" +
                            idParking + "\" n'existe pas.").build();
            // Pas la bonne clé.
        } else if (! parking.getCle().equals(cle)) {
            return Response.status(WebServiceParkidia.HTTP_ERR_CLE_INVALIDE)
                           .type(MediaType.TEXT_PLAIN_TYPE)
                           .entity("Vous n'avez pas le droit d'enregistrer un" +
                                   " statut pour cette place : clé incorrecte.")
                           .build();
        }

        // Recherche la place.
        for (IPlace place : parking.getPlaces()) {
            if (place.getNom().equals(nomPlace)) {
                // Ajoute le statut.
                IStatut statut = new Statut(place, dispo, couleurVoiture
                                                                  .equals("null")
                                                          ? null
                                                          : couleurVoiture);

                placeService.ajouterStatut(statut);

                // Retourne le statut.
                return Response.ok(statut, MediaType.APPLICATION_JSON_TYPE)
                               .build();
            }
        }

        // PPlace non trouvée.
        return Response.status(WebServiceParkidia.HTTP_ERR_PLACE_INEXISTANTE)
                       .type(MediaType.TEXT_PLAIN_TYPE)
                       .entity("La place " + nomPlace +
                               " n'existe pas dans le parking " +
                               parking.getNom())
                       .build();
    }
}
