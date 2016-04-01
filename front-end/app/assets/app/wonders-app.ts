import {Component, Inject, OnInit, ElementRef, SimpleChange, ViewChild} from 'angular2/core';
import {WonderService} from './service/wonders.service';
import {Wonder} from './model/worldwonders-model';
import AverageRating from './components/average-rating';
import Details from './components/wonder-details';
import WondersGrid from './components/wonders-grid';

declare var jQuery: any;

@Component({
  selector: 'wonders-app',
  templateUrl: 'assets/app/templates/wonders-app.html'
  , directives:[WondersGrid, Details]
  })

export default class WondersApp {

  @ViewChild(WondersGrid) private grid: WondersGrid;
  @ViewChild(Details) private details: Details;

  private wonderTypes: String[];
  private wonders: Wonder[];

  constructor(
    @Inject(WonderService) private wonderService: WonderService
  ) {

  }

  detailsClicked(wonder: Wonder) {
    console.log("Details event received");
    console.log(wonder);
    this.grid.hide();
    this.details.show(wonder);
  }

  backToListClicked(e) {
    console.log("Back to list event received");
    console.log(e);
    this.grid.show();
    this.details.hide();
  }

  ngOnInit() {
    this.wonderService.getAllWonders().subscribe(wonders => {
      console.log(wonders);
      this.wonders = wonders;
    });
    this.wonderService.getAllWonderTypes().subscribe(wonderTypes => this.wonderTypes = wonderTypes);
  }
}
