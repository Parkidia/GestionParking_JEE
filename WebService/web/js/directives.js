// Représente un objet Parking.
parkidiaApp.directive("parking", function () {
    return {
        restrict: "E",
        scope: {
            parking: "=data"
        },
        templateUrl: "templates/Parking/Parking.html" 
    };
});

// Représente un objet Place.
parkidiaApp.directive("place", function () {
    return {
        restrict: "E",
        scope: {
            place: "=data"
        },
        templateUrl: "templates/Place/Place.html"
    };
});