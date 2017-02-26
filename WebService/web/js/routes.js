// Configuration des routes.
parkidiaApp.config(function ($locationProvider, $routeProvider) {

    // Préfixe des URLs.
    $locationProvider.hashPrefix('!');

    // Accueil.
    $routeProvider.when("/", {
        templateUrl: "templates/Parking/ListeParkings.html",
        controller: "ListeParkingsController"

    }).when("/creerParking", {
        templateUrl: "templates/Parking/CreerParking.html",
        controller: "CreerParkingController"

    }).when("/parking/:id", {
        templateUrl: "templates/Parking/DetailsParking.html",
        controller: "DetailsParkingController"

    }).when("/creerJson/:id", {
        templateUrl: "templates/Parking/CreerJson.html",
        controller: "CreerJsonController"

    }).when("/erreur-404", {
        templateUrl: "templates/Erreur404.html"

    }).otherwise("/erreur-404");
});