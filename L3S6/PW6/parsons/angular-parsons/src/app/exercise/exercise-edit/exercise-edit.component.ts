import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ExerciseModel} from '../../models/exercise.model';
import {ApiService} from '../../services/api.service';

@Component({
  selector: 'app-exercise-edit',
  templateUrl: './exercise-edit.component.html',
  styleUrls: ['./exercise-edit.component.scss']
})
export class ExerciseEditComponent implements OnInit {

  public displayStats = false;
  public exercise = new ExerciseModel();
  public action: 'EDIT' | 'NEW';
  private idCourse: number;

  constructor(private route: ActivatedRoute, private router: Router, private api: ApiService) {
  }

  ngOnInit(): void {
    if (this.route.snapshot.routeConfig.path.includes('new')) {
      this.action = 'NEW';
      this.idCourse = Number(this.route.snapshot.paramMap.get('course'));
    } else {
      this.action = 'EDIT';
      const id = this.route.snapshot.paramMap.get('id');
      this.api.get('exercises', Number(id))
        .subscribe(exercise => {
          this.exercise.deserialize(exercise);
          this.idCourse = Number(this.exercise.course.id);
        });
    }
  }

  save(): void {
    if (this.exercise.title && this.exercise.description && this.exercise.parson) {
      if (this.action === 'EDIT') {
        this.api.update('exercises', this.exercise.id, {
          title: this.exercise.title,
          description: this.exercise.description,
          parson: this.exercise.parson
        }).subscribe();
      } else {
        this.api.store('exercises', {
          title: this.exercise.title,
          description: this.exercise.description,
          parson: this.exercise.parson,
          course: 'api/courses/' + this.idCourse
        }).subscribe();
      }
      this.redirect();
    }
  }

  stats(): void {
    this.displayStats = !this.displayStats;
  }

  delete(): void {
    this.api.delete('exercises', this.exercise.id).subscribe();
    this.redirect();
  }

  redirect(): void {
    this.router.navigate(['/courses/', this.idCourse, 'edit']).then();
  }
}
