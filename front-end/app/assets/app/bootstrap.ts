import 'rxjs/Rx';
import {bootstrap} from 'angular2/platform/browser';
import AverageRating from './components/average-rating';
import Details from './components/wonder-details';
import WondersGrid from './components/wonders-grid';
import WondersApp from './wonders-app';

// Service providers for the application:
import {WonderService} from './service/wonders.service';
import {HTTP_PROVIDERS} from 'angular2/http';

bootstrap(WondersApp, [WondersGrid, AverageRating, Details, WonderService, HTTP_PROVIDERS]);
