var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { getEtiquetas, getRestaurantes, getRestaurantesCercanos } from './services.js';
window.onload = () => {
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    form.addEventListener('submit', handlerSubmit);
    input.addEventListener('input', handlerAutoComplete);
    initLocalizacion();
};
const validarInput = (input) => {
    if (input.length < 3)
        return false;
    return true;
};
const handlerSubmit = (eve) => {
    eve.preventDefault();
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    let inputValido = validarInput(input.value);
    if (inputValido) {
        form.submit();
        return;
    }
    ;
    alert('Necesitas al menos tres carácteres');
};
const handlerAutoComplete = (eve) => __awaiter(void 0, void 0, void 0, function* () {
    const input = eve.target;
    const autoComplete = document.querySelector('.autocomplete');
    borrarAutoComplete();
    if (input.value.length > 2) {
        autoComplete.style.display = 'block';
        const restaurantes = yield getRestaurantes(input.value);
        if (restaurantes.length > 0) {
            crearAutoCompleteRestaurantes(restaurantes);
        }
        const etiquetas = yield getEtiquetas(input.value);
        if (etiquetas.length > 0) {
            crearAutoCompleteEtiquetas(etiquetas);
        }
    }
    else {
        autoComplete.style.display = 'none';
    }
    if (!autoComplete.hasChildNodes())
        mostrarNoHaySugerencias();
});
const crearAutoCompleteRestaurantes = (restaurantes) => {
    const autoComplete = document.querySelector('.autocomplete');
    restaurantes.forEach(({ idRestaurante, nombre }) => {
        const URL = `http://localhost:8080/comerEn/controlador?restaurante=${idRestaurante}`;
        const aRestaurante = crearSugerencia(nombre, URL);
        autoComplete.appendChild(aRestaurante);
    });
};
const crearAutoCompleteEtiquetas = (etiquetas) => {
    const autoComplete = document.querySelector('.autocomplete');
    etiquetas.forEach(({ idEtiqueta, nombre }) => {
        const URL = `http://localhost:8080/comerEn/controlador?etiquetas=${idEtiqueta}`;
        const aEtiqueta = crearSugerencia(nombre, URL);
        autoComplete.appendChild(aEtiqueta);
    });
};
const crearSugerencia = (texto, url) => {
    const aSugerencia = document.createElement('a');
    aSugerencia.className = 'sugerencia';
    aSugerencia.append(texto);
    aSugerencia.href = url;
    return aSugerencia;
};
const borrarAutoComplete = () => {
    const autoComplete = document.querySelector('.autocomplete');
    while (autoComplete.hasChildNodes()) {
        autoComplete.removeChild(autoComplete.childNodes[0]);
    }
};
const mostrarNoHaySugerencias = () => {
    const autoComplete = document.querySelector('.autocomplete');
    const pNoHaySugerencias = document.createElement('p');
    pNoHaySugerencias.append('No hay sugerencias con los datos introducidos!');
    autoComplete.append(pNoHaySugerencias);
};
const initLocalizacion = () => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(obtenerLocalizacion, errorLocalizacion);
    }
};
const obtenerLocalizacion = ({ coords }) => __awaiter(void 0, void 0, void 0, function* () {
    const restaurantes = yield getRestaurantesCercanos(coords);
    crearRestaurantes('Restaurantes más cercanos', restaurantes);
    localStorage.setItem('ultimaUbicacion', `${coords.longitude}|${coords.latitude}`);
});
const errorLocalizacion = () => __awaiter(void 0, void 0, void 0, function* () {
    if (localStorage.getItem('ultimaUbicacion')) {
        const strPuntos = localStorage.getItem('ultimaUbicacion');
        const coords = { longitude: Number(strPuntos.split('|')[1]), latitude: Number(strPuntos.split('|')[0]) };
        const restaurantes = yield getRestaurantesCercanos(coords);
        crearRestaurantes('Restaurantes más cercanos desde tu última ubicación', restaurantes);
    }
});
const crearRestaurantes = (nombreSeccion, restaurantes) => {
    const h2Seccion = document.querySelector('#restaurantes > h2');
    h2Seccion.textContent = nombreSeccion;
    const sectionRestaurantes = document.querySelector('#restaurantesContainer');
    while (sectionRestaurantes.hasChildNodes()) {
        sectionRestaurantes.removeChild(sectionRestaurantes.childNodes[0]);
    }
    restaurantes.forEach(restaurante => {
        const restauranteArticle = crearRestaurante(restaurante);
        sectionRestaurantes.appendChild(restauranteArticle);
    });
};
const crearRestaurante = (restaurante) => {
    const aRestaurante = document.createElement('a');
    aRestaurante.href = `controlador?restaurante=${restaurante.idRestaurante}`;
    const articleRestaurante = document.createElement('article');
    articleRestaurante.className = 'restaurante';
    aRestaurante.append(articleRestaurante);
    const h3Restaurante = document.createElement('h3');
    h3Restaurante.append(restaurante.nombre);
    articleRestaurante.append(h3Restaurante);
    const hr = document.createElement('hr');
    articleRestaurante.append(hr);
    const sectionRestaurante = document.createElement('section');
    articleRestaurante.appendChild(sectionRestaurante);
    sectionRestaurante.className = 'bodyRestaurante';
    const articleImg = document.createElement('article');
    sectionRestaurante.appendChild(articleImg);
    const imgRestaurante = document.createElement('img');
    imgRestaurante.className = 'imagenRestaurante';
    imgRestaurante.src = `public/img/${restaurante.icono}`;
    imgRestaurante.alt = `Imagen del restaurante ${restaurante.nombre}`;
    articleImg.append(imgRestaurante);
    const articleCointainerDescripcion = document.createElement('article');
    articleCointainerDescripcion.className = 'containerDescripcion';
    sectionRestaurante.appendChild(articleCointainerDescripcion);
    const articleDescripcion = document.createElement('article');
    articleDescripcion.className = 'descripcionRestaurante';
    articleDescripcion.append(restaurante.descripcion);
    articleCointainerDescripcion.appendChild(articleDescripcion);
    const articleValoracion = document.createElement('article');
    articleValoracion.className = 'valoracionRestaurante';
    articleValoracion.append('★★★★★');
    articleCointainerDescripcion.appendChild(articleValoracion);
    return aRestaurante;
};
//# sourceMappingURL=index.js.map