import {UserModel} from './user.model';
import {ExerciseModel} from './exercise.model';
import {Model} from './model';
import {SubscriptionModel} from './subscription.model';

export class CourseModel extends Model {
  title: string;
  author: UserModel;
  content: string;
  exercises: ExerciseModel[];
  subscriptions: SubscriptionModel[];
}
