// Services de l'application pour accéder au web service REST.

/** L'URL racine du service REST. */
var ROOT_URL_REST = "http://localhost:8080/GestionParking_war_exploded/rest/";

// Web Service des parkings.
parkidiaApp.factory("parkingsFactory", function ($http) {
    return {

        /**
         * @return une promesse vers la liste des parkings.
         */
        getParkings: function () {
            return $http.get(ROOT_URL_REST + "parking");
        },

        /**
         * Récupère un parking.
         * @param id l'identifiant du parking.
         * @return une promesse vers le parking.
         */
        getParking: function (id) {
            return $http.get(ROOT_URL_REST + "parking/" + id);
        },

        /**
         * Créé un nouveau parking.
         * @param nom le nom du parking à créer.
         * @param latitude la latitude du parking à créer.
         * @param longitude la longitude du parking à créer.
         * @return une promesse.
         */
        creerParking: function (nom, latitude, longitude) {
            return $http.post(ROOT_URL_REST + "parking/" + nom + "/" + latitude
                + "/" + longitude);
        },

        /**
         * Modifie un parking.
         * @param id l'identifiant du parking à modifier.
         * @param cle la clé du parking pour pouvoir le modifier.
         * @param nom le nouveau nom du parking.
         * @param latitude la nouvelle latitude du parking.
         * @param longitude la nouvelle longitude du parking.
         * @return une promesse.
         */
        modifierParking: function (id, cle, nom, latitude, longitude) {
            return $http.put(ROOT_URL_REST + "parking/" + id + "/" + cle 
                + "/" + nom + "/" + latitude + "/" + longitude);
        },

        /**
         * Supprime un parking.
         * @param id l'identifiant du parking à supprimer.
         * @param cle la clé du parking pour pouvoir le supprimer.
         * @return une promesse.
         */
        supprimerParking: function (id, cle) {
            return $http.delete(ROOT_URL_REST + "parking/" + id + "/" + cle);
        }
    };
});

// Web Service des places de parkings.
parkidiaApp.factory("placesFactory", function ($http) {
    return {

        /**
         * Créé une nouvelle place pour un parking.
         * @param idParking l'identifiant du parking.
         * @param cle la clé du parking pour pouvoir le modifier.
         * @param nom le nom de la place.
         * @param handicapee si cette place est une place handicapée.
         * @param latitude la latitude de la place.
         * @param longitude la longitude de la place.
         * @param orientation l'orientation de la place sur 
                              l'overlay du parking.
         * @return une promesse. 
         */
        creerPlace: function (idParking, cle, nom, handicapee, latitude, 
                              longitude, orientation) {
            return $http.post(ROOT_URL_REST + "place/" + idParking + "/" + cle
                + "/" + nom + "/" + handicapee + "/" + latitude + "/" 
                + longitude + "/" + orientation);
        },

        /**
         * Modifie place pour un parking.
         * @param idParking l'identifiant du parking.
         * @param cle la clé du parking pour pouvoir le modifier.
         * @param nom le nom de la place.
         * @param handicapee si cette place est une place handicapée maintenant.
         * @param latitude la nouvelle latitude de la place.
         * @param longitude la nouvelle longitude de la place.
         * @param orientation la nouvelle orientation de la place sur 
                              l'overlay du parking.
         * @return une promesse.
         */
        modifierPlace: function (idParking, cle, nom, handicapee, latitude, 
                              longitude, orientation) {
            return $http.put(ROOT_URL_REST + "place/" + idParking + "/" + cle
                + "/" + nom + "/" + handicapee + "/" + latitude + "/" 
                + longitude + "/" + orientation);
        },

        /**
         * Supprime une place de parking.
         * @param idParking l'identifiant du parking.
         * @param cle la clé du parking pour pouvoir le modifier.
         * @param nomPlace le nom de la place.
         */
        supprimerPlace: function (idParking, cle, nomPlace) {
            return $http.delete(ROOT_URL_REST + "place/" + idParking + "/" 
                + cle + "/" + nomPlace);
        },

        /**
         * Modifie le statut d'une place de parking.
         * @param idParking l'identifiant du parking.
         * @param cle la clé du parking pour pouvoir le modifier.
         * @param nomPlace le nom de la place.
         * @param dispo si la place est maintenant disponible.
         * @param couleurVoiture la couleur de la voiture si la place
                                 n'est pas disponible.
         * @return une promesse.
         */
        modifierStatut: function (idParking, cle, nomPlace, dispo, 
                                  couleurVoiture) {
            return $http.post(ROOT_URL_REST + "place/ajouterStatut/" 
                + idParking + "/" + cle + "/" + nomPlace + "/" + dispo
                + "/" + couleurVoiture);
        }
    };
});