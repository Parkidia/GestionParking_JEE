/*
 * ParkingWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.services.ParkingService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Représente le web service permettant de gérer les parkings.
 */
@Path("/parking")
public class ParkingWebService {

    /**
     * Le service permettant d'accéder aux parkings.
     */
    @EJB
    private ParkingService service;

    /**
     * @return la liste de tous les parkings sous la forme d'un tableau en JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTousParking() {

        // Retourne les parkings.
        return Response
                .ok(service.listeParkings().stream().toArray(Parking[]::new),
                    MediaType.APPLICATION_JSON_TYPE).build();
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
        IParking parking = service.getParking(id);

        // S'il n'existe pas.
        if (parking == null) {
            return Response.status(506).build();
        } else {
            return Response
                    .ok((Parking) parking, MediaType.APPLICATION_JSON_TYPE)
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
    public Response creerParking(@PathParam("nom") String nom,
                                 @PathParam("latitude") double latitude,
                                 @PathParam("longitude") double longitude) {
        // Créé le parking.
        IParking parking = new Parking(nom);
        parking.setLocalisation(new Localisation(latitude, longitude));

        // On le persiste.
        service.creerParking(parking);

        // On le retourne.
        return Response.ok(parking, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
