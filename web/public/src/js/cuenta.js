import { validarNombre, validarApellido, validarContraseña, validarCorreo } from './validaciones.js';
window.onload = () => {
    let form = document.getElementById('formActualizarCuenta');
    form.addEventListener('submit', evt => {
        evt.preventDefault();
        if (validarCuenta()) {
            form.submit();
        }
        else {
            let erroresContainer = document.getElementById('erroresContainer');
            while (erroresContainer.hasChildNodes()) {
                erroresContainer.removeChild(erroresContainer.childNodes[0]);
            }
            if (!validarNombre())
                handlerErrorCuenta('El nombre debe tener 3 carácteres o más');
            if (!validarApellido())
                handlerErrorCuenta('El apellido debe tener 3 carácteres o más');
            if (!validarCorreo())
                handlerErrorCuenta('El formato del correo electrónico no es valido');
            if (!validarContraseña())
                handlerErrorCuenta('La contraseña debe tener 8 caracteres y al menos un número');
        }
    });
};
const validarCuenta = () => {
    return validarNombre() && validarApellido() && validarCorreo() && validarContraseña();
};
const handlerErrorCuenta = (error) => {
    const erroresContainer = document.getElementById('erroresContainer');
    let pError = document.createElement('p');
    pError.className = 'error';
    pError.append(error);
    erroresContainer.appendChild(pError);
};
//# sourceMappingURL=cuenta.js.map