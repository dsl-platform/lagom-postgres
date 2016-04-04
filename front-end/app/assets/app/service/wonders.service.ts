import {Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS, Response, Headers, RequestOptions} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Wonder, ImageInfo, Comment} from '../model/worldwonders-model';

@Injectable()
export class WonderService {

  constructor(private http: Http) {
  }

  getAllWonders(): Observable<Wonder[]> {
    return this.http.get('/wonders')
       .map(res => <Wonder[]> res.json())
       .catch(this.handleError);
  }

  getAllWonderTypes(): Observable<string[]> {
      return this.http.get('/wonderTypes')
         .map(res => <String[]> res.json())
         .catch(this.handleError);
  }

  getCommentsFor(wonder: Wonder): Observable<Comment[]> {
      return this.http.get('/comments/'+wonder.englishName)
         .map(res => <Comment[]> res.json())
         .catch(this.handleError);
  }

  submitComment(comment: Comment): Observable<Response> {
    let body = JSON.stringify(comment);
    let headers = new Headers({'Content-Type':'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post('/comment', body, options)
      .map(res => res)
      .catch(this.handleError);
  }

  private handleError (error: Response) {
   console.error(error);
   return Observable.throw(error.json().error || 'Server error');
  }


}
