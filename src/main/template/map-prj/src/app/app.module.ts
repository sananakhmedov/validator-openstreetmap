import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {DemoService} from "./app.service";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

// Import FusionCharts library and chart modules
// Load FusionCharts

import { FusionChartsModule } from 'angular-fusioncharts';

import FusionCharts from 'fusioncharts/core';
// Load Charts module
import pie3d from 'fusioncharts/viz/pie3d';

// Load fusion theme
//import FusionTheme from 'fusioncharts/themes/es/fusioncharts.theme.fusion';

// Pass the fusioncharts library and chart modules
FusionChartsModule.fcRoot(FusionCharts, pie3d);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgbModule.forRoot(),
    // Specify FusionChartsModule as import
    FusionChartsModule
  ],
  providers: [
    DemoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
