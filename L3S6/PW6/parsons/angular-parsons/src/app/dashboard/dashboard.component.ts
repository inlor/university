import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {Role} from '../models/role';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  public role;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.role = this.authService.getRole();
  }

  get roles() {
    return Role;
  }
}
