package com.schedule.model;

import java.util.Date; // Import the Date class

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "test_name")
    private String testName;

    @Column(name = "appointment_date")
    private Date appointmentDate; // Date of the appointment

    @Column(name = "appointment_time")
    private String appointmentTime; // Time of the appointment

    @Column(name = "prescription_images")
    private String prescriptionImages; // Store the image file path or data

    @Column(name = "description")
    private String description;
    
    
    
     public Schedule() {
    	 
     }

	public Schedule(String fullName, String testName, Date appointmentDate, String appointmentTime,
			String prescriptionImages, String description) {
		super();
		this.fullName = fullName;
		this.testName = testName;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.prescriptionImages = prescriptionImages;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getPrescriptionImages() {
		return prescriptionImages;
	}

	public void setPrescriptionImages(String prescriptionImages) {
		this.prescriptionImages = prescriptionImages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
    
    
   
 // Name of the test (added attribute)

    // Constructors, getters, setters, and other methods can be added as needed
}
