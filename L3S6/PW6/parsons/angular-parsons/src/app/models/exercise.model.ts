import {CourseModel} from './course.model';
import {Model} from './model';
import {AttemptModel} from './attempt.model';

export class ExerciseModel extends Model {
  title: string;
  description: string;
  course: CourseModel;
  parson: string;
  attempts: AttemptModel[];
}
