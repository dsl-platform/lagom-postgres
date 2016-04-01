import {Component, Input, EventEmitter, Output, ElementRef, Inject} from 'angular2/core';
import {Comment, Wonder} from '../model/worldwonders-model';
import {WonderService} from '../service/wonders.service';

declare var jQuery: any;

@Component({
  selector: 'wonder-details',
  templateUrl: 'assets/app/templates/wonder-details.html'})
export default class Details {

  private commentBody: string = "";
  private newCommentRating: number = 0;
  @Input() wonder : Wonder;
  @Input() commentList: Comment[];

  @Output() backToListClicked = new EventEmitter<any>();

  constructor(@Inject(ElementRef) private elementRef: ElementRef,
    @Inject(WonderService) private wonderService: WonderService) {
  }

  show(wonder: Wonder) {
    this.wonder = wonder;
    this.wonderService.getCommentsFor(wonder).subscribe(
      comments => this.commentList = comments
    );
    this.commentBody = "";
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

  submitComment() {
    console.log(this.newCommentRating);
    console.log(this.commentBody);
  }

  ngOnInit() {
    this.hide();
  }
}
