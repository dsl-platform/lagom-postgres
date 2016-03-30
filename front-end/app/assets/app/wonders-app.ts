import {Component, Inject, OnInit} from 'angular2/core';
import {WonderService, Wonder} from './service/wonders.service';
import AverageRating from './average-rating';

@Component({
  selector: 'wonders-app',
  template: `
      <section class="section-isotope">
          <div class="container">
            <!-- filter -->
            <div class="filter">
              <label for="check-all">All</label>
              <button id="check-all" class="btn active"></button>

              <template ngFor #wonderType [ngForOf]="wonderTypes">
                <label attr.for="check-{{ wonderType | lowercase }}">{{ wonderType }}</label>
                <button id="check-{{ wonderType | lowercase }}" class="btn" ></button>
              </template>

            </div>
            <!-- /filter -->
            <div class="grid">
              <!-- .grid-sizer empty element, only used for element sizing -->
              <div class="grid-sizer"></div>

            <template ngFor #wonder [ngForOf]="wonders">
              <div class="grid-item {{wonder.wonderType | lowercase}} {{wonder.imageInfo.doubleWidth ? 'item-w2' : ''}} {{wonder.imageInfo.doubleHeight ? 'item-h2' : ''}}">
                <div class="feat-box" style="background-image: url('{{wonder.imageInfo.imageLink}}');">
                  <div class="feat-box-txt">
                    <h4 class="feat-title">{{wonder.englishName}}
                        <template ngFor #nativeName [ngForOf]="wonder.nativeNames">
                        <span class="native">{{nativeName}}</span>
                        </template>
                    </h4>
                    <ul class="comment-list">
                      <li *ngFor="#comment of wonder.chosenComments">{{comment.body}}</li>
                    </ul>
                    <average-rating [value]="wonder.getAverageRating"></average-rating>
                    <a href="#" class="feat-link">Details</a>
                  </div>
                </div>
              </div>
            </template>



            </div><!-- /grid -->
          </div><!-- /container -->
        </section><!-- /.section-isotope -->
  `
  , directives:[AverageRating]
  })

export default class WondersApp {
  private wonderTypes = ["Ancient", "Modern"];
  private wonders: Wonder[];

  constructor(
    @Inject(WonderService) private wonderService: WonderService
  ) {

  }

  ngOnInit() {
    this.wonderService.getAllWonders().subscribe(wonders => {
      console.log(wonders);
      this.wonders = wonders
    });
    this.wonderService.getAllWonderTypes().subscribe(wonderTypes => this.wonderTypes = wonderTypes);
  }
}