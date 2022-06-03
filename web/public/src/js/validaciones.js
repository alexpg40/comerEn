export const validarNombre = () => {
    let nombreInput = document.getElementsByName('nombre')[0];
    if (nombreInput.value != "" && nombreInput.value.length >= 3) {
        return true;
    }
    return false;
};
export const validarApellido = () => {
    let apellidoInput = document.getElementsByName('apellido')[0];
    if (apellidoInput.value != "" && apellidoInput.value.length >= 3) {
        return true;
    }
    return false;
};
export const validarCorreo = () => {
    const regexCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let correoInput = document.getElementsByName('correo')[0];
    return regexCorreo.test(correoInput.value);
};
export const validarContraseña = () => {
    const regexContraseña = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    let contraseña = document.getElementsByName('contrasena')[0];
    return regexContraseña.test(contraseña.value);
};
export const validarRContraseña = () => {
    let contraseña = document.getElementsByName('contrasena')[0];
    let rcontraseña = document.getElementsByName('rContrasena')[0];
    return contraseña.value === rcontraseña.value;
};
