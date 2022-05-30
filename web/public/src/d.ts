export interface Usuario{
    idUsuario: Number
    nombre: string
    apellido: string
    correo: string
    icono: string
}

export interface Restaurante{
    idRestaurante: Number
    nombre: string
    descripcion: string
    horario_abre: string
    horario_cierra: string
    icono: string
    idDueño: Number
    idAdmin: Number
    oculto: boolean
}

export interface Etiqueta{
    idEtiqueta: Number
    nombre: string
}

export interface Punto{
    longitude: Number
    latitude: Number
}