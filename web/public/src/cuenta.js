window.onload = () => {
    try {
        handlerRegistro();
    }
    catch (err) {
        alert(err.message);
    }
};
const handlerRegistro = () => {
    let submitInput = document.getElementsByName('actualizarCuenta')[0];
    let form = document.getElementById('formActualizarCuenta');
    submitInput.addEventListener('click', evt => {
        evt.preventDefault();
        if (validarRegistro()) {
            form.submit();
        }
        else {
            if (!validarNombre())
                alert('El nombre debe tener 3 carácteres o más');
            if (!validarApellido())
                alert('El apellido debe tener 3 carácteres o más');
            if (!validarCorreo())
                alert('El formato del correo electrónico no es valido');
            if (!validarContraseña())
                alert('La contraseña debe tener 8 caracteres y al menos un número');
        }
    });
};
const validarRegistro = () => {
    return validarNombre() && validarApellido() && validarCorreo() && validarContraseña();
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
//# sourceMappingURL=registro.js.map