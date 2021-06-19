import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {CourseComponent} from './course/course.component';
import {ExerciseComponent} from './exercise/exercise.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CourseEditComponent} from './course/course-edit/course-edit.component';
import {ExerciseEditComponent} from './exercise/exercise-edit/exercise-edit.component';
import {ExploreComponent} from './explore/explore.component';
import {AuthGuard} from './guard/auth.guard';
import {AuthGuardTeacher} from './guard/auth.guard.teacher';
import {GuestGuard} from './guard/guest.guard';

const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent, pathMatch: 'full', canActivate: [GuestGuard]},
  {path: 'register', component: RegisterComponent, pathMatch: 'full', canActivate: [GuestGuard]},
  {path: 'dashboard', component: DashboardComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'explore', component: ExploreComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'courses/new', component: CourseEditComponent, pathMatch: 'full', canActivate: [AuthGuard, AuthGuardTeacher]},
  {path: 'courses/:id/edit', component: CourseEditComponent, pathMatch: 'full', canActivate: [AuthGuard, AuthGuardTeacher]},
  {path: 'courses/:id', component: CourseComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'exercises/:course/new', component: ExerciseEditComponent, pathMatch: 'full', canActivate: [AuthGuard, AuthGuardTeacher]},
  {path: 'exercises/:id/edit', component: ExerciseEditComponent, pathMatch: 'full', canActivate: [AuthGuard, AuthGuardTeacher]},
  {path: 'exercises/:id', component: ExerciseComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
