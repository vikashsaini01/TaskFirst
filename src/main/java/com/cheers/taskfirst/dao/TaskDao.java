package com.cheers.taskfirst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cheers.taskfirst.model.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Long>{

}
