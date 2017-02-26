// Définition de l'application Angular.
var parkidiaApp = angular.module("parkidiaApp", [
    'ngRoute', 'ngAnimate', 'ui.materialize'
]);

/**
 * Convertit une couleur en hexa en RGB.
 * @param hex la couleur en hexa.
 * @return string la même couleur en RGB.
 */
function hexToRGB(hex) {
    var r = parseInt(hex.slice(1, 3), 16),
        g = parseInt(hex.slice(3, 5), 16),
        b = parseInt(hex.slice(5, 7), 16);

    return r + ", " + g + ", " + b;
}

/**
 * Convertit une couleur en RGB en hexa.
 * @param color la couleur en RGB.
 * @return string la même couleur en hexa.
 */
function rgbToHex(color) {
    var nums = /(.*?)rgb\((\d+),\s*(\d+),\s*(\d+)\)/i.exec(color),
        r = parseInt(nums[2], 10).toString(16),
        g = parseInt(nums[3], 10).toString(16),
        b = parseInt(nums[4], 10).toString(16);

    return "#" + (
        (r.length == 1 ? "0" + r : r) +
        (g.length == 1 ? "0" + g : g) +
        (b.length == 1 ? "0" + b : b)
    );
}

// Contrôleur de la liste des parking.
parkidiaApp.controller("ListeParkingsController", 
    function ($scope, $rootScope, parkingsFactory) {
    
    // Chargement.
    $rootScope.chargementTerminee = false;

    // Pas d'erreurs.
    $rootScope.erreur = false;
    $rootScope.titreErreur = "";
    $rootScope.messageErreur = "";

    // Récupère les parkings.
    parkingsFactory.getParkings().then(function ok(reponse) {

        // Chargement terminé.
        $rootScope.chargementTerminee = true;

        // Sauvegarde les parkings dans le scope.
        $scope.parkings = reponse.data;

    }, function err(reponse) {
        
        // Chargement terminé.
        $rootScope.chargementTerminee = true;

        // Erreur.
        $rootScope.erreur = true;
        $rootScope.titreErreur = "Impossible de charger la liste des parkings";
        $rootScope.messageErreur = "";
    });
});

// Contrôleur de la création d'un parking.
parkidiaApp.controller("CreerParkingController", 
    function ($scope, $rootScope, $location, parkingsFactory) {
    
    // Chargement.
    $rootScope.chargementTerminee = true;

    // Pas d'erreurs.
    $rootScope.erreur = false;
    $rootScope.titreErreur = "";
    $rootScope.messageErreur = "";

    // Créé le parking.
    $scope.nouveauParking = function () {

        parkingsFactory.creerParking(this.nom, this.latitude, this.longitude)
        .then(function ok(reponse) {
            
            // Enregistre le parking créé pour afficher la clé.
            $scope.parkingCree = reponse.data;

            // Récupère l'overlay.
            var overlay = $("#overlay")[0].files[0];

            // Upload l'image.
            parkingsFactory.uploadOverlayParking(
                $scope.parkingCree.id, 
                $scope.parkingCree.cle, overlay).then(function ok(reponse) {
                    
                    // Fin du chargement
                    $rootScope.chargementTerminee = true;

                    // Montre le résultat de la création.
                    $scope.parkingOk = true;

                }, function err(reponse) {

                    // Chargement terminé.
                    $rootScope.chargementTerminee = true;

                    // Erreur.
                    $rootScope.erreur = true;
                    $rootScope.titreErreur = "Impossible de charger " + 
                                             "l'image du parking"
                    $rootScope.messageErreur = "Veuillez réessayer de charger "
                                               + "l'image en modifiant le parking";                         
                });

        }, function err(reponse) {

            // Chargement terminé.
            $rootScope.chargementTerminee = true;

            // Erreur.
            $rootScope.erreur = true;
            $rootScope.titreErreur = "Impossible de créer le parking \"" 
                                     + this.nom + "\"";
            $rootScope.messageErreur = "";
        });
    };
});

