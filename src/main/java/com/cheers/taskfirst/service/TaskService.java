package com.cheers.taskfirst.service;

import java.util.List;

import com.cheers.taskfirst.model.Task;

public interface TaskService {

	public List<Task> findByCreatedBy(String createdBy);
	
	public Task findById(Long id);
	
	public List<Task> findBySubject(String subject);
	
	public List<Task> findByAllTextFields(String searchText);
	
	public Task addTask(Task task);
	
	public Task updateTask(Task task);
	
	public void deleteTask(Task task);
}
