import { Component, OnInit } from '@angular/core';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-schedule-list',
  templateUrl: './schedule-list.component.html',
  styleUrls: ['./schedule-list.component.css']
})
export class ScheduleListComponent implements OnInit {
   
  reloadFlag!:Boolean;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  imageName: any;
  message:any;
   schedules!:Schedule[];

   constructor(private scheduleService:ScheduleService,private httpClient:HttpClient){}

  ngOnInit(): void {
  this.getSchedules();
}
 private getSchedules(){  
  this.scheduleService.getScheduleList().subscribe(
    data => {
      this.schedules = data;
    })
  }

  delete(id: number): void {
    this.scheduleService.deleteSchedule(id).subscribe(
      (data: any) => {
        console.log(data);
        if (data.message === 'Delete successful') {
         this.message = data.message;
         window.location.reload();
        } else {
          // Handle unexpected response or error
          console.error('Error deleting schedule', data.error);
        }
      },
      (error) => {
        console.error('Error deleting schedule', error);
        // Handle errors or display error messages to the user
      }
    );
  }
  
    //Gets called when the user clicks on retieve image button to get the image from back end
    getImage() {
      this.httpClient.get('http://localhost:8080/api/v1/get/' + this.imageName)
        .subscribe(
          res => {
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
            console.log(this.retrievedImage);
            this.reloadFlag=true;
          },
          (error) => {
            console.error('Error:', error);
            if(error.status === 404){
              this.message = "Not found"
              setTimeout(()=>{this.message=""},1000)
            }
          }
        );
    }

    reload(){
     this.reloadFlag?this.retrievedImage="": this.reloadFlag = false;
     this.message = "";
     this.imageName="";
    }


}
function next(value: Object): void {
  throw new Error('Function not implemented.');
}

