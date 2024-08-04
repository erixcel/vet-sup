import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterTableModal'
})
export class FilterTableModalPipe implements PipeTransform {

  transform(lista: any, nombreColumnas:string[], cadena: string): any {
    cadena = cadena.trim().toLowerCase()
    if(cadena === '') return lista;
    const listaFiltrada = [];
    for(let i of lista){
      for(let j of nombreColumnas){
        if((this.isFecha(eval(`i.${j}`)).toString()).toLowerCase().indexOf(cadena) > -1)
        {
          listaFiltrada.push(i)
          break;
        }
      }
    }
    return listaFiltrada
  }

  isFecha(dato:any){
    if (typeof(dato) == typeof(new Date())) {
      return dato.toDate().toLocaleDateString()
    }
    else{
      return dato
    }
  }

}
