import {Model} from './model';
import {ExerciseModel} from './exercise.model';
import {UserModel} from './user.model';

export class AttemptModel extends Model {
  exercise: ExerciseModel;
  user: UserModel;
  attempting: number;
  solved: boolean;
}
