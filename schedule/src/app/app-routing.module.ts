import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ScheduleListComponent } from './schedule-list/schedule-list.component';
import { HomeComponent } from './home/home.component';
import { BookingComponent } from './booking/booking.component';

const routes: Routes = [
  {path:"schedule-list",component:ScheduleListComponent},
  {path:"booking", component:BookingComponent},
  {path:"home", component:HomeComponent},
  {path:"",redirectTo:"home",pathMatch:"full"},
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
