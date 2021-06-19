import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    })
  };

  constructor(protected httpClient: HttpClient) {
  }

  public getAll(path: string): Observable<any> {
    return this.httpClient
      .get(environment.host + path, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  public get(path: string, id: number): Observable<any> {
    return this.httpClient
      .get(environment.host + path + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  public store(path: string, json): Observable<any> {
    return this.httpClient
      .post(environment.host + path, json, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  public update(path: string, id: number, json): Observable<any> {
    return this.httpClient
      .put(environment.host + path + '/' + id, json, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  public delete(path: string, id: number): Observable<any> {
    return this.httpClient
      .delete(environment.host + path + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
      console.log(error);
    }
    return throwError('Something bad happened; please try again later.');
  }
}
