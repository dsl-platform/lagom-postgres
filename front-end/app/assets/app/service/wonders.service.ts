import {Injectable} from 'angular2/core';
import {Http, HTTP_PROVIDERS} from 'angular2/http';
import {Observable} from 'rxjs/Observable';

export interface Wonder {
  URI: string;
  englishName: string;
  wonderType: string;
  nativeNames: string[];
  description: string;
  ordinal: number;
  imageInfo: ImageInfo;
  totalRatings: number;
  averageRating: number;
}

export interface ImageInfo {
  imageLink: string;
  doubleWidth: boolean;
  doubleHeight: boolean;
}


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
}


