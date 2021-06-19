import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {FlexLayoutModule} from '@angular/flex-layout';
import {
  MatSliderModule,
  MatFormFieldModule,
  MatAutocompleteModule,
  MatCheckboxModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatInputModule,
  MatButtonModule,
  MatRadioModule,
  MatDatepickerModule,
  MatIconModule,
  MatSidenavModule,
  MatListModule,
  MatToolbarModule,
  MatCardModule,
  MatPaginatorModule,
  MatTableModule,
  MatExpansionModule,
  MatDialogModule,
  MatMenuModule,
} from '@angular/material';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {CourseComponent} from './course/course.component';
import {ExerciseComponent} from './exercise/exercise.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CourseEditComponent} from './course/course-edit/course-edit.component';
import {ExerciseEditComponent} from './exercise/exercise-edit/exercise-edit.component';
import {AuthService} from './services/auth.service';
import {AuthGuard} from './guard/auth.guard';
import {AuthInterceptor} from './services/auth.interseptor.service';
import {DashboardStudentComponent} from './dashboard/dashboard-student/dashboard-student.component';
import {DashboardTeacherComponent} from './dashboard/dashboard-teacher/dashboard-teacher.component';
import {ExploreComponent} from './explore/explore.component';
import {CourseListComponent} from './course/course-list/course-list.component';
import {AuthGuardTeacher} from './guard/auth.guard.teacher';
import {ChartsModule} from 'ng2-charts';
import {ExerciseStatsComponent} from './exercise/exercise-stats/exercise-stats.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CourseComponent,
    ExerciseComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    CourseEditComponent,
    ExerciseEditComponent,
    DashboardStudentComponent,
    DashboardTeacherComponent,
    ExploreComponent,
    CourseListComponent,
    ExerciseStatsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NoopAnimationsModule,
    DragDropModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatIconModule,
    FlexLayoutModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatCardModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatExpansionModule,
    MatDialogModule,
    PortalModule,
    MatMenuModule,
    ChartsModule,
  ],
  providers: [
    AuthGuard,
    AuthGuardTeacher,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
