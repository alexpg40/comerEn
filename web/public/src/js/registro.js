import { validarNombre, validarApellido, validarContraseña, validarCorreo, validarRContraseña } from './validaciones';
window.onload = () => {
    let form = document.getElementById('formIniciarSesion');
    form.addEventListener('submit', evt => {
        evt.preventDefault();
        if (validarRegistro()) {
            form.submit();
        }
        else {
            let erroresContainer = document.getElementById('erroresContainer');
            while (erroresContainer.hasChildNodes()) {
                erroresContainer.removeChild(erroresContainer.childNodes[0]);
            }
            if (!validarNombre())
                handlerErrorRegistro('El nombre debe tener 3 carácteres o más');
            if (!validarApellido())
                handlerErrorRegistro('El apellido debe tener 3 carácteres o más');
            if (!validarCorreo())
                handlerErrorRegistro('El formato del correo electrónico no es valido');
            if (!validarContraseña())
                handlerErrorRegistro('La contraseña debe tener 8 caracteres y al menos un número');
            if (!validarRContraseña())
                handlerErrorRegistro('La contraseña no coinciden');
        }
    });
};
const validarRegistro = () => {
    return validarNombre() && validarApellido() && validarCorreo() && validarContraseña() && validarRContraseña();
};
const handlerErrorRegistro = (error) => {
    const erroresContainer = document.getElementById('erroresContainer');
    let pError = document.createElement('p');
    pError.className = 'error';
    pError.append(error);
    erroresContainer.appendChild(pError);
};
//# sourceMappingURL=registro.js.map