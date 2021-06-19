import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {Helper} from '../helper/helper';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  form: FormGroup;

  /* Display error */
  public error = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.form = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    this.error = false;
    const val = this.form.value;
    if (val.email && val.password && Helper.validateEmail(val.email)) {
      this.authService.login(val.email, val.password)
        .subscribe(user => {
            this.authService.setSession(user);
            this.router.navigateByUrl('/dashboard').then();
          },
          error => {
            this.error = true;
          }
        );
    }
  }
}