// Contrôleur des détails d'un parking.
parkidiaApp.controller("DetailsParkingController",
    function ($scope, $rootScope, $routeParams, $location,
              parkingsFactory, placesFactory) {
        
    // Chargement.
    $rootScope.chargementTerminee = false;

    // Pas d'erreurs.
    $rootScope.erreur = false;
    $rootScope.titreErreur = "";
    $rootScope.messageErreur = "";
    $scope.parkingModifie = false;

    // Rend disponible la fonction dans le scope.
    $scope.enHex = function (color) {
        return rgbToHex(color);
    }

    /** Initialise le parking et ses places. */
    function init() {
        // Récupère les parkings.
        parkingsFactory.getParking($routeParams.id).then(function ok(reponse) {

            // Chargement terminé.
            $rootScope.chargementTerminee = true;

            // Sauvegarde le parking dans le scope.
            $scope.nom = reponse.data.nom;
            $scope.parking = reponse.data;

        }, function err(reponse) {
            
            // Chargement terminé.
            $rootScope.chargementTerminee = true;

            // Erreur.
            $rootScope.erreur = true;

            if (reponse.status == 510) {
                $rootScope.titreErreur = "Le parking avec l'identifiant \"" 
                                         + $routeParams.id + "\" n'existe pas";
            } else {
                $rootScope.titreErreur = "Impossible de charger le parking";
            }
            $rootScope.messageErreur = "";
        });
    }

    // Initialise la vue.
    init();

    // Modifie la place courante sélectionnée.
    $scope.setPlaceCourante = function (place) {
        $scope.placeCourante = place;
    };

    // Crééer une nouvelle place à ce parking.
    $scope.creerPlaceParking = function () {
        
        // Chargement.
        $rootScope.chargementTerminee = false;

        // Créé la place.
        placesFactory.creerPlace($routeParams.id, this.cleParking, 
                                 this.nomPlace, this.handicapeePlace, 
                                 this.latitudePlace, this.longitudePlace, 
                                 this.orientationPlace).then(
            function ok(reponse) {
                
                // Place créée => update la vue.
                init();

            }, function err(reponse) {
                
                // Erreur.
                $rootScope.erreur = true;
                $rootScope.titreErreur = "Impossible de créer cette place.";
                $rootScope.messageErreur = reponse.data;
            });

        // Reset les champs
        this.cleParking = this.nomPlace = this.handicapeePlace = 
            this.latitudePlace = this.longitudePlace = this.orientationPlace 
                = null;
    };

    // Supprime le parking courant.
    $scope.supprimerParkingCourant = function () {
        parkingsFactory.supprimerParking($routeParams.id, this.cleParking).then(

            function ok(reponse) {
                
                // Parking supprimé => on revient à la liste.
                $location.path("/");

            }, function err(reponse) {
                
                // Erreur.
                $rootScope.erreur = true;
                $rootScope.titreErreur = "Impossible de supprimer ce parking.";
                $rootScope.messageErreur = reponse.data;
            });

        // Reset les champs
        this.cleParking = null;
    };

    // Modifie le parking courant.
    $scope.modifierParkingCourant = function () {
        parkingsFactory.modifierParking($routeParams.id, this.cleParking,
                                        this.nom, this.latitude, this.longitude)
        .then(function ok(reponse) {
            
            // Enregistre le parking.
            $scope.parkingCree = reponse.data;

            if ($('#overlay')[0].files.length != 0) {
                // Récupère l'overlay.
                var overlay = $("#overlay")[0].files[0];

                // Upload l'image.
                parkingsFactory.uploadOverlayParking(
                    $scope.parkingCree.id, 
                    $scope.parkingCree.cle, overlay).then(function ok(reponse) {
                        
                        // Fin du chargement
                        $rootScope.chargementTerminee = true;

                        // Refresh.
                        init();
                        $scope.parkingModifie = true;

                    }, function err(reponse) {

                        // Chargement terminé.
                        $rootScope.chargementTerminee = true;

                        // Erreur.
                        $rootScope.erreur = true;
                        $rootScope.titreErreur = "Impossible de charger " + 
                                                 "l'image du parking"
                        $rootScope.messageErreur = "Veuillez réessayer de charger "
                                                   + "l'image en modifiant le parking";                         
                    });
                } else {
                    
                    // Refresh.
                    init();
                    $scope.parkingModifie = true;
                }

        }, function err(reponse) {

            // Chargement terminé.
            $rootScope.chargementTerminee = true;

            // Erreur.
            $rootScope.erreur = true;
            $rootScope.titreErreur = "Impossible de modifier le parking \"" 
                                     + this.nom + "\"";
            $rootScope.messageErreur = "";
        });

        // Reset les champs.
        this.cleParking = null;
    };

    // Modifie la place de parking courante.
    $scope.modifierPlaceParking = function () {
        // Modifie la place.
        placesFactory.modifierPlace($routeParams.id, this.cleParking, 
                                 $scope.placeCourante.nom, this.handicapeePlace, 
                                 this.latitudePlace, this.longitudePlace, 
                                 this.orientationPlace).then(
            function ok(reponse) {
                
                // Place modifiée => update la vue.
                init();

            }, function err(reponse) {
                
                // Erreur.
                $rootScope.erreur = true;
                $rootScope.titreErreur = "Impossible de modifier cette place.";
                $rootScope.messageErreur = reponse.data;
            });

        // Reset les champs
        this.cleParking = this.handicapeePlace = 
            this.latitudePlace = this.longitudePlace = this.orientationPlace 
                = null;
    };

    // Supprime la place de parking courante.
    $scope.supprimerPlaceParking = function () {
        placesFactory.supprimerPlace($routeParams.id, this.cleParking, 
                                     $scope.placeCourante.nom).then(

            function ok(reponse) {
                
                // Place supprimée => update la vue.
                init();

            }, function err(reponse) {
                
                // Erreur.
                $rootScope.erreur = true;
                $rootScope.titreErreur = "Impossible de supprimer cette place.";
                $rootScope.messageErreur = reponse.data;
            });

        // Reset les champs
        this.cleParking = null;
    };

    // Modifie le statut de la place courante.
    $scope.majStatutPlace = function () {

        placesFactory.modifierStatut($routeParams.id, this.cleParking,
                                     $scope.placeCourante.nom, !this.dispoPlace,
                                     this.dispoPlace ? 
                                        hexToRGB(this.couleurVoiture) : null).then(
            function ok(reponse) {
                
                // Place modifiée => update la vue.
                init();

            }, function err() {
                
                // Erreur.
                $rootScope.erreur = true;
                $rootScope.titreErreur = "Impossible de modifier le statut de" 
                                         + " cette place.";
                $rootScope.messageErreur = reponse.status;
            });

        // Reset les champs
        this.cleParking = this.dispoPlace = this.couleurVoiture = null;
    };
});

