import {Component, Inject, OnInit} from 'angular2/core';
import {WonderService, Wonder} from './service/wonders.service';

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
                <label for="check-{{ wonderType | lowercase }}">{{ wonderType }}</label>
                <button id="check-{{ wonderType | lowercase }}" class="btn" ></button>
              </template>
<!--

            @for(wonderType <- wonderTypes) {
              <label for="check-@wonderType.lowerName">@wonderType.name</label>
              <button id="check-@wonderType.lowerName" class="btn" data-filter=".@wonderType.lowerName"></button>
            }

            -->
            </div>
            <!-- /filter -->
            <div class="grid">
              <!-- .grid-sizer empty element, only used for element sizing -->
              <div class="grid-sizer"></div>

            <!--
            @for(wonder <- wonderList) {
              <div class="grid-item @{wonder.getWonderType.lowerName}@{
                  (if (wonder.getImageInfo.getDoubleWidth) " item-w2" else "") +
                  (if (wonder.getImageInfo.getDoubleHeight) " item-h2" else "")
              }">
                <div class="feat-box" style="background-image: url('@wonder.getImageInfo.getImageLink');">
                  <div class="feat-box-txt">
                    <h4 class="feat-title">@wonder.getEnglishName
                  @for(nativeName <- wonder.getNativeNames) {
                    <span class="native">( @nativeName )</span>
                  }</h4>
                    <ul class="comment-list">
                    @for(comment <- wonder.getChosenComments) {
                      <li>@comment.getBody</li>
                    }
                    </ul>
                    @ratings(Option(wonder.getAverageRating))
                    <a href="#" class="feat-link">Details</a>
                  </div>
                </div>
              </div>
            }
            -->



            </div><!-- /grid -->
          </div><!-- /container -->
        </section><!-- /.section-isotope -->
  `
})
export default class WondersApp {
  private wonderTypes = ["Ancient", "Modern"];
  private wonders: Wonder[];

  constructor(
    @Inject(WonderService) private wonderService: WonderService
  ) {}

  ngOnInit() {
    this.wonders = this.wonderService.getAllWonders();
  }
}