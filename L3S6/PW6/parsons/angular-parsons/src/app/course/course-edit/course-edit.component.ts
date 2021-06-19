import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CourseModel} from '../../models/course.model';
import {AuthService} from '../../services/auth.service';
import {ApiService} from '../../services/api.service';

@Component({
  selector: 'app-course-edit',
  templateUrl: './course-edit.component.html',
  styleUrls: ['./course-edit.component.scss']
})
export class CourseEditComponent implements OnInit {

  public course = new CourseModel();
  public action: 'EDIT' | 'NEW';

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private api: ApiService) {
  }

  ngOnInit(): void {
    if (this.route.snapshot.routeConfig.path.includes('new')) {
      this.action = 'NEW';
    } else {
      this.action = 'EDIT';
      const id = this.route.snapshot.paramMap.get('id');
      this.api.get('courses', Number(id))
        .subscribe(course => this.course = new CourseModel().deserialize(course));
    }
  }

  save(): void {
    if (this.course.title && this.course.content) {
      if (this.action === 'EDIT') {
        this.api.update('courses', this.course.id, {
          title: this.course.title,
          content: this.course.content
        }).subscribe();
      } else {
        this.api.store('courses', {
          title: this.course.title,
          content: this.course.content,
          author: 'api/users/' + this.authService.getUser().id
        }).subscribe();
      }
      this.redirect();
    }
  }

  delete(): void {
    this.api.delete('courses', this.course.id).subscribe();
    this.redirect();
  }

  redirect() {
    this.router.navigateByUrl('/dashboard').then();
  }
}
