/*
 * PlacesWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.application.WebServiceParkidia;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
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
     * Ajoute une nouvelle place à un parking.
     * @param idParking l'identifiant du parking où ajouter la place.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param nom le nom de la place de parking.
     * @param handicapee si la place est une place handicapée ou non.
     * @param orientation l'orientation de la place sur la photo.
     * @param latitude la latitude de la place.
     * @param longitude la longitude de la place.
     * @return Une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT}
     * : Si le parking n'existe pas.</li>
     * <li>{@link WebServiceParkidia#HTTP_ERR_PLACE_EXISTANTE}
     * : Si la place existe déjà dans ce parking.</li> <li>{@link
     * WebServiceParkidia#HTTP_ERR_CLE_INVALIDE} : Si la clé donnée n'est pas la
     * bonne.</li> <li>200 avec le statut créé en JSON si l'ajout c'est bien
     * passé.</li> </ul>
     */
    @POST
    @Path("/{idParking}/{cle}/{nom}/{handicapee}/{orientation" +
          "}/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response creerPlace(@PathParam("idParking") int idParking,
                               @PathParam("cle") String cle,
                               @PathParam("nom") String nom,
                               @PathParam("handicapee") boolean handicapee,
                               @PathParam("orientation") int orientation,
                               @PathParam("latitude") double latitude,
                               @PathParam("longitude") double longitude) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Le parking avec l'identifiant \"" +
                            idParking + "\" n'existe pas.").build();
        } else {

            // Bonne clé.
            if (! cle.equals(parking.getCle())) {
                return Response.status(WebServiceParkidia.HTTP_ERR_CLE_INVALIDE)
                               .type(MediaType.TEXT_PLAIN_TYPE)
                               .entity("Vous n'avez pas le droit " +
                                       "d'enregistrer un" +
                                       " statut pour cette place : clé " +
                                       "incorrecte.")
                               .build();
            }

            // Place existe.
            for (IPlace place : parking.getPlaces()) {
                if (place.getNom().equalsIgnoreCase(nom)) {
                    return Response
                            .status(WebServiceParkidia.HTTP_ERR_PLACE_EXISTANTE)
                            .type(MediaType.TEXT_PLAIN_TYPE)
                            .entity("Cette place existe déjà dans ce parking.")
                            .build();
                }
            }

            // Créé la place.
            IPlace place = new Place(nom, orientation, handicapee, parking,
                                     new Localisation(latitude, longitude));
            parking.ajouterPlace(place);

            // Persiste.
            placeService.creerPlace(place);

            return Response
                    .ok(place, MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

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
     * <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>{@link
     * WebServiceParkidia#HTTP_ERR_PLACE_INEXISTANTE} : Si la place n'existe pas
     * dans le parking.</li> <li>200 avec le statut créé en JSON si l'ajout
     * c'est bien passé.</li> </ul>
     */
    @POST
    @Path("/ajouterStatut/{idParking}/{nomPlace}/{cle}/{dispo}/{couleurVoiture}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterStatut(@QueryParam("idParking") int idParking,
                                  @QueryParam("nomPlace") String nomPlace,
                                  @QueryParam("cle") String cle,
                                  @QueryParam("dispo") boolean dispo,
                                  @QueryParam("couleurVoiture") String couleurVoiture) {
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
