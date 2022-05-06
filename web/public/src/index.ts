window.onload = () => {
    const form = document.getElementsByTagName('form')[0];
    form.addEventListener('submit', handlerSubmit);
}

const validarInput = (input : String) : Boolean => {
    if(input.length < 3) return false;
    return true;
}

const handlerSubmit = (eve : Event) : void => {
    eve.preventDefault();
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0] as HTMLInputElement;
    let inputValido = validarInput(input.value);
    if(inputValido) { form.submit(); return };
    alert('Necesitas al menos tres carácteres');
}