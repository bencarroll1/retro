import {Pipe, PipeTransform} from '@angular/core';

import {Retro} from '../../models/retro';

@Pipe({
  name: 'retroFilter',
  pure: false
})
export class RetroPipe implements PipeTransform {
  transform(retros: Retro[], retroNameFilter: string): Retro[] {
    if (!retros || !retroNameFilter) {
      return retros;
    }
    return retros.filter(retro => retro.name.toLowerCase().includes(retroNameFilter.toLowerCase()));
  }
}
