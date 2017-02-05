/*
 * ParkingWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.application.WebServiceParkidia;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import com.parkidia.services.ParkingService;
import com.parkidia.services.PlaceService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Représente le web service permettant de gérer les parkings.
 */
@Path("/parking")
public class ParkingWebService {

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
     * @return la liste de tous les parkings sous la forme d'un tableau en JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IParking> getTousParking() {
        // La liste des parkings.
        List<IParking> parkings = parkingService.listeParkings();

        // On enlève les places.
        for (IParking parking : parkings) {
            parking.setPlaces(null);
        }

        return parkings;
    }

    /**
     * Retourne le parking dont l'identifiant est passé en argument au format
     * JSON.
     * @param id l'identifiant du parking.
     * @return une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT}
     * : si le parking n'existe pas.</li> <li>200 et le parking au format JSON,
     * s'il existe.</li> </ul>
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParking(@PathParam("id") int id) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(id);

        // S'il n'existe pas.
        if (parking == null) {
            return Response
                    .status(WebServiceParkidia.HTTP_ERR_PARKING_INEXISTANT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Le parking avec l'identifiant \"" + id +
                            "\" n'existe pas.").build();
        } else {
            return Response
                    .ok(parking, MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

    /**
     * Créé un nouveau parking.
     * @param nom le nom du parking.
     * @param latitude la latitude du parking.
     * @param longitude la longitude du parking.
     * @return le parking créé.
     */
    @POST
    @Path("/{nom}/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public IParking creerParking(@PathParam("nom") String nom,
                                 @PathParam("latitude") double latitude,
                                 @PathParam("longitude") double longitude) {
        // Créé le parking.
        IParking parking =
                new Parking(nom, new Localisation(latitude, longitude));

        // On le persiste.
        parkingService.creerParking(parking);

        // On le retourne.
        return parking;
    }

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
     *     <li>{@link WebServiceParkidia#HTTP_ERR_PLACE_EXISTANTE}
     * : Si la place existe déjà dans ce parking.</li>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>200 avec le statut créé
     * en JSON si l'ajout c'est bien passé.</li> </ul>
     */
    @POST
    @Path("/ajouterPlace/{idParking}/{cle}/{nom}/{handicapee}/{orientation" +
          "}/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterPlace(@PathParam("idParking") int idParking,
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

            // Persiste.
            placeService.creerPlace(place);

            return Response
                    .ok(place, MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }
}
