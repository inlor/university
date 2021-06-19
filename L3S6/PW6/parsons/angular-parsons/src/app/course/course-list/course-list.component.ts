import {Component, Input, OnInit} from '@angular/core';
import {Role} from '../../models/role';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {
  @Input() public courses = [];
  public role: string;
  public userId: number;
  constructor(private auth: AuthService) {
  }

  ngOnInit() {
    this.role = this.auth.getRole();
    this.userId = this.auth.getUser().id;
  }

  public roles() {
    return Role;
  }
}
