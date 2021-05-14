import {Pipe, PipeTransform} from '@angular/core';
import {Item} from '../../models/item';

@Pipe({
  name: 'sortByVotes',
  pure: false
})

// pipe to sort retrospective items by no. of votes
export class RetroItemVotesPipe implements PipeTransform {
  transform(items: Item[], args?: any): Item[] {
    return items.sort((a: Item, b: Item) => {
      return b.itemVotes - a.itemVotes;
    });
  }
}
