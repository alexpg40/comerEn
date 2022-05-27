import {getEtiquetas, getRestaurantes} from './services'
import {Restaurante, Etiqueta} from './d'

window.onload = () => {
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    form.addEventListener('submit', handlerSubmit);
    input.addEventListener('input', handlerAutoComplete);
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

const handlerAutoComplete = async (eve : Event): Promise<void> => {
    const input = eve.target as HTMLInputElement;
    if(input.value.length > 0 ){
        const autoComplete = document.querySelector('.autocomplete') as HTMLElement;
        autoComplete.style.display = 'block';
        const restaurantes = await getRestaurantes(input.value);
        if(restaurantes.length > 0){
            crearAutoCompleteRestaurantes(restaurantes);
        }
        const etiquetas = await getEtiquetas(input.value);
        if(etiquetas.length > 0){
            crearAutoCompleteEtiquetas(etiquetas);
        }
    }
}

const crearAutoCompleteRestaurantes = (restaurantes: Array<Restaurante>): void => {
    const autoComplete = document.querySelector('.autocomplete');
    restaurantes.forEach(({idRestaurante, nombre}) => {
        const aRestaurante = document.createElement('a');
        aRestaurante.append(nombre);
        aRestaurante.href = `http://localhost:8080/comerEn/controlador?restaurante=${idRestaurante}`
        autoComplete.appendChild(aRestaurante);
    })
}

const crearAutoCompleteEtiquetas = (etiquetas : Array<Etiqueta>) : void => {
    const autoComplete = document.querySelector('.autocomplete');
    etiquetas.forEach(({idEtiqueta, nombre}) => {
        const aEtiqueta = document.createElement('a');
        aEtiqueta.append(nombre);
        aEtiqueta.href = `http://localhost:8080/comerEn/controlador?etiquetas=${idEtiqueta}`
        aEtiqueta.appendChild(aEtiqueta)
    })
}