/*
 * ParkingWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.Place;
import com.parkidia.modeles.raspberry.RaspBerryPi;
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
     * @return une réponse HTTP REST contenant les informations du parking, une
     * erreur HTTP si le parking n'a pas été trouvé.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParking(@PathParam("id") int id) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(id);

        // S'il n'existe pas.
        if (parking == null) {
            return Response.status(506).type(MediaType.TEXT_PLAIN_TYPE)
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
    @Path("/{nom}/{latitude}/{longitude}/{nomRasp}/{hote}/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public IParking creerParking(@PathParam("nom") String nom,
                                 @PathParam("latitude") double latitude,
                                 @PathParam("longitude") double longitude,
                                 @PathParam("nomRasp") String nomRasp,
                                 @PathParam("hote") String hote,
                                 @PathParam("port") int port) {
        // Créé le parking.
        IParking parking =
                new Parking(nom, new Localisation(latitude, longitude),
                            new RaspBerryPi(nomRasp, hote, port));

        // On le persiste.
        parkingService.creerParking(parking);

        // On le retourne.
        return parking;
    }

    @POST
    @Path("/ajouterPlace/{idParking}/{nom}/{handicapee}/{orientation}/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterPlace(@PathParam("idParking") int idParking,
                                 @PathParam("nom") String nom,
                                 @PathParam("handicapee") boolean handicapee,
                                 @PathParam("orientation") int orientation,
                                 @PathParam("latitude") double latitude,
                                 @PathParam("longitude") double longitude) {
        // Récupère le parking.
        IParking parking = parkingService.getParking(idParking);

        // S'il n'existe pas.
        if (parking == null) {
            return Response.status(506).type(MediaType.TEXT_PLAIN_TYPE)
                           .entity("Le parking avec l'identifiant \"" +
                                   idParking + "\" n'existe pas.").build();
        } else {

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
