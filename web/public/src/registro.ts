window.onload = () => {
    try{
        handlerRegistro();
    }catch(err){
        alert(err.message);
    }
}

const handlerRegistro = () => {
    let submitInput = document.getElementsByName('registrar')[0] as HTMLInputElement;
    let form = document.getElementById('formIniciarSesion') as HTMLFormElement;
    submitInput.addEventListener('click', evt => {
        evt.preventDefault();
        if(validarRegistro()){
            form.submit();
        } else {
            if(!validarNombre()) alert('El nombre debe tener 3 carácteres o más');
            if(!validarApellido()) alert('El apellido debe tener 3 carácteres o más');
            if(!validarCorreo()) alert('El formato del correo electrónico no es valido');
            if(!validarContraseña()) alert('La contraseña debe tener 8 caracteres y al menos un número');
        }
    })
}

const validarRegistro = () => {
    return validarNombre() && validarApellido() && validarCorreo() && validarContraseña();
}

const validarNombre = () => {
    let nombreInput = document.getElementsByName('nombre')[0] as HTMLInputElement;
    if(nombreInput.value != "" && nombreInput.value.length >= 3) {
        return true;
    }
    return false;
}

const validarApellido = () => {
    let apellidoInput = document.getElementsByName('apellido')[0] as HTMLInputElement;
    if(apellidoInput.value != "" && apellidoInput.value.length >= 3) {
        return true;
    }
    return false;
}

const validarCorreo = () => {
    const regexCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let correoInput = document.getElementsByName('correo')[0] as HTMLInputElement;
    return regexCorreo.test(correoInput.value);
}

const validarContraseña = () => {
    const regexContraseña = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    let contraseña = document.getElementsByName('contrasena')[0] as HTMLInputElement;
    return regexContraseña.test(contraseña.value);
}