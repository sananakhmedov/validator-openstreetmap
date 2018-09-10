import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest, HttpEvent, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class DemoService {

  private articleUrl = "http://localhost:7090/maps";

  constructor(private http: HttpClient) {
  }

  statistic(fileName): any {
    console.info(fileName);

    return this.http.get(this.articleUrl + '?fileName='+ fileName)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: HttpResponse<any>) {
    return res;
  }

  private handleError (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }

  upload(file: File): Observable<HttpEvent<{}>>{
    let formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', this.articleUrl, formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req)
      .map(this.extractData)
      .catch(this.handleError)
  }

}
