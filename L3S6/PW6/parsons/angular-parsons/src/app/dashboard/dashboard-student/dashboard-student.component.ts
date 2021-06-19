import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {AuthService} from '../../services/auth.service';
import {SubscriptionModel} from '../../models/subscription.model';
import {AttemptModel} from '../../models/attempt.model';

@Component({
    selector: 'app-dashboard-student',
    templateUrl: './dashboard-student.component.html',
    styleUrls: ['./dashboard-student.component.scss']
})
export class DashboardStudentComponent implements OnInit {

    public courses = [];
    public exercises = {done: [], inProgress: []};

    constructor(private api: ApiService, private auth: AuthService) {
    }

    ngOnInit(): void {
        this.initCourses();
        this.initExercises();
    }

    private initCourses(): void {
        this.api.getAll('subscriptions?user=' + this.auth.getUser().id)
            .subscribe(sub => {
              sub.forEach(item => {
                    const temp = new SubscriptionModel().deserialize(item);
                    if (temp.active) {
                        this.courses.push(temp.course);
                    }
                });
            });
    }

    private initExercises(): void {
        this.api.getAll('attempts?user=' + this.auth.getUser().id)
            .subscribe(attempts => {
              attempts.forEach(item => {
                    const temp = new AttemptModel().deserialize(item);
                    if (temp.solved) {
                        this.exercises.done.push(temp.exercise);
                    } else {
                        this.exercises.inProgress.push(temp.exercise);
                    }
                });
            });
    }
}
