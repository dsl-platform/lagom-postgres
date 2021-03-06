import {Component, Inject, SimpleChange, Input, Output, EventEmitter, ElementRef} from 'angular2/core';
import {Wonder} from '../model/worldwonders-model';
import AverageRating from './average-rating';

declare var jQuery: any;

@Component({
  selector: 'wonders-grid',
  templateUrl: 'assets/app/templates/wonders-grid.html',
  directives : [AverageRating]
  })
export default class WondersGrid {

  hidden: boolean=false;

  private static first: boolean = true;
  @Input() private wonderTypes: String[];
  @Input() private wonders: Wonder[];

  @Output() detailsClicked = new EventEmitter<Wonder>();

  constructor(@Inject(ElementRef) private elementRef: ElementRef) {
  }

  details(wonder: Wonder) {
    this.detailsClicked.emit(wonder);
  }

  show() {
    jQuery(this.elementRef.nativeElement).show();
  }

  hide() {
    jQuery(this.elementRef.nativeElement).hide();
  }

  filterByWonderType(event: any) {
    var btn = jQuery(event.target || event.srcElement);
    if(btn && !btn.hasClass('active')) {
      jQuery('.filter .btn').removeClass('active');
      jQuery(btn).addClass('active');
      var filterValue = jQuery(btn).attr('data-filter');

      jQuery('.grid').isotope({
       filter: filterValue
      });
    }
  }

  ngAfterViewInit() {
    if(WondersGrid.first) {
      // TODO: This is a workaround, all of this needs to be put somewhere into the Angular2 workflow, if possible
      setTimeout(function(){
        jQuery('a[href*=#]').click(function(e: any) {
          e.preventDefault();
          if (jQuery(this.hash).offset()) {
            jQuery('html,body').animate({scrollTop: jQuery(this.hash).offset().top}, 400);
          }
        });

        jQuery('.grid').isotope({
         itemSelector: '.grid-item',
         percentPosition: true,
         masonry: {
           columnWidth: '.grid-sizer'
         }
        });

      }, 50);

      WondersGrid.first = false;
    }
  }
}
