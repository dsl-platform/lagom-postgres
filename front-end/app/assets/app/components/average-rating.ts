import {Component, Input} from 'angular2/core';

@Component({
  selector: 'average-rating',
  templateUrl: 'assets/app/templates/average-rating.html'})
export default class AverageRating {
  @Input() averageRating: Number;

  private ratingRange = [1,2,3,4,5];

  private toStar(value: Number): String {    
    if (value >= 1) return "fa-star";
    else if (value >= 0.5) return "fa-star-half-o";
    else return "fa-star-o";
  }
}
