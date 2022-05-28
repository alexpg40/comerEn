var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { getEtiquetas, getRestaurantes } from './services.js';
window.onload = () => {
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    form.addEventListener('submit', handlerSubmit);
    input.addEventListener('input', handlerAutoComplete);
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
//# sourceMappingURL=index.js.map