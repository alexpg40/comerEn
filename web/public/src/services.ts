import {Restaurante, Etiqueta, Usuario, Punto} from './d'

async function request<TResponse>(
    url: string,
    config: RequestInit = {}
) : Promise<TResponse> {
    return fetch(url, config)
    .then((response) => {
        if(response.ok) return response.json();
        throw new Error('Error al intengar recuperar los usuarios de la base de datos')
    })
    .then((json) => json as TResponse)
}

export const getUsuarios =  () => {
    const URL = 'administrador?buscarUsuarios'
    return request<Array<Usuario>>(URL);
}

export const getUsuario = (usuario: string) => {
    const URL = `administrador?buscarUsuario=${usuario}`
    return request<Array<Usuario>>(URL);
}

export const getRestaurantes = (restaurantes: string) => {
    const URL = `controlador?buscarRestaurantes=${restaurantes}`
    return request<Array<Restaurante>>(URL);
}

export const getRestaurantesPopulares = () => {
    const URL = `controlador?getRestaurantesPopulares=getRestaurantesPopulare`
    return request<Array<Restaurante>>(URL);
}

export const getEtiquetas = (etiquetas: string) => {
    const URL = `controlador?buscarEtiquetas=${etiquetas}`
    return request<Array<Etiqueta>>(URL);
}

export const getLocalidades = (localidad: string) => {
    const URL = `controlador?buscarLocalidades=${localidad}`
    return request<Array<string>>(URL);
}

export const getRestaurantesCercanos = (punto : Punto, radio = 40) => {
    const {latitude, longitude} = punto;
    const URL = `controlador?buscarRestaurantesCercanos=${longitude}|${latitude}&radio=${radio}`
    return request<Array<Restaurante>>(URL);
}

export const getRestaurantesFiltrados = (localidad : string, valoracionMin : Number, radio = 40, punto?: Punto) => {
    let URL = ''
    if(punto){
        const { latitude, longitude } = punto;
        URL = `controlador?filtrarRestaurantes=${longitude}|${latitude}&radio=${radio}&valoracionMin=${valoracionMin}&localidadFiltros=${localidad}`
    } else {
        URL = `controlador?filtrarRestaurantes=a&valoracionMin=${valoracionMin}&localidadFiltros=${localidad}`
    }
    return request<Array<Restaurante>>(URL);
}