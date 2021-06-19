import {Component, OnInit} from '@angular/core';
import {CourseModel} from '../models/course.model';
import {ApiService} from '../services/api.service';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.scss']
})
export class ExploreComponent implements OnInit {

  public courses = [];

  constructor(private api: ApiService) {
  }

  ngOnInit(): void {
    this.api.getAll('courses')
      .subscribe(courses => {
        courses.forEach(item => {
          this.courses.push(new CourseModel().deserialize(item));
        });
      });
  }

}
