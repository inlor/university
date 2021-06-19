import {Component, OnInit} from '@angular/core';
import {UserModel} from '../models/user.model';
import {Role} from '../models/role';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';
import {Helper} from '../helper/helper';
import {ApiService} from '../services/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public error = false;
  public roles = [Role.STUDENT, Role.TEACHER];
  public role;
  public user = new UserModel();

  constructor(private router: Router, private api: ApiService, private authService: AuthService) {

  }

  ngOnInit(): void {
  }

  register(): void {
    this.error = false;
    this.user.roles = [];
    this.user.roles.push(this.role);
    if (this.user.username && this.user.email && this.user.password && this.user.roles && Helper.validateEmail(this.user.email)) {
      this.api.store('users', this.user.serialize())
        .subscribe(e => {
            this.authService.login(this.user.email, this.user.password)
              .subscribe(user => {
                  this.authService.setSession(user);
                  this.router.navigateByUrl('/dashboard').then();
                }
              );
          },
          error => {
            this.error = true;
          });
    }
  }
}
