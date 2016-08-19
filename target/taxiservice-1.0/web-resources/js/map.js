/**
 * Created by maxim on 18.08.2016.
 */

function initMap() {
    var origin_place = {address: undefined};
    var destination_place = {address: undefined};
    var currentInputEl = {location: 'currentLocation', latlng: 'currentLatLng'};
    var targetInputEl = {location: 'targetLocation', latlng: 'targetLatLng'};
    var isDirection = false;
    var travel_mode = google.maps.TravelMode.DRIVING;
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 53.9036202, lng: 27.5451904},
        zoom: 13,
        streetViewControl: false
    });

    var origin_input = document.getElementById('origin-input');
    var destination_input = document.getElementById('destination-input');
    // var modes = document.getElementById('mode-selector');

    var currentPositionMarker = new google.maps.Marker();
    var targetPositionMarker = new google.maps.Marker();
    var geocoder = new google.maps.Geocoder;
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer({
        draggable: true,
        map: map
    });

    map.controls[google.maps.ControlPosition.TOP_LEFT].push(origin_input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(destination_input);
    // map.controls[google.maps.ControlPosition.TOP_LEFT].push(modes);

    currentPositionMarker.addListener('click', function () {
        this.setMap(null);
    });

    currentPositionMarker.addListener('dragend', function () {
        var position = this.position;
        geocodeLatLng(geocoder, map, position, origin_input, currentInputEl, origin_place);
        route(origin_place.address, destination_place.address, travel_mode,
            directionsService, directionsDisplay);
    });

    targetPositionMarker.addListener('dragend', function () {
        var position = this.position;
        geocodeLatLng(geocoder, map, position, destination_input, targetInputEl, destination_place);
        route(origin_place.address, destination_place.address, travel_mode,
            directionsService, directionsDisplay);
    });

    map.addListener('click', function(event) {
        if (!isDirection) {
            if (!currentPositionMarker.getMap()) {
                currentPositionMarker.setMap(map);
                currentPositionMarker.setPosition(event.latLng);
                currentPositionMarker.setDraggable(true);
                geocodeLatLng(geocoder, map, event.latLng, origin_input, currentInputEl, origin_place);
            } else if (!targetPositionMarker.getMap()) {
                targetPositionMarker.setMap(map);
                targetPositionMarker.setPosition(event.latLng);
                targetPositionMarker.setDraggable(true);
                geocodeLatLng(geocoder, map, event.latLng, destination_input, targetInputEl, destination_place);
            }
            route(origin_place.address, destination_place.address, travel_mode,
                directionsService, directionsDisplay);
        }
    });

    var origin_autocomplete = new google.maps.places.Autocomplete(origin_input);
    origin_autocomplete.bindTo('bounds', map);
    var destination_autocomplete = new google.maps.places.Autocomplete(destination_input);
    destination_autocomplete.bindTo('bounds', map);

    // Sets a listener on a radio button to change the filter type on Places
    // Autocomplete.
    // function setupClickListener(id, mode) {
    //     var radioButton = document.getElementById(id);
    //     radioButton.addEventListener('click', function() {
    //         travel_mode = mode;
    //     });
    // }
    // setupClickListener('changemode-walking', google.maps.TravelMode.WALKING);
    // setupClickListener('changemode-transit', google.maps.TravelMode.TRANSIT);
    // setupClickListener('changemode-driving', google.maps.TravelMode.DRIVING);

    function expandViewportToFitPlace(map, place) {
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
    }

    origin_autocomplete.addListener('place_changed', function() {
        var place = origin_autocomplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }
        expandViewportToFitPlace(map, place);
        currentPositionMarker.setMap(map);
        currentPositionMarker.setPosition(place.geometry.location);
        currentPositionMarker.setDraggable(true);
        document.getElementById(currentInputEl.location).innerHTML = place.formatted_address;
        document.getElementById(currentInputEl.latlng).value = place.geometry.location;
        // If the place has a geometry, store its place ID and route if we have
        // the other place ID
        origin_place.address = place.formatted_address;
        route(origin_place.address, destination_place.address, travel_mode,
            directionsService, directionsDisplay);
    });

    destination_autocomplete.addListener('place_changed', function() {
        var place = destination_autocomplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }
        expandViewportToFitPlace(map, place);
        targetPositionMarker.setMap(map);
        targetPositionMarker.setPosition(place.geometry.location);
        targetPositionMarker.setDraggable(true);
        document.getElementById(targetInputEl.location).innerHTML = place.formatted_address;
        document.getElementById(targetInputEl.latlng).value = place.geometry.location;
        // If the place has a geometry, store its place ID and route if we have
        // the other place ID
        destination_place.address = place.formatted_address;
        route(origin_place.address, destination_place.address, travel_mode,
            directionsService, directionsDisplay)
    });

    function route(origin_place_address, destination_place_address, travel_mode,
                   directionsService, directionsDisplay) {
        if (!origin_place_address || !destination_place_address) {
            return;
        }
        currentPositionMarker.setMap(null);
        targetPositionMarker.setMap(null);
        isDirection = true;
        directionsService.route({
            origin: origin_place_address,
            destination: destination_place_address,
            travelMode: travel_mode
        }, function(response, status) {
            if (status === google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
    }

    directionsDisplay.addListener('directions_changed', function() {
        var response = directionsDisplay.getDirections();
        computeTotalDistance(response);
        origin_input.value = response.routes[0].legs[0].start_address;
        destination_input.value = response.routes[0].legs[response.routes[0].legs.length-1].end_address;
        document.getElementById(currentInputEl.location).innerHTML = response.routes[0].legs[0].start_address;
        document.getElementById(targetInputEl.location).innerHTML = response.routes[0].legs[response.routes[0].legs.length-1].end_address;
        document.getElementById(currentInputEl.latlng).value = response.routes[0].legs[0].start_location;
        document.getElementById(targetInputEl.latlng).value = response.routes[0].legs[response.routes[0].legs.length-1].end_location;
    });

    function geocodeLatLng(geocoder, map, latlng, input, locationSpan, place) {
        // var latlngStr = input.split(',', 2);
        // var latlng = {lat: parseFloat(latlngStr[0]), lng: parseFloat(latlngStr[1])};
        geocoder.geocode({'location': latlng}, function(results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    place.address = results[0].formatted_address;
                    input.value = place.address;
                    document.getElementById(locationSpan.location).innerHTML = place.address;
                    document.getElementById(locationSpan.latlng).value = results[0].geometry.location;
                } else {
                    window.alert('No results found');
                }
            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    }

    function computeTotalDistance(result) {
        var total = 0;
        var myroute = result.routes[0];
        for (var i = 0; i < myroute.legs.length; i++) {
            total += myroute.legs[i].distance.value;
        }
        total = total / 1000;
        document.getElementById('total').innerHTML = total + ' km';
    }

    // Adds a marker to the map.
    function addMarker(location, map) {
        // Add the marker at the clicked location, and add the next-available label
        // from the array of alphabetical characters.
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });
    }
}
