/*
 * PlaceWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.application.WebServiceParkidia;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import com.parkidia.modeles.place.PlaceId;
import com.parkidia.modeles.place.statut.IStatut;
import com.parkidia.modeles.place.statut.Statut;
import com.parkidia.services.ParkingService;
import com.parkidia.services.PlaceService;
import com.parkidia.services.StatutService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Représente le web service permettant de gérer les places de parking.
 */
@Path("/place")
public class PlaceWebService {

    /**
     * Le service permettant d'accéder aux parkings.
     */
    @EJB
    private ParkingService parkingService;

    /**
     * Le service permettant d'accéder aux places.
     */
    @EJB
    private PlaceService placeService;

    /**
     * Le service permettant d'accéder aux statuts des places.
     */
    @EJB
    private StatutService statutService;

    /**
     * Ajoute une nouvelle place à un parking.
     * @param idParking l'identifiant du parking où ajouter la place.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param nom le nom de la place de parking.
     * @param handicapee si la place est une place handicapée ou non.
     * @param latitude la latitude du parking.
     * @param longitude la longitude du parking.
     * @param orientation l'orientation de la place sur la photo.
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
    @Path("/{idParking}/{cle}/{nom}/{handicapee}/{latitude}/{longitude" +
          "}/{orientation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response creerPlace(@PathParam("idParking") int idParking,
                               @PathParam("cle") String cle,
                               @PathParam("nom") String nom,
                               @PathParam("handicapee") boolean handicapee,
                               @PathParam("latitude") double latitude,
                               @PathParam("longitude") double longitude,
                               @PathParam("orientation") int orientation) {

        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Le parking avec l'identifiant \"" +
                            idParking + "\" n'existe pas.").build();
            // Bonne clé.
        } else if (! cle.equals(parking.getCle())) {
            return Response.status(WebServiceParkidia.HTTP_ERR_CLE_INVALIDE)
                           .type(MediaType.TEXT_PLAIN_TYPE)
                           .entity("Vous n'avez pas le droit " +
                                   "de créer une" +
                                   " place pour ce parking : clé " +
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
        IPlace place = new Place(nom, parking, handicapee, latitude, longitude,
                                 orientation);

        // Créé un premier statut.
        IStatut statut = new Statut(place, true, null);
        place.ajouterStatut(statut);

        // Ajoute la place au parking.
        parking.ajouterPlace(place);

        // Créé la place.
        placeService.creerPlace(place);
        parkingService.majParking(parking);

        return Response
                .ok(place, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    /**
     * Ajoute un statut à la place de parking passée en argument.
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
    @Path("/ajouterStatut/{idParking}/{cle}/{nomPlace}/{dispo" +
          "}/{couleurVoiture}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterStatut(@PathParam("idParking") int idParking,
                                  @PathParam("cle") String cle,
                                  @PathParam("nomPlace") String nomPlace,
                                  @PathParam("dispo") boolean dispo,
                                  @PathParam("couleurVoiture")
                                          String couleurVoiture) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .header("Access-Control-Allow-Origin", "*")
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

        // Récupère la place.
        IPlace place = placeService.getPlace(new PlaceId(nomPlace, idParking));

        // Elle n'existe pas.
        if (place == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PLACE_INEXISTANTE)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Cette place n'existe pas.")
                    .build();
        }

        // Ajout du statut.
        IStatut statut = new Statut(place, dispo, couleurVoiture
                                                          .equals("null")
                                                  ? null
                                                  : couleurVoiture);

        place.ajouterStatut(statut);
        statutService.creerStatut(statut);
        placeService.majPlace(place);

        place.calculerDernierStatut();
        parking.calculerPlaces();

        return Response
                .ok(place, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    /**
     * Modifie la place d'un parking.
     * @param idParking l'identifiant du parking où modifier la place.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param nom le nom de la place de parking.
     * @param handicapee si la place est une place handicapée ou non.
     * @param latitude la latitude du parking.
     * @param longitude la longitude du parking.
     * @param orientation l'orientation de la place sur la photo.
     * @return Une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT}
     * : Si le parking n'existe pas.</li>
     * <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>{@link
     * WebServiceParkidia#HTTP_ERR_PLACE_INEXISTANTE} : Si la place n'existe
     * pas.</li><li>200 avec le statut créé en JSON si l'ajout c'est bien
     * passé.</li> </ul>
     */
    @PUT
    @Path("/{idParking}/{cle}/{nom}/{handicapee}/" +
          "{latitude}/{longitude}/{orientation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response majPlace(@PathParam("idParking") int idParking,
                             @PathParam("cle") String cle,
                             @PathParam("nom") String nom,
                             @PathParam("handicapee") boolean handicapee,
                             @PathParam("latitude") double latitude,
                             @PathParam("longitude") double longitude,
                             @PathParam("orientation") int orientation) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Le parking avec l'identifiant \"" +
                            idParking + "\" n'existe pas.").build();
            // Bonne clé.
        } else if (! cle.equals(parking.getCle())) {
            return Response.status(WebServiceParkidia.HTTP_ERR_CLE_INVALIDE)
                           .type(MediaType.TEXT_PLAIN_TYPE)
                           .entity("Vous n'avez pas le droit " +
                                   "d'enregistrer un" +
                                   " statut pour cette place : clé " +
                                   "incorrecte.")
                           .build();
        }
        // Récupère la place.
        IPlace place = placeService.getPlace(new PlaceId(nom, idParking));

        // Elle n'existe pas.
        if (place == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PLACE_INEXISTANTE)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Cette place n'existe pas.")
                    .build();
        }

        // Modifie.
        place.setHandicapee(handicapee);
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        place.setOrientation(orientation);

        placeService.majPlace(place);

        return Response
                .ok(place, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    /**
     * Supprime la place dont le nom est passé en argument.
     * @param idParking l'identifiant du parking où modifier la place.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param nom le nom de la place à supprimer.
     * @return une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT}
     * : si le parking n'existe pas.</li>
     * <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>{@link
     * WebServiceParkidia#HTTP_ERR_PLACE_INEXISTANTE} : Si la place n'existe
     * pas.</li><li>200 si la place a bien été supprimé.</li> </ul>
     */
    @DELETE
    @Path("/{idParking}/{cle}/{nom}")
    public Response supprimerPlace(@PathParam("idParking") int idParking,
                                   @PathParam("cle") String cle,
                                   @PathParam("nom") String nom) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Le parking avec l'identifiant \"" + idParking +
                            "\" n'existe pas.").build();
            // Bonne clé.
        } else if (! cle.equals(parking.getCle())) {
            return Response.status(WebServiceParkidia.HTTP_ERR_CLE_INVALIDE)
                           .type(MediaType.TEXT_PLAIN_TYPE)
                           .entity("Vous n'avez pas le droit " +
                                   "d'enregistrer un" +
                                   " statut pour cette place : clé " +
                                   "incorrecte.")
                           .build();
        }

        // Récupère la place.
        IPlace place = placeService.getPlace(new PlaceId(nom, idParking));

        // Elle n'existe pas.
        if (place == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PLACE_INEXISTANTE)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Cette place n'existe pas.")
                    .build();
        }

        // Supprime.
        parking.supprimerPlace(place);
        placeService.supprimerPlace(place);

        parking.calculerPlaces();

        return Response.ok()
                       .build();
    }
}
