"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const services_1 = require("./services");
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
const handlerAutoComplete = async (eve) => {
    const input = eve.target;
    if (input.value.length > 0) {
        const autoComplete = document.querySelector('.autocomplete');
        autoComplete.style.display = 'block';
        const restaurantes = await (0, services_1.getRestaurantes)(input.value);
        if (restaurantes.length > 0) {
            crearAutoCompleteRestaurantes(restaurantes);
        }
        const etiquetas = await (0, services_1.getEtiquetas)(input.value);
        if (etiquetas.length > 0) {
            crearAutoCompleteEtiquetas(etiquetas);
        }
    }
};
const crearAutoCompleteRestaurantes = (restaurantes) => {
    const autoComplete = document.querySelector('.autocomplete');
    restaurantes.forEach(({ idRestaurante, nombre }) => {
        const aRestaurante = document.createElement('a');
        aRestaurante.append(nombre);
        aRestaurante.href = `http://localhost:8080/comerEn/controlador?restaurante=${idRestaurante}`;
        autoComplete.appendChild(aRestaurante);
    });
};
const crearAutoCompleteEtiquetas = (etiquetas) => {
    const autoComplete = document.querySelector('.autocomplete');
    etiquetas.forEach(({ idEtiqueta, nombre }) => {
        const aEtiqueta = document.createElement('a');
        aEtiqueta.append(nombre);
        aEtiqueta.href = `http://localhost:8080/comerEn/controlador?etiquetas=${idEtiqueta}`;
        aEtiqueta.appendChild(aEtiqueta);
    });
};
//# sourceMappingURL=index.js.map