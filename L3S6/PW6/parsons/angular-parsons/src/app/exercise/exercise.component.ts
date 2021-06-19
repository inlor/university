import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {FieldModel} from '../models/field.model';
import {ExerciseModel} from '../models/exercise.model';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {ApiService} from '../services/api.service';
import {AttemptModel} from '../models/attempt.model';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss']
})
export class ExerciseComponent implements OnInit {

  /* Exercise is started or not */
  public start = 0;
  /* Display description */
  public description = true;
  /* Display errors */
  public error = {display: false, indent: false, line: false, size: false};
  /* Is answer correct */
  public correct = false;
  /* Exercise model*/
  public exercise = new ExerciseModel();
  /* All fields */
  public build: FieldModel[];
  /* Student answer */
  public result: FieldModel[];
  /* Attempts model*/
  public attempts = new AttemptModel();
  /* Indent proprieties */
  public readonly MIN_INDENT = 0;
  public readonly MAX_INDENT = 16;
  private readonly INDENT_STEP = 2;
  /* Initial parson */
  private parsons: FieldModel[];
  /* Solution for the parson */
  private solution: FieldModel[];

  private readonly SEPARATOR = '\n';
  private readonly DISTRACTOR = '#distractor';


  constructor(
    private route: ActivatedRoute,
    private auth: AuthService,
    private api: ApiService) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.api.get('exercises', Number(id)).subscribe(exercise => {
      this.exercise = exercise;
      this.initAttempts();
    });
  }

  /**
   * Ini parsons arrays for all fields
   */
  initParson(): void {
    this.start = 1;
    this.build = [];
    this.result = [];
    this.parsons = [];
    const parson = this.exercise.parson;
    parson.split(this.SEPARATOR).forEach((item, index) => {
      this.build.push(new FieldModel(index + 1, 0, item.replace(this.DISTRACTOR, '')));
      this.parsons.push(new FieldModel(index + 1, 0, item));
    });
    this.solution = this.getSolution();
  }

  drop(event: CdkDragDrop<FieldModel[]>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  /**
   * Hide the description
   */
  hide() {
    this.description = !this.description;
  }

  indentLeft(item: FieldModel): void {
    if (item.indent !== this.MIN_INDENT) {
      item.indent -= this.INDENT_STEP;
    }
  }

  indentRight(item: FieldModel): void {
    if (item.indent < this.MAX_INDENT) {
      item.indent += this.INDENT_STEP;
    }
  }

  /**
   * Reset exercise
   */
  reset(): void {
    this.initParson();
    this.build.sort(() => Math.random() - 0.5);
    this.error.display = false;
    this.correct = false;
  }

  /**
   * Get to user some indications
   */
  indication(): void {
    this.answer();
    this.error.display = true;
    this.error.size = this.solution.length !== this.result.length;
    if (!this.error.size) {
      for (let i = 0; i < this.solution.length; i++) {
        this.error.line = this.solution[i].errorLine(this.result[i]);
        this.error.indent = this.solution[i].errorIndent(this.result[i]);
        if (this.error.indent || this.error.line) {
          break;
        }
      }
    }
  }

  /**
   * Call with http to update result or create them
   */
  feedback(): void {
    this.answer();
    const result = FieldModel.equalsArray(this.solution, this.result);
    if (result) {
      this.correct = true;
    } else {
      alert('try again!');
    }
    this.error.display = false;
    this.storeAttempts(result);
  }

  /**
   * Init attempt model
   */
  private initAttempts(): void {
    this.api.getAll('attempts?user=' + this.auth.getUser().id + '&exercise=' + this.exercise.id)
      .subscribe(attempts => {
        if (attempts.length === 1) {
          this.attempts = new AttemptModel().deserialize(attempts[0]);
        }
      });
  }

  /**
   * Update attempt
   */
  private storeAttempts(result: boolean): void {
    if (this.attempts.id !== undefined) {
      if (!this.attempts.solved) {
        this.api.update('attempts', this.attempts.id, this.getAttemptsJson(this.attempts.attempting + 1, result))
          .subscribe(attempts => {
            this.attempts = new AttemptModel().deserialize(attempts);
          });
      }
    } else {
      this.api.store('attempts', this.getAttemptsJson(1, result)).subscribe(attempts => {
        this.attempts = new AttemptModel().deserialize(attempts);
      });
    }
  }

  /**
   *  Get attempt json
   */
  private getAttemptsJson(attempts: number, result: boolean): object {
    return {
      exercise: 'api/exercises/' + this.exercise.id,
      user: 'api/users/' + this.auth.getUser().id,
      attempting: attempts,
      solved: result
    };
  }

  /**
   * Init answer with the right line
   */
  private answer(): void {
    this.result.forEach((item, index) => {
      item.line = index + 1;
    });
  }

  /**
   * Get the solution for tha parson
   */
  private getSolution(): FieldModel[] {
    const solution = [];
    this.parsons.forEach((item, index) => {
      if (!item.content.includes(this.DISTRACTOR)) {
        item.line = index + 1;
        item.indent = this.countIndent(item.content);
        solution.push(item);
      }
    });
    return solution;
  }

  /**
   *  Count indent
   */
  private countIndent = (content: string): number => {
    let i = 0;
    while (content.charAt(i) === ' ') {
      i++;
    }
    return i;
  }
}
