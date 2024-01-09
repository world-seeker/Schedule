import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Schedule } from './schedule';
import { Observable } from 'rxjs/internal/Observable';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private baseURL="http://localhost:8080/api/v1/schedules";
  
  constructor(private httpClient:HttpClient) { }

   getScheduleList():Observable<Schedule[]>{
    return this.httpClient.get<Schedule[]>(`${this.baseURL}`);
   }

   createSchedule(schedule:Schedule,):Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`,schedule);
   }

   deleteSchedule(id:number){
    return this.httpClient.delete(`${this.baseURL}`+`/${id}`)
   }


 

   

}
