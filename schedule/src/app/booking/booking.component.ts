import { Component, OnInit, Renderer2 } from '@angular/core';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  selectedFile!: File;
  message: string | any;
  errorMessage!: string;
  schedule: Schedule = new Schedule();
  
  booking!:string;

  labTests = [
    { id: 1, name: 'Blood Test' },
    { id: 2, name: 'Urinalysis' },
    { id: 3, name: 'X-ray' },
    { id: 4, name: 'MRI Scan' },
    { id: 5, name: 'CT Scan' },
    // Add more tests as needed
  ];

  constructor(
    private scheduleService: ScheduleService,
    private router: Router,
    private httpClient: HttpClient,
    private render:Renderer2
  ) {}

  ngOnInit(): void {}

  saveSchedule() {
    this.schedule.prescriptionImages = this.selectedFile.name;
    this.scheduleService.createSchedule(this.schedule).subscribe(data => {
      console.log(data);
    });
    this.booking = this.schedule.fullName+"'s"+" Booking Confirmed";
    setTimeout(()=>{this.goToHome();},4000)
  }

  goToHome() {
    this.router.navigate(['/home']);
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.onUpload();
    } else {
      alert('Please fill out all the required fields.');
    }
  }

  onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.httpClient
      .post('http://localhost:8080/api/v1/upload', uploadImageData, { observe: 'response', responseType: 'text' })
      .pipe(
        tap((response: HttpResponse<string>) => {
          if (response.status === 200) {
            this.message = response.body; // Extract the response text
            this.saveSchedule(); // Call saveSchedule only if onUpload is successful
          } else {
            this.message = 'Image not uploaded successfully';
          }
        }),
        catchError((error: HttpErrorResponse) => {
          if (error.status === 409) {
            console.error('File with the same name already exists');
            this.errorMessage = 'File with the same name already exists. Rename your prescribtion File and try again';
            setTimeout(()=>{this.errorMessage=""},4000)
          } else {
            console.error('An unexpected error occurred', error);
            // Handle other error scenarios
          }
          return throwError(error);
        })
      )
      .subscribe();
  }

  isFormValid(): boolean | string {
    return (
      this.schedule.fullName &&
      this.schedule.description &&
      this.schedule.appointmentDate &&
      this.schedule.appointmentTime &&
      this.schedule.testName !== 'null'
    );
  }
}
