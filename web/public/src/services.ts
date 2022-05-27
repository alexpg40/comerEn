import {Restaurante, Etiqueta, Usuario} from './d'

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
    const URL = 'http://localhost:8080/comerEn/administrador?buscarUsuarios'
    return request<Array<Usuario>>(URL);
}

export const getUsuario = (usuario: string) => {
    const URL = `http://localhost:8080/comerEn/administrador?buscarUsuario=${usuario}`
    return request<Array<Usuario>>(URL);
}

export const getRestaurantes = (restaurantes: string) => {
    const URL = `http://localhost:8080/comerEn/controlador?buscarRestaurantes=${restaurantes}`
    return request<Array<Restaurante>>(URL);
}

export const getEtiquetas = (etiquetas: string) => {
    const URL = `http://localhost:8080/comerEn/controlador?buscarEtiquetas=${etiquetas}`
    return request<Array<Etiqueta>>(URL);
}