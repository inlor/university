import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CourseModel} from '../models/course.model';
import {ApiService} from '../services/api.service';
import {AuthService} from '../services/auth.service';
import {SubscriptionModel} from '../models/subscription.model';
import {UserModel} from '../models/user.model';
import {AttemptModel} from '../models/attempt.model';
import {Role} from '../models/role';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements OnInit {

  public course = new CourseModel();
  public alreadySubscribed = false;
  public display = true;
  public role = '';
  public attempts = {};
  private subscription = new SubscriptionModel();

  constructor(private route: ActivatedRoute, private api: ApiService, private auth: AuthService) {
    this.course.author = new UserModel();
  }

  ngOnInit(): void {
    this.initCourse();
    this.initAttempts();
    this.role = this.auth.getRole();
  }

  subscribe(): void {
    this.display = false;
    if (this.subscription.id !== undefined) {
      this.updateSubscription(true);
    } else {
      this.api.store('subscriptions', this.getJson(true))
        .subscribe();
    }
  }

  unsubscribe(): void {
    this.display = false;
    this.updateSubscription(false);
  }

  roles() {
    return Role;
  }

  private initCourse(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.api.get('courses', Number(id))
      .subscribe(course => {
        this.course = course;
        this.initSubscription();
      });
  }

  private initSubscription(): void {
    this.api.getAll('subscriptions?user=' + this.auth.getUser().id + '&course=' + this.course.id)
      .subscribe(sub => {
        if (sub.length === 1) {
          const subscription = new SubscriptionModel().deserialize(sub[0]);
          if (subscription.course.id === this.course.id) {
            this.subscription = subscription;
            if (subscription.active === true) {
              this.alreadySubscribed = true;
            }
          }
        }
      });
  }

  private initAttempts(): void {
    this.api.getAll('attempts?course=' + this.course.id + '?user' + this.auth.getUser().id)
      .subscribe(attempts => {
        attempts.forEach(item => {
          const temp = new AttemptModel().deserialize(item);
          this.attempts[temp.id] = temp;
        });
      });
  }

  private updateSubscription(state: boolean): void {
    this.api.update('subscriptions', this.subscription.id, this.getJson(state))
      .subscribe();
  }

  private getJson(state: boolean): object {
    return {
      user: 'api/users/' + this.auth.getUser().id,
      course: 'api/courses/' + this.course.id,
      active: state
    };
  }
}
