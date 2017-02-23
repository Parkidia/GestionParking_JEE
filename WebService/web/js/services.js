/*
 * services.js
 */

parkidia.factory("parkingsFactory", function ($http) {
    return {

        /**
         * Récupère la liste des parkings.
         */
        getParkings: function () {
            return $http.get(
                "http://localhost:8080/GestionParking_war_exploded/rest/parking");
        },

        /**
         * Retourne les informations d'un parking.
         */
        getParking: function (id) {
            return $http.get(
                "http://localhost:8080/GestionParking_war_exploded/rest/parking/"
                + id);
        },

        /**
         * Créé un nouveau parking.
         */
        creerParking: function (nom, latitude, longitude) {
            return $http.post(
                "http://localhost:8080/GestionParking_war_exploded/rest/parking/" +
                nom + "/" + latitude + "/" + longitude)
        },

        /**
         * Charge l'image d'un parking.
         */
        "chargerImageParking": function (idParking, cle, image) {
            var fd = new FormData();
            fd.append("overlay", image);
            return $http.post("http://localhost:8080/GestionParking_war_exploded/rest/parking/overlay/"
                              + idParking + "/" + cle, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            });
        }
    }
});