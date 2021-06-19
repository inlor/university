import {Component, OnInit, Input} from '@angular/core';
import {ChartOptions, ChartType, ChartDataSets} from 'chart.js';
import {ExerciseModel} from '../../models/exercise.model';


@Component({
  selector: 'app-exercise-stats',
  templateUrl: './exercise-stats.component.html',
  styleUrls: ['./exercise-stats.component.scss']
})
export class ExerciseStatsComponent implements OnInit {

  @Input() exercise: ExerciseModel;

  public barChartOptions: ChartOptions = {
    responsive: true,
    scales: {xAxes: [{}], yAxes: [{}]},
  };
  public barChartLabels = [];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [];

  ngOnInit(): void {
    this.barChartLabels.push(this.exercise.title);
    this.exercise.attempts.forEach(attempt => {
      const solved = attempt.solved ? 'Solved' : 'Not Solved';
      this.barChartData.push({data: [attempt.attempting], label: attempt.user.username + '-' + solved});
    });
  }
}
