/*
 * routes.js
 */

// Créé les routes.
parkidia.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

    // Page d'accueil.
    $routeProvider.when("/", {
        templateUrl: "templates/listeParkings.html",
        controller: "ListeParkingsController"

    }).when("/creerParking", {
        templateUrl: "templates/creerParking.html",
        controller: "CreerParkingController"

    }).when("/parking/:id", {
        templateUrl: "templates/detailsParking.html",
        controller: "DetailsParkingController"
    });
}]);