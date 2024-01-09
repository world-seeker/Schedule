package com.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schedule.model.Schedule;



@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long>{

}
