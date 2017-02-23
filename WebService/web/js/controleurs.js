/*
 * controleurs.js
 */

// Application Angular.
var parkidia = angular.module("parkidia", ['ngRoute', 'ngAnimate']);

// Contrôleur de la liste des parking.
parkidia.controller("ListeParkingsController", function ($scope, $rootScope, $location, parkingsFactory) {

    $rootScope.loading_finish = null;

    // Va vers l'ajout d'un parking.
    $scope.ajouterParking = function () {
        $location.path("/creerParking")
    };

    // Va vers la page des détails d'un parking.
    $scope.detailsParking = function (id) {
        $location.path("/parking/" + id);
    };

    // Récupère les parkings.
    parkingsFactory.getParkings()
        .then(function (response) {

            // Récupère les parkings.
            $scope.parkings = response.data;

            // Enlève la barre de chargement.
            $rootScope.loading_finish = 1;
        }, function (response) {
        })
});

// Contrôleur de l'ajout d'un parking
parkidia.controller("CreerParkingController", function($scope, $rootScope, $location, parkingsFactory) {

    $rootScope.loading_finish = 1;

    // Retour à la liste des parkings.
    $scope.listeParkings = function () {
        $location.path("/");
    };

    // Soumission du formulaire.
    $scope.creerParking = function () {

        // Affiche la barre de chargement.
        $rootScope.loading_finish = null;
        $('#creerParking').attr("disabled", "disabled");

        // Créé le parking.
        parkingsFactory.creerParking(this.nom, this.latitude, this.longitude)
           .then(function (response) {

               // Sauvegarde le parking créé.
               $scope.parking = response.data;

               // Récupère la photo.
               var photo = $('#photo')[0].files[0];

               // Charge l'image.
               parkingsFactory.chargerImageParking($scope.parking.id, $scope.parking.cle, photo)
                   .then(function (response) {
                       $("#modal-success").slideDown("fast");
                       $rootScope.loading_finish = 42;
                   }, function (response) {

                   });

           }, function (response) {

           });
    }
});

// Contrôleur du détail d'un parking.
parkidia.controller("DetailsParkingController", function ($scope, $routeParams, $rootScope, parkingsFactory) {

    // Affiche la barre de chargement.
    $rootScope.loading_finish = null;

    // Récupère le parking.
    parkingsFactory.getParking($routeParams.id)
       .then(function (response) {

           // Récupère le parking.
           $scope.parking = response.data;

           console.log(response);

           // Enlève la barre de chargement.
           $rootScope.loading_finish = 1;
       }, function (response) {
       })
});