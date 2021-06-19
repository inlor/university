import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {AuthGuard} from './auth.guard';
import {Role} from '../models/role';
import {ApiService} from '../services/api.service';
import {CourseModel} from '../models/course.model';
import {ExerciseModel} from '../models/exercise.model';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardTeacher implements CanActivate {

  private can = false;
  private readonly userId: number;
  constructor(private authGuard: AuthGuard, private auth: AuthService, private api: ApiService) {
    this.userId = this.auth.getUser().id;
  }

  async canActivate(route: ActivatedRouteSnapshot) {
    const id = Number(route.paramMap.get('id'));
    if (route.routeConfig.path.includes('courses')) {
      if (route.routeConfig.path.includes('edit')) {
        await this.ownerCourse(id);
      } else if (route.routeConfig.path.includes('new')) {
        this.can = true;
      }
    } else if (route.routeConfig.path.includes('exercises')) {
      if (route.routeConfig.path.includes('edit')) {
        await this.ownerExercise(id);
      } else if (route.routeConfig.path.includes('new')) {
        await this.ownerCourse(Number(route.paramMap.get('course')));
      }
    }
    return (this.auth.getRole() === Role.TEACHER) && this.can;
  }

  private async ownerCourse(id: number): Promise<void> {
    const sub = this.api.get('courses', id);
    try {
      await sub.toPromise().then(temp => {
        const course = new CourseModel().deserialize(temp);
        this.can = course.author.id === this.userId;
      });
    } catch (e) {
    }
  }

  private async ownerExercise(id: number): Promise<void> {
    const sub = this.api.get('exercises', id);
    try {
      await sub.toPromise().then(temp => {
        const exercise = new ExerciseModel().deserialize(temp);
        this.can = exercise.course.author.id === this.userId;
      });
    } catch (e) {
    }
  }
}
