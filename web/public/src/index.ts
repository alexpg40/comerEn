import {getEtiquetas, getRestaurantes, getRestaurantesCercanos, getLocalidades, getRestaurantesPopulares, getRestaurantesFiltrados} from './services.js'
import {Restaurante, Etiqueta, Punto} from './d'

var restaurantesG : Array<Restaurante> = []

window.onload = () => {
    init()
}

const validarInput = (input : String) : Boolean => {
    if(input.length < 3) return false;
    return true;
}

const init = () => {
    initListeners()
    initLocalizacion()
    initRestaurantes()
}

const initListeners = () => {
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0];
    const valoracionMin = document.getElementsByName('valoracionMin')[0];
    const distanciaMax = document.getElementsByName('radio')[0];
    const formFiltrar = document.getElementById('formFiltrar') ;
    formFiltrar.addEventListener('submit', handlerFiltrar)
    valoracionMin.addEventListener('input', handlerOutputVal)
    distanciaMax.addEventListener('input', handlerOutputDis)
    form.addEventListener('submit', handlerSubmit);
    input.addEventListener('input', handlerAutoComplete);
}

const initRestaurantes = async () => {
    restaurantesG = await getRestaurantesPopulares();
}

const handlerOutputVal = (eve : Event) => {
    const output = document.querySelector('#outValoracion');
    const valoracionMin = eve.target as HTMLInputElement;
    output.textContent = crearEstrellas(parseInt(valoracionMin.value))
}

const handlerOutputDis = (eve : Event) => {
    const output = document.querySelector('#outRadio');
    const valoracionMin = eve.target as HTMLInputElement;
    output.textContent = `${valoracionMin.value} km`
}

const handlerFiltrar = (eve : Event) => {
    eve.preventDefault();
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(mostrarRestaurantesFiltrados, mostrarFiltradosSinUbicacion)
    }
}

const mostrarRestaurantesFiltrados = async ({coords})=> {
    const inputLocalidad = document.getElementsByName('localidadFiltros')[0] as HTMLInputElement;
    const inputValoracion = document.getElementsByName('valoracionMin')[0] as HTMLInputElement;
    const inputRadio = document.getElementsByName('radio')[0] as HTMLInputElement;
    restaurantesG = await getRestaurantesFiltrados(inputLocalidad.value, parseInt(inputValoracion.value), parseInt(inputRadio.value), coords);
    crearRestaurantes('Resultados filtrados', restaurantesG)
}

const mostrarFiltradosSinUbicacion = async ()=> {
    const inputLocalidad = document.getElementsByName('localidadFiltros')[0] as HTMLInputElement;
    const inputValoracion = document.getElementsByName('valoracionMin')[0] as HTMLInputElement;
    const inputRadio = document.getElementsByName('radio')[0] as HTMLInputElement;
    restaurantesG = await getRestaurantesFiltrados(inputLocalidad.value, parseInt(inputValoracion.value));
    crearRestaurantes('Resultados filtrados', restaurantesG)
}

const handlerSubmit = async (eve : Event) => {
    eve.preventDefault();
    const form = document.getElementsByTagName('form')[0];
    const input = document.getElementsByName('buscador')[0] as HTMLInputElement;
    let inputValido = validarInput(input.value);
    if(inputValido) {
        const restaurantes = await getRestaurantes(input.value);
        if(restaurantes.length > 1){ 
            ocultarRestaurante();
            crearRestaurantes('Resultados de la busqueda', restaurantes) 
        } else if(restaurantes.length = 1){
            form.submit();
        }
    } else {
        alert('Necesitas al menos tres carácteres');
    }
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
        const localidades = await getLocalidades(input.value);
        if(localidades.length > 0){
            crearAutoCompleteLocalidades(localidades);
        }
    } else{
        ocultarRestaurante()
    }
    if(!autoComplete.hasChildNodes()) mostrarNoHaySugerencias();
}

const crearAutoCompleteRestaurantes = (restaurantes: Array<Restaurante>): void => {
    const autoComplete = document.querySelector('.autocomplete');
    restaurantes.forEach(({idRestaurante, nombre}) => {
        const URL = `http://localhost:8080/comerEn/controlador?restaurante=${idRestaurante}`
        const aRestaurante = crearSugerencia(nombre , URL, 'restaurante')
        autoComplete.appendChild(aRestaurante)
    })
}

const crearAutoCompleteLocalidades = (localidades: Array<string>): void => {
    const autoComplete = document.querySelector('.autocomplete');
    localidades.forEach((localidad ) => {
        const URL = `http://localhost:8080/comerEn/controlador?localidad=${localidad}`
        const aRestaurante = crearSugerencia(localidad , URL, 'marker')
        autoComplete.appendChild(aRestaurante)
    })
}

const crearAutoCompleteEtiquetas = (etiquetas : Array<Etiqueta>) : void => {
    const autoComplete = document.querySelector('.autocomplete');
    etiquetas.forEach(({idEtiqueta, nombre}) => {
        const URL = `http://localhost:8080/comerEn/controlador?etiquetas=${idEtiqueta}`
        const aEtiqueta = crearSugerencia(nombre , URL, 'tag')
        autoComplete.appendChild(aEtiqueta)
    })
}

const crearSugerencia = (texto : string, url : string, imgUrl: string) : HTMLAnchorElement => {
    const aSugerencia = document.createElement('a');
    const imgSugerencia = document.createElement('img');
    imgSugerencia.src = `public/img/${imgUrl}.png`
    aSugerencia.append(imgSugerencia)
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
        const coords = { longitude: Number(strPuntos.split('|')[0]), latitude: Number(strPuntos.split('|')[1])}
        const restaurantes = await getRestaurantesCercanos(coords);
        crearRestaurantes('Restaurantes más cercanos desde tu última ubicación', restaurantes)
    }
}

const crearRestaurantes = (nombreSeccion: string, restaurantes: Array<Restaurante>) => {
    restaurantesG = restaurantes;
    const h2Seccion = document.querySelector('#restaurantes > h2');
    h2Seccion.textContent = nombreSeccion;
    const sectionRestaurantes = document.querySelector('.restaurantes');
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

const ocultarRestaurante = () => {
    const autoComplete = document.querySelector('.autocomplete') as HTMLElement;
    autoComplete.style.display = 'none';
}

const crearEstrellas = (nEstrellas : number) => {
    let ret = ''
    for(let i = 0; i < nEstrellas; i++){
        ret += '★'
    }
    return ret;
}