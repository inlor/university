import {Model} from './model';
import {UserModel} from './user.model';
import {CourseModel} from './course.model';

export class SubscriptionModel extends Model {
  user: UserModel;
  course: CourseModel;
  active: boolean;
}
