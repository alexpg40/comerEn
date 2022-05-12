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
                handlerError('El nombre debe tener 3 carácteres o más');
            if (!validarApellido())
                handlerError('El apellido debe tener 3 carácteres o más');
            if (!validarCorreo())
                handlerError('El formato del correo electrónico no es valido');
            if (!validarContraseña())
                handlerError('La contraseña debe tener 8 caracteres y al menos un número');
            if (!validarRContraseña())
                handlerError('La contraseña no coinciden');
        }
    });
};
const validarRegistro = () => {
    return validarNombre() && validarApellido() && validarCorreo() && validarContraseña() && validarRContraseña();
};
const validarNombre = () => {
    let nombreInput = document.getElementsByName('nombre')[0];
    if (nombreInput.value != "" && nombreInput.value.length >= 3) {
        return true;
    }
    return false;
};
const validarApellido = () => {
    let apellidoInput = document.getElementsByName('apellido')[0];
    if (apellidoInput.value != "" && apellidoInput.value.length >= 3) {
        return true;
    }
    return false;
};
const validarCorreo = () => {
    const regexCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let correoInput = document.getElementsByName('correo')[0];
    return regexCorreo.test(correoInput.value);
};
const validarContraseña = () => {
    const regexContraseña = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    let contraseña = document.getElementsByName('contrasena')[0];
    return regexContraseña.test(contraseña.value);
};
const validarRContraseña = () => {
    let contraseña = document.getElementsByName('contrasena')[0];
    let rcontraseña = document.getElementsByName('rContrasena')[0];
    return contraseña.value === rcontraseña.value;
};
const handlerError = (error) => {
    const erroresContainer = document.getElementById('erroresContainer');
    let pError = document.createElement('p');
    pError.className = 'error';
    pError.append(error);
    erroresContainer.appendChild(pError);
};
//# sourceMappingURL=registro.js.map