import {getEtiquetas, getRestaurantes} from './services.js'
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
    const autoComplete = document.querySelector('.autocomplete') as HTMLElement;
    borrarAutoComplete();
    if(input.value.length > 2 ){
        autoComplete.style.display = 'block';
        const restaurantes = await getRestaurantes(input.value);
        if(restaurantes.length > 0){
            crearAutoCompleteRestaurantes(restaurantes);
        }
        const etiquetas = await getEtiquetas(input.value);
        if(etiquetas.length > 0){
            crearAutoCompleteEtiquetas(etiquetas);
        }
    } else{
        autoComplete.style.display = 'none';
    }
    if(!autoComplete.hasChildNodes()) mostrarNoHaySugerencias();
}

const crearAutoCompleteRestaurantes = (restaurantes: Array<Restaurante>): void => {
    const autoComplete = document.querySelector('.autocomplete');
    restaurantes.forEach(({idRestaurante, nombre}) => {
        const URL = `http://localhost:8080/comerEn/controlador?restaurante=${idRestaurante}`
        const aRestaurante = crearSugerencia(nombre , URL)
        autoComplete.appendChild(aRestaurante)
    })
}

const crearAutoCompleteEtiquetas = (etiquetas : Array<Etiqueta>) : void => {
    const autoComplete = document.querySelector('.autocomplete');
    etiquetas.forEach(({idEtiqueta, nombre}) => {
        const URL = `http://localhost:8080/comerEn/controlador?etiquetas=${idEtiqueta}`
        const aEtiqueta = crearSugerencia(nombre , URL)
        autoComplete.appendChild(aEtiqueta)
    })
}

const crearSugerencia = (texto : string, url : string) : HTMLAnchorElement => {
    const aSugerencia = document.createElement('a');
    aSugerencia.className = 'sugerencia';
    aSugerencia.append(texto);
    aSugerencia.href = url;
    return aSugerencia;
}

const borrarAutoComplete = () : void => {
    const autoComplete = document.querySelector('.autocomplete');
    while(autoComplete.hasChildNodes()){
        autoComplete.removeChild(autoComplete.childNodes[0]);
    }
}

const mostrarNoHaySugerencias = () => {
    const autoComplete = document.querySelector('.autocomplete');
    const pNoHaySugerencias = document.createElement('p');
    pNoHaySugerencias.append('No hay sugerencias con los datos introducidos!');
    autoComplete.append(pNoHaySugerencias);
}