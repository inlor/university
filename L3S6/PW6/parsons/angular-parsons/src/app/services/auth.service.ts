import * as moment from 'moment';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public isLogin = false;

  constructor(private http: HttpClient, private router: Router) {
    this.isLogin = this.isLoggedIn();
  }

  login(email: string, password: string) {
    return this.http.post(environment.token, {email, password});
  }

  setSession(authResult) {
    this.isLogin = true;
    const expiresAt = moment().add(90000, 'second');
    localStorage.setItem('id_token', authResult.token);
    localStorage.setItem('expires_at', JSON.stringify(expiresAt.valueOf()));
  }

  logout() {
    this.isLogin = false;
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    this.router.navigateByUrl('/').then();
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  getUser() {
    const jwt = localStorage.getItem('id_token');

    const jwtData = jwt.split('.')[1];
    const decodedJwtJsonData = window.atob(jwtData);
    return JSON.parse(decodedJwtJsonData);
  }

  getRole() {
    return this.getUser().roles[0];
  }
}
