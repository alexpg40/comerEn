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

    map.on('click', function (e) {
        let coord = e.latlng;
        console.log(coord);
        let lat = coord.lat;
        let lng = coord.lng;
        fetch(`administrador?actualizarUbicacion=${lng};${lat}&idRestaurante=${idRestaurante}`)
        punto = new L.LatLng(lat, lng);
        marker.remove();
        marker = L.marker(punto);
        marker.addTo(map);
    })
}