import {validarNombre, validarApellido, validarCorreo} from './validaciones.js';

window.onload = () => {
    let form = document.getElementById('formActualizarCuenta') as HTMLFormElement;
    form.addEventListener('submit', evt => {
        evt.preventDefault();
        if(validarCuenta()){
            form.submit();
        } else {
            let erroresContainer = document.getElementById('erroresContainer');
            while(erroresContainer.hasChildNodes()){
                erroresContainer.removeChild(erroresContainer.childNodes[0])
            }
            if(!validarNombre()) handlerErrorEditar('El nombre debe tener 3 carácteres o más');
            if(!validarApellido()) handlerErrorEditar('El apellido debe tener 3 carácteres o más');
            if(!validarCorreo()) handlerErrorEditar('El formato del correo electrónico no es valido');
        }
    })
}

const validarCuenta = () => {
    return validarNombre() && validarApellido() && validarCorreo();
}

const handlerErrorEditar= (error : string) => {
    const erroresContainer = document.getElementById('erroresContainer');
    let pError = document.createElement('p');
    pError.className = 'error';
    pError.append(error);
    erroresContainer.appendChild(pError);
}