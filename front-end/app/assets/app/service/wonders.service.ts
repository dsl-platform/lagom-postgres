import {Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Wonder, ImageInfo, Comment} from '../model/worldwonders-model';

@Injectable()
export class WonderService {

  constructor(private http: Http) {
  }

  getAllWonders(): Observable<Wonder[]> {
    return this.http.get('/wonders')
       .map(res => <Wonder[]> res.json());
  }

  getAllWonderTypes(): Observable<string[]> {
      return this.http.get('/wonderTypes')
         .map(res => <String[]> res.json());
  }

  getCommentsFor(wonder: Wonder): Observable<Comment[]> {
      return this.http.get('/comments/'+wonder.englishName)
         .map(res => <Comment[]> res.json());
  }
}
