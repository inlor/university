import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {ApiService} from '../../services/api.service';
import {CourseModel} from '../../models/course.model';

@Component({
  selector: 'app-dashboard-teacher',
  templateUrl: './dashboard-teacher.component.html',
  styleUrls: ['./dashboard-teacher.component.scss']
})
export class DashboardTeacherComponent implements OnInit {

  public courses = [];

  constructor(
    private authService: AuthService,
    private api: ApiService) {
  }

  ngOnInit() {
    const id = this.authService.getUser().id;
    this.api.getAll('courses?author=' + id)
      .subscribe(courses => {
        courses.forEach(item => {
          this.courses.push(new CourseModel().deserialize(item));
        });
      });
  }
}
