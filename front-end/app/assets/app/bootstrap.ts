import {bootstrap} from 'angular2/platform/browser';
import WondersApp from './wonders-app';

// Service providers for the application:
import {WonderService} from './service/wonders.service';

bootstrap(WondersApp, [WonderService]);