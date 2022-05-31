import {getUsuarios, getUsuario} from './services.js'
import {Usuario} from './d'

window.onload = () => {
    init();
}

const init =  async () => {
    let usuarios = await getUsuarios();
    crearUsuarios(usuarios)
    let buscadorUsuario = document.getElementsByName('buscarUsuarios')[0];
    let inputBuscador = document.getElementsByName('buscador')[0] as HTMLInputElement;
    buscadorUsuario.onclick = async (eve) => {
        eve.preventDefault();
        let usuariosBuscador = await getUsuario(inputBuscador.value);
        crearUsuarios(usuariosBuscador);
    }
}

const crearUsuarios = (usuarios : Array<Usuario>) => {
    let sectionUsuario = document.getElementById('sectionUsuario');
    while(sectionUsuario.hasChildNodes()){
        sectionUsuario.removeChild(sectionUsuario.childNodes[0]);
    }
    usuarios.forEach(usuario => {
        sectionUsuario.appendChild(crearFilaUsuario(usuario));
    })
}

const crearFilaUsuario = (usuario: Usuario) => {
    const {idUsuario, nombre, apellido, correo, icono} = usuario;
    let articleUsuario = document.createElement('article');
    articleUsuario.className = 'usuario';

    let divIcono = document.createElement('div');
    divIcono.className = 'icono';
    let imgIcono = document.createElement('img');
    imgIcono.src = `public/img/${icono}`;
    divIcono.append(imgIcono);
    articleUsuario.append(divIcono);

    let divNombre = document.createElement('div');
    divNombre.className = 'nombreUsuario';
    divNombre.append(nombre);
    articleUsuario.append(divNombre);

    let divApellido = document.createElement('div');
    divApellido.className = 'apellidoUsuario';
    divApellido.append(apellido);
    articleUsuario.append(divApellido);

    let divCorreo = document.createElement('div');
    divCorreo.className = 'correoUsuario';
    divCorreo.append(correo);
    articleUsuario.append(divCorreo);

    let divBotones = document.createElement('div');
    divBotones.className = 'botonesUsuario';
    let aEliminar = document.createElement('a');
    aEliminar.className = 'botonEliminar';
    aEliminar.href = `administrador?eliminarUsuario=${idUsuario}`
    aEliminar.append('Eliminar');
    divBotones.append(aEliminar);
    let aEditar = document.createElement('a');
    aEditar.className = 'botonEditar';
    aEditar.href = `administrador?editarUsuario=${idUsuario}`
    aEditar.append('Editar');
    divBotones.append(aEditar);
    articleUsuario.append(divBotones);

    return articleUsuario;
}
