package com.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="image_table")
public class ImageModel {
	 
	/* this entity class contains a total of 4 fields
	 * in those the id field is auto generated 
	 * */
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

    @OneToOne
    private Schedule schedule;
	
	@Column(name="name",unique=true)
	private String name;
	
	@Column(name="type")
	private String type;
	
	@Column(name="picByte", length=200000)
	private byte[] picByte;
	
	

	public ImageModel() {
		super();
	}

	public ImageModel(String name, String type, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	
	  public Schedule getSchedule() {
	        return schedule;
	    }

	    public void setSchedule(Schedule schedule) {
	        this.schedule = schedule;
	    }
	
	
	
	

}
