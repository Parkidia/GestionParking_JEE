/*
 * ParkingWebService.java
 */
package com.parkidia.webServices;

import com.parkidia.application.WebServiceParkidia;
import com.parkidia.modeles.localisation.Localisation;
import com.parkidia.modeles.parking.IParking;
import com.parkidia.modeles.parking.Parking;
import com.parkidia.services.ParkingService;
import com.parkidia.services.PlaceService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
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

    @GET
    @Path("/image/{idParking}")
    @Produces("image/jpg")
    public Response getImageParking(@PathParam("idParking") int idParking) {
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

            // Le chemin de l'image.
            String chemin =
                    WebServiceParkidia.DOSSIER_IMAGE + idParking + ".jpg";
            File fichier = new File(chemin);

            if (fichier.exists()) {
                return Response.ok(new File(chemin)).build();
            } else {
                return Response
                        .status(WebServiceParkidia.HTTP_ERR_CHARGEMENT_IMAGE)
                        .build();
            }
        }
    }

    /**
     * Charge une image sur le serveur correspondant à un parking.
     * @param idParking l'identifiant du parking dont l'image représente.
     * @param cle le clé permettant de modifier des informations du parking.
     * @param in le flux permettant de lire l'image.
     * @param desc une description du fichier de l'image.
     * @return Une réponse HTTP : <ul>
     *     <li>{@link WebServiceParkidia#HTTP_ERR_UPLOAD_IMAGE}
     * : si un problème est survenu lors du chargement de l'image.</li>
     * <li>{@link WebServiceParkidia#HTTP_ERR_PARKING_INEXISTANT} : Si le
     * parking n'existe pas.</li>
     * <li>{@link WebServiceParkidia#HTTP_ERR_CLE_INVALIDE}
     * : Si la clé donnée n'est pas la bonne.</li> <li>200 si le chargement a
     * été réalisé avec succès.</li> </ul>
     */
    @POST
    @Path("/image/{idParking}/{cle}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response ajouterImage(@PathParam("cle") String cle,
                                 @PathParam("idParking") int idParking,
                                 @FormDataParam("image") InputStream in,
                                 @FormDataParam("image")
                                         FormDataContentDisposition desc) {

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

            // Le chemin où le fichier sera upload.
            String cheminUpload =
                    WebServiceParkidia.DOSSIER_IMAGE + idParking + ".jpg";

            // Enregistre l'image.
            try {
                enregistrerFichier(in, cheminUpload);
            } catch (IOException e) {
                return Response.status(WebServiceParkidia.HTTP_ERR_UPLOAD_IMAGE)
                               .build();
            }

            return Response.ok().build();
        }
    }

    /**
     * Sauvegarde un fichier sur le serveur.
     * @param stream le flux permettant de lire le fichier à sauvegarder.
     * @param chemin le chemin absolu où sauvegarder le fichier.
     * @throws IOException si une erreur est survenue lors de l'écriture du
     * fichier.
     */
    private static void enregistrerFichier(InputStream stream, String chemin)
    throws IOException {
        // Objet pour écrire.
        OutputStream out = new FileOutputStream(new File(chemin));

        // Nombre d'octets lus.
        int read = 0;

        // Tableau où seront stockés les octets lus.
        byte[] bytes = new byte[1024];

        // Lis.
        while ((read = stream.read(bytes)) != - 1) {
            out.write(bytes, 0, read);
        }

        // Vide le tampon dans le fichier et ferme le flux.
        out.flush();
        out.close();
    }
}
