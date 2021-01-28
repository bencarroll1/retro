import {Pipe} from '@angular/core';

import {Retro} from '../../models/retro';

@Pipe({
  name: 'retroArchiveFilter',
  pure: false
})
export class RetroArchivedPipe {
  transform(retros: Retro[], retroArchiveFilter: boolean) {
    if (!retroArchiveFilter) {
      return retros.filter(retro => {
        return retro.archiveRetroFlag === false;
      });
    } else {
      return retros.filter(retro => {
        return retro.archiveRetroFlag === true;
      });
    }
  }
}