// Le contrôleur de la création du JSON.
parkidiaApp.controller("CreerJsonController", 
    function ($scope, $rootScope, $routeParams, parkingsFactory) {
    
    // Récupère les parkings.
    parkingsFactory.getParking($routeParams.id).then(function ok(reponse) {

        // Chargement terminé.
        $rootScope.chargementTerminee = true;

        // Sauvegarde le parking dans le scope.
        $scope.nom = reponse.data.nom;
        $scope.parking = reponse.data;

        // Les découpages saisies.
        $scope.decoup = [];
        $scope.places = [];

        // Formulaire valide.
        $scope.valide = false;

        // Rend disponible la fonction dans le scope.
        $scope.enHex = function (color) {
            return rgbToHex(color);
        }

        // Initialise le module pour faire des sélections.
        $(document).ready(function () {
            $('#image').selectAreas({
                allowEdit: true,
                allowMove: true,
                allowResize: true,
                allowSelect: true,
                allowDelete: true,
                allowNudge: true,
                aspectRatio: 0,
                minSize: [1, 1],
                width: 0,
                maxAreas: $scope.parking.places.length,
                outlineOpacity: 1,
                overlayOpacity: 0,
                areas: [],
                onChanging: null,
                onChanged: function () {
                    // Enregistre les coordonnées.
                    $scope.decoup = $('#image').selectAreas("relativeAreas");

                    // Si le nombre de zones renseignées est bon.
                    $scope.valide = $scope.decoup.length 
                                        == $scope.parking.places.length;

                    // Force la MAJ du scope.
                    $scope.$apply();
                }
            });
        });

    }, function err(reponse) {
        
        // Chargement terminé.
        $rootScope.chargementTerminee = true;

        // Erreur.
        $rootScope.erreur = true;

        if (reponse.status == 510) {
            $rootScope.titreErreur = "Le parking avec l'identifiant \"" 
                                     + $routeParams.id + "\" n'existe pas";
        } else {
            $rootScope.titreErreur = "Impossible de charger le parking";
        }
        $rootScope.messageErreur = "";
    });

    /**
     * Génère le JSON des places.
     */
    $scope.genererJSON = function () {
        var json = [];

        for (var i = 0 ; i < $scope.parking.places.length ; i++) {
            json.push({
                nom: $scope.parking.places[i].nom,
                minX: $scope.decoup[i].x,
                minY: $scope.decoup[i].y,
                maxX: $scope.decoup[i].width + $scope.decoup[i].x,
                maxY: $scope.decoup[i].height + $scope.decoup[i].y,
                dispo: $scope.parking.places[i].dernierStatut.disponible,
                couleurVoiture: $scope.parking.places[i].dernierStatut.couleurVoiture
            });
        }
        var data = "data:text/json;charset=utf-8," +
            encodeURIComponent(JSON.stringify(json, null, 4));
        var downloadJson = document.getElementById('downloadJson');
        downloadJson.setAttribute("href", data);
        downloadJson.setAttribute("download", "parking.json");
        downloadJson.click();
    };
});