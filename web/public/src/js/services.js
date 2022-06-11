var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function request(url, config = {}) {
    return __awaiter(this, void 0, void 0, function* () {
        return fetch(url, config)
            .then((response) => {
            if (response.ok)
                return response.json();
            throw new Error('Error al intengar recuperar los usuarios de la base de datos');
        })
            .then((json) => json);
    });
}
export const getUsuarios = () => {
    const URL = 'administrador?buscarUsuarios';
    return request(URL);
};
export const getUsuario = (usuario) => {
    const URL = `administrador?buscarUsuario=${usuario}`;
    return request(URL);
};
export const getRestaurantes = (restaurantes) => {
    const URL = `controlador?buscarRestaurantes=${restaurantes}`;
    return request(URL);
};
export const getRestaurantesPopulares = () => {
    const URL = `controlador?getRestaurantesPopulares=getRestaurantesPopulare`;
    return request(URL);
};
export const getEtiquetas = (etiquetas) => {
    const URL = `controlador?buscarEtiquetas=${etiquetas}`;
    return request(URL);
};
export const getLocalidades = (localidad) => {
    const URL = `controlador?buscarLocalidades=${localidad}`;
    return request(URL);
};
export const getRestaurantesCercanos = (punto, radio = 40) => {
    const { latitude, longitude } = punto;
    const URL = `controlador?buscarRestaurantesCercanos=${longitude}|${latitude}&radio=${radio}`;
    return request(URL);
};
export const getRestaurantesFiltrados = (localidad, valoracionMin, radio = 40, punto) => {
    let URL = '';
    if (punto) {
        const { latitude, longitude } = punto;
        URL = `controlador?filtrarRestaurantes=${longitude}|${latitude}&radio=${radio}&valoracionMin=${valoracionMin}&localidadFiltros=${localidad}`;
    }
    else {
        URL = `controlador?filtrarRestaurantes=a&valoracionMin=${valoracionMin}&localidadFiltros=${localidad}`;
    }
    return request(URL);
};
//# sourceMappingURL=services.js.map