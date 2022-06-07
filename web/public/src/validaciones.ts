export const validarNombre = () => {
    let nombreInput = document.getElementsByName('nombre')[0] as HTMLInputElement;
    if(nombreInput.value != "" && nombreInput.value.length >= 3) {
        return true;
    }
    return false;
}

export const validarApellido = () => {
    let apellidoInput = document.getElementsByName('apellido')[0] as HTMLInputElement;
    if(apellidoInput.value != "" && apellidoInput.value.length >= 3) {
        return true;
    }
    return false;
}

export const validarCorreo = () => {
    const regexCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let correoInput = document.getElementsByName('correo')[0] as HTMLInputElement;
    return regexCorreo.test(correoInput.value);
}

export const validarContraseña = () => {
    const regexContraseña = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    let contraseña = document.getElementsByName('contrasena')[0] as HTMLInputElement;
    return regexContraseña.test(contraseña.value);
}

export const validarRContraseña = () => {
    let contraseña = document.getElementsByName('contrasena')[0] as HTMLInputElement;
    let rcontraseña = document.getElementsByName('rContrasena')[0] as HTMLInputElement;
    return contraseña.value === rcontraseña.value;
}

export const validarInput = (input : String) : Boolean => {
    if(input.trim().length < 3) return false;
    if(input.trim() == "") return false;
    if(input.trim() == " ") return false;
    return true;
}