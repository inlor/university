import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public login;

  constructor(private auth: AuthService) {
    this.login = this.auth.isLogin;
  }

  ngOnInit(): void {
  }

}
