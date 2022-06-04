const initMapa = (lat, lng) => {
    let punto = new L.LatLng(lat, lng);

    let map = new L.Map('map', {
        center: punto,
        zoom: 50,
    });

    let marker = L.marker(punto);
    marker.addTo(map);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    map.on('click', function(e){
        var coord = e.latlng;
        var lat = coord.lat;
        var lng = coord.lng;

        punto = new L.LatLng(lat, lng);

        console.log(marker)

        marker.remove();

        let marker = L.marker(punto);

        marker.addTo(map);

        console.log("You clicked the map at latitude: " + lat + " and longitude: " + lng);
        });
}