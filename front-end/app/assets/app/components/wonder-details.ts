import {Component, Input, EventEmitter, Output, ElementRef, Inject, ViewChildren, QueryList, SimpleChange} from 'angular2/core';
import {Response} from 'angular2/http';
import {Comment, Wonder} from '../model/worldwonders-model';
import {WonderService} from '../service/wonders.service';
import {Observable} from 'rxjs/Observable';
import AverageRating from './average-rating';

declare var jQuery: any;

@Component({
  selector: 'wonder-details',
  templateUrl: 'assets/app/templates/wonder-details.html',
  directives: [AverageRating]
})
export default class Details {

  @ViewChildren(AverageRating) ratings: QueryList<AverageRating>

  private newComment: Comment = {
      topic: "",
      user: "Anonymous",
      body: "",
      rating:0
    };

  @Input() wonder : Wonder;
  @Input() commentList: Comment[];

  @Output() backToListClicked = new EventEmitter<any>();

  constructor(@Inject(ElementRef) private elementRef: ElementRef,
    @Inject(WonderService) private wonderService: WonderService) {
  }

  show(wonder: Wonder) {
    this.wonder = wonder;
    this.resetComment();
    this.newComment.topic = this.wonder.englishName;
    this.wonderService.getCommentsFor(wonder).subscribe( comments => {
      console.log(comments);
      this.commentList = comments;
    },
    error => console.log(error)
  );
    jQuery(this.elementRef.nativeElement).show();
  }

  hide() {
    this.wonder = undefined;
    jQuery(this.elementRef.nativeElement).hide();
  }

  back() {
    this.backToListClicked.emit(null);
  }

  submitComment() {
      let comment = this.newComment
      if(comment.topic.length > 0 && comment.body.length > 0 && comment.rating >= 1) {
        this.wonderService.submitComment(comment)
          .subscribe(res => {
            console.log(res);
            this.show(this.wonder);
          });
      }
  }

  ngOnInit() {
    this.hide();
  }

  ngAfterViewInit() {
    jQuery('#star').raty({
       half: true ,
       click: (score: number, evt: any) => {
         this.newComment.rating = score;
       },
       mouseover: (score: number, evt: any) => {
         this.newComment.rating = score;
       },
    });
  }

  private handleError (error: Response) {
   console.error(error);
   return Observable.throw(error.json().error || 'Server error');
  }

  private resetComment() {
    this.newComment.topic = "";
    this.newComment.user = "Anonymous";
    this.newComment.body = "";
    this.newComment.rating = 0;
    jQuery('#star').raty('score', 0);
  }
}
