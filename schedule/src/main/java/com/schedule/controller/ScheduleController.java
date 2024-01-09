package com.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.model.Schedule;
import com.schedule.repository.ScheduleRepository;


@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	//getAll users
	@GetMapping("/schedules")
	public List<Schedule> getAllUsers()
	{
		return scheduleRepository.findAll();	
	}
    
	//create user restApi
    @PostMapping("/schedules")
	public Schedule createSchedule(@RequestBody Schedule schedule)
    {
		return scheduleRepository.save(schedule);
		
	}
	
	
}
