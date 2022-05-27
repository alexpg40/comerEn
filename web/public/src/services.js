"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.getEtiquetas = exports.getRestaurantes = exports.getUsuario = exports.getUsuarios = void 0;
async function request(url, config = {}) {
    return fetch(url, config)
        .then((response) => {
        if (response.ok)
            return response.json();
        throw new Error('Error al intengar recuperar los usuarios de la base de datos');
    })
        .then((json) => json);
}
const getUsuarios = () => {
    const URL = 'http://localhost:8080/comerEn/administrador?buscarUsuarios';
    return request(URL);
};
exports.getUsuarios = getUsuarios;
const getUsuario = (usuario) => {
    const URL = `http://localhost:8080/comerEn/administrador?buscarUsuario=${usuario}`;
    return request(URL);
};
exports.getUsuario = getUsuario;
const getRestaurantes = (restaurantes) => {
    const URL = `http://localhost:8080/comerEn/controlador?buscarRestaurantes=${restaurantes}`;
    return request(URL);
};
exports.getRestaurantes = getRestaurantes;
const getEtiquetas = (etiquetas) => {
    const URL = `http://localhost:8080/comerEn/controlador?buscarEtiquetas=${etiquetas}`;
    return request(URL);
};
exports.getEtiquetas = getEtiquetas;
//# sourceMappingURL=services.js.map