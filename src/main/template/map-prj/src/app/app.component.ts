import {Component, OnInit} from '@angular/core';
import {DemoService} from "./app.service";

import { } from '@types/googlemaps';
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'OpenStreetMap Validator';
  data: any = {};

  statisticData: any = {};

  fileToUpload: File = null;
  fileSelected: boolean = false;
  statisticCalled: boolean = false;

  closeResult: string;

  boolValue: boolean = false;

  numberOfGooglePlaces;
  numberOfOpenPlaces;
  numberOfFalseOpen;

  width = 600;
  height = 400;
  type = 'pie3d';
  dataFormat = 'json';
  dataSourceGoogle;
  dataSource;

  constructor(private demoService: DemoService,
              private modalService: NgbModal) {
    this.dataSourceGoogle = {
      "chart": {
        "caption": "Openstreetmap places names mapping",
        "subcaption": "Comparing to Google Maps Places",
        "startingangle": "120",
        "showlabels": "1",
        "showlegend": "1",
        "enablemultislicing": "0",
        "slicingdistance": "15",
        "showpercentvalues": "1",
        "showpercentintooltip": "1",
        "plottooltext": "Age group : $label Total visit : $datavalue",
        "theme": "ocean"
      },
      "data": [
        {
          "label": "Google Alle Places",
          "value": this.numberOfGooglePlaces,
          "color": "#3ADF00"
        },
        {
          "label": "Openstreet Alle Places",
          "value": this.numberOfOpenPlaces,
          "color": "#A9F5F2"
        }
      ]
    };

    this.dataSource = {
      "chart": {
        "caption": "Openstreetmap places names mapping",
        "startingangle": "120",
        "showlabels": "1",
        "showlegend": "1",
        "enablemultislicing": "0",
        "slicingdistance": "15",
        "showpercentvalues": "1",
        "showpercentintooltip": "1",
        "plottooltext": "Age group : $label Total visit : $datavalue",
        "theme": "ocean"
      },
      "data": [
        {
          "label": "Openstreet Richtig abgebildet",
          "value": this.numberOfOpenPlaces - this.numberOfFalseOpen,
          "color": "#3ADF00"
        },
        {
          "label": "Openstreet Falsch abgebildet",
          "value": this.numberOfFalseOpen,
          "color": "#FF0000"
        }
      ]
    };

  }

  private isEmpty(obj) {
    for(var key in obj) {
      if(obj.hasOwnProperty(key))
        return false;
    }
    return true;
  }

  open(content) {
    this.modalService.open(content, {size: 'lg', ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  afterStatistic() {
    /*this.numberOfGooglePlaces = 60;
    this.numberOfOpenPlaces = 40;

    this.dataSourceGoogle.data[0].value = this.numberOfGooglePlaces;
    this.dataSourceGoogle.data[1].value = this.numberOfOpenPlaces;

    this.dataSource.data[0].value = this.numberOfOpenPlaces - this.numberOfFalseOpen;
    console.info("false open after statistic : " + this.numberOfFalseOpen);
    this.dataSource.data[1].value = this.numberOfFalseOpen;
*/
    if (this.statisticData.hasOwnProperty('numOfGooglePlaces')) {
      this.numberOfGooglePlaces = this.statisticData["numOfGooglePlaces"];
      this.numberOfOpenPlaces = this.statisticData["numOfOpenstreetMapPlaces"];

      this.dataSourceGoogle.data[0].value = this.numberOfGooglePlaces;
      this.dataSourceGoogle.data[1].value = this.numberOfOpenPlaces;

      this.dataSource.data[0].value = this.numberOfOpenPlaces - this.numberOfFalseOpen;
      // console.info("false open after statistic : " + this.numberOfFalseOpen);
      this.dataSource.data[1].value = this.numberOfFalseOpen;

      this.boolValue = false;
    }
  }

  ngOnInit() {

    let map;
    map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 41.016309, lng: 28.963770},
      zoom: 15
    });

    if (this.fileSelected) {
      this.handleStatistic(this.fileToUpload.name);
    }

    if (this.data !== undefined) {
      if (this.data.body !== undefined) {

        this.data = JSON.parse(this.data.body);

        if (!this.isEmpty(this.data)) {

          let keys = Object.keys(this.data);

          let splitCoordinateFocus = keys[0].split(',');

          const FOCUS = {lat: Number(splitCoordinateFocus[0]), lng: Number(splitCoordinateFocus[1])};

          map = new google.maps.Map(document.getElementById('map'), {
            center: FOCUS,
            zoom: 15
          });

          var infowindow = new google.maps.InfoWindow();

          var marker;
          var i = 0;

          this.numberOfFalseOpen = keys.length;
          //
          //
          // console.info("false open : " + this.numberOfFalseOpen);

          for (let coordinate of keys) {
            //coordinate = 123.3 80.3
            let value = this.data[coordinate];
            //value = google : name
            // open : name

            let googleName = value["google"];
            let openName = value["openstreet"];
            let foursqName = value["foursquare"];
            let microsoftName = value["microsoft"];


            let splitCoordinate = coordinate.split(',');

            let lat = splitCoordinate[0];
            let lon = splitCoordinate[1];

            let openStreetMapLink = "<a target=\"_blank\" href=\"https://www.openstreetmap.org/#map=18/@LAT/@LON\">" +
              "Open openstreetMap<a/>";
            openStreetMapLink = openStreetMapLink
              .replace("@LAT", lat)
              .replace("@LON", lon);

            // console.info(openStreetMapLink);

            marker = new google.maps.Marker({
              position: new google.maps.LatLng(Number(lat), Number(lon)),
              map: map
            });

            marker.setMap(map);

            google.maps.event.addListener(marker, 'click', (function(marker, i) {
              return function() {
                infowindow.setContent('location(' + lat + "," + lon + ") - " + openStreetMapLink + "<br />" + "google:" +
                  " " + googleName +
                  "<br />" + "open: " +openName + "<br />" + "foursquare: " + foursqName
                  + "<br />" + "microsoft: " + microsoftName);

                infowindow.open(map, marker);
              }
            })(marker, i));

            i++;

          }

        }

      }
    }

  }

  handleStatistic(fileName) {
    // console.info("file : " + fileName);
    this.demoService.statistic(fileName).subscribe(
      // the first argument is a function which runs on success
      data => { /*console.log('check THIS!!' + data);*/ this.statisticData = data
      },
      // the second argument is a function which runs on error
      err => console.error(err),
      // the third argument is a function which runs on completion
      () => { console.log('done loading data'); this.statisticCalled = true; this.afterStatistic();}
    );
  }

  handleFileInput(files: FileList) {
    console.info("in post and get");

    this.fileToUpload = files.item(0);

    if (this.fileToUpload.name.includes('&')) {
      alert('Please dont use ampersand for the name of file, upload new file!');
      return;
    }

    this.boolValue = true;

    this.demoService.upload(this.fileToUpload).subscribe(
      // the first argument is a function which runs on success
      data => {
        // console.log('check THIS!!' + data);
        this.fileSelected = true;
        this.data = data;
      },
      // the second argument is a function which runs on error
      err => console.error(err),
      // the third argument is a function which runs on completion
      () => { console.log('done loading data'); this.ngOnInit(); }
    );


    console.info("out getdata");


  }
}
