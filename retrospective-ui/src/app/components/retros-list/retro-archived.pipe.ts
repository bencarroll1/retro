import {Pipe} from '@angular/core';

import {Retro} from '../../models/retro';

@Pipe({
  name: 'retroArchiveFilter',
  pure: false
})

// pipe to filter retrospectives by archive status
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
