import {Component} from '@angular/core';
import {AuthService} from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Parsons';

  constructor(public authService: AuthService) {
    // TODO stats for teacher per user and exercise
    // TODO block register and login when user is log
  }
}
