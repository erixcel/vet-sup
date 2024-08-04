import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterTable'
})
export class FilterTablePipe implements PipeTransform {

  transform(collection: any, nameColumn:string, text: string, isModified:boolean): any {
    text = text.trim().toLowerCase()
    if(text === '') return collection;
    const collectionFilter = [];
    if(nameColumn.toString().indexOf("fecha") > -1) {
      for(const i of collection){
        if(i?.[`${nameColumn}`] && new Date(i?.[`${nameColumn}`]).toLocaleDateString().indexOf(text) > -1) collectionFilter.push(i);
      }
    } else {
      for(const i of collection){
        if(i?.[`${nameColumn}`].toString().toLowerCase().indexOf(text) > -1) collectionFilter.push(i);
      }
    }
    return collectionFilter;
  }

}
