import {Component, Input, EventEmitter, Output, ElementRef, Inject} from 'angular2/core';
import {Comment, Wonder} from '../model/worldwonders-model';

declare var jQuery: any;

@Component({
  selector: 'wonder-details',
  templateUrl: 'assets/app/templates/wonder-details.html'})
export default class Details {

  hidden: boolean = false;

  @Input() wonder : Wonder;
  @Input() commentList: Comment[];

  @Output() backToListClicked = new EventEmitter<any>();

  constructor(@Inject(ElementRef) private elementRef: ElementRef) {
  }

  show(wonder: Wonder) {
    this.wonder = wonder;
    jQuery(this.elementRef.nativeElement).show();
  }

  hide() {
    this.wonder = undefined;
    jQuery(this.elementRef.nativeElement).hide();
  }

  back() {
    console.log("Back clicked");
    this.backToListClicked.emit(null);
  }
}
