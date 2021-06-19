import {Model} from './model';
import {CourseModel} from './course.model';
import {AttemptModel} from './attempt.model';

export class UserModel extends Model {
  username: string;
  email: string;
  roles: string[];
  password: string;
  courses: CourseModel[];
  attempts: AttemptModel[];
}
