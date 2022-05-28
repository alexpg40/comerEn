var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { getUsuarios, getUsuario } from './services';
window.onload = () => {
    init();
};
const init = () => __awaiter(void 0, void 0, void 0, function* () {
    let usuarios = yield getUsuarios();
    crearUsuarios(usuarios);
    let buscadorUsuario = document.getElementsByName('buscarUsuarios')[0];
    let inputBuscador = document.getElementsByName('buscador')[0];
    buscadorUsuario.onclick = (eve) => __awaiter(void 0, void 0, void 0, function* () {
        eve.preventDefault();
        let usuariosBuscador = yield getUsuario(inputBuscador.value);
        crearUsuarios(usuariosBuscador);
    });
});
const crearUsuarios = (usuarios) => {
    let sectionUsuario = document.getElementById('sectionUsuario');
    while (sectionUsuario.hasChildNodes()) {
        sectionUsuario.removeChild(sectionUsuario.childNodes[0]);
    }
    usuarios.forEach(usuario => {
        sectionUsuario.appendChild(crearFilaUsuario(usuario));
    });
};
const crearFilaUsuario = (usuario) => {
    const { idUsuario, nombre, apellido, correo, icono } = usuario;
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
    aEliminar.href = `administrador?eliminarUsuario=${idUsuario}`;
    aEliminar.append('Eliminar');
    divBotones.append(aEliminar);
    let aEditar = document.createElement('a');
    aEditar.className = 'botonEditar';
    aEditar.href = `administrador?editarUsuario=${idUsuario}`;
    aEditar.append('Editar');
    divBotones.append(aEditar);
    articleUsuario.append(divBotones);
    return articleUsuario;
};
//# sourceMappingURL=adminUsuarios.js.map