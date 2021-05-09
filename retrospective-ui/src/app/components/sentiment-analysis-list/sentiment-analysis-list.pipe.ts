import {Pipe, PipeTransform} from '@angular/core';
import {Retro} from '../../models/retro';

@Pipe({
  name: 'sentimentAnalysisListRetroFilter'
})
export class SentimentAnalysisListPipe implements PipeTransform {
  transform(retros: Retro[], retroNameFilter: string): Retro[] {
    if (!retros || !retroNameFilter) {
      return retros;
    }
    return retros.filter(retro => retro.name.toLowerCase().includes(retroNameFilter.toLowerCase()));
  }
}
