import 'rxjs/Rx';
import {bootstrap} from 'angular2/platform/browser';
import AverageRating from './average-rating';
import WondersApp from './wonders-app';

// Service providers for the application:
import {WonderService} from './service/wonders.service';
import {HTTP_PROVIDERS} from 'angular2/http';

bootstrap(WondersApp, [AverageRating, WonderService, HTTP_PROVIDERS]);