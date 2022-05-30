import {getEtiquetas, getRestaurantes, getRestaurantesCercanos} from './services.js'
import {Restaurante, Etiqueta, Punto} from './d'

window.onload = () => {
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    form.addEventListener('submit', handlerSubmit);
    input.addEventListener('input', handlerAutoComplete);
    initLocalizacion()
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

const initLocalizacion = () => {
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(obtenerLocalizacion, errorLocalizacion);
    }
}

const obtenerLocalizacion = async ({coords}) => {
    const restaurantes = await getRestaurantesCercanos(coords);
    crearRestaurantes('Restaurantes más cercanos', restaurantes)
    localStorage.setItem('ultimaUbicacion', `${coords.longitude}|${coords.latitude}`);
}

const errorLocalizacion = async () => {
    if(localStorage.getItem('ultimaUbicacion')){
        const strPuntos = localStorage.getItem('ultimaUbicacion');
        const coords = { longitude: Number(strPuntos.split('|')[1]), latitude: Number(strPuntos.split('|')[0])}
        const restaurantes = await getRestaurantesCercanos(coords);
        crearRestaurantes('Restaurantes más cercanos desde tu última ubicación', restaurantes)
    }
}

const crearRestaurantes = (nombreSeccion: string, restaurantes: Array<Restaurante>) => {
    const h2Seccion = document.querySelector('#restaurantes > h2');
    h2Seccion.textContent = nombreSeccion;
    const sectionRestaurantes = document.querySelector('#restaurantesContainer');
    while(sectionRestaurantes.hasChildNodes()){
        sectionRestaurantes.removeChild(sectionRestaurantes.childNodes[0])
    }
    restaurantes.forEach(restaurante => {
        const restauranteArticle = crearRestaurante(restaurante)
        sectionRestaurantes.appendChild(restauranteArticle)
    })
}

const crearRestaurante = (restaurante : Restaurante) => {
    const aRestaurante = document.createElement('a');
    aRestaurante.href = `controlador?restaurante=${restaurante.idRestaurante}`
    const articleRestaurante = document.createElement('article');
    articleRestaurante.className = 'restaurante';
    aRestaurante.append(articleRestaurante)
    const h3Restaurante = document.createElement('h3');
    h3Restaurante.append(restaurante.nombre);
    articleRestaurante.append(h3Restaurante);
    const hr = document.createElement('hr');
    articleRestaurante.append(hr);
    const sectionRestaurante = document.createElement('section');
    articleRestaurante.appendChild(sectionRestaurante)
    sectionRestaurante.className = 'bodyRestaurante';
    const articleImg = document.createElement('article')
    sectionRestaurante.appendChild(articleImg)
    const imgRestaurante = document.createElement('img')
    imgRestaurante.className = 'imagenRestaurante';
    imgRestaurante.src = `public/img/${restaurante.icono}`
    imgRestaurante.alt = `Imagen del restaurante ${restaurante.nombre}`
    articleImg.append(imgRestaurante)
    const articleCointainerDescripcion = document.createElement('article');
    articleCointainerDescripcion.className = 'containerDescripcion';
    sectionRestaurante.appendChild(articleCointainerDescripcion)
    const articleDescripcion = document.createElement('article');
    articleDescripcion.className = 'descripcionRestaurante';
    articleDescripcion.append(restaurante.descripcion);
    articleCointainerDescripcion.appendChild(articleDescripcion)
    const articleValoracion = document.createElement('article')
    articleValoracion.className = 'valoracionRestaurante';
    articleValoracion.append('★★★★★')
    articleCointainerDescripcion.appendChild(articleValoracion)
    return aRestaurante
}