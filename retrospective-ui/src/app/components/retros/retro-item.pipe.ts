import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'itemFilter',
  pure: false
})
export class RetroItemPipe implements PipeTransform {
  transform(items: any[], itemFilter: string): any {
    if (!items || !itemFilter) {
      return items;
    }
    return items.filter(itemType => itemType.type.indexOf(itemFilter) !== -1);
  }
}
