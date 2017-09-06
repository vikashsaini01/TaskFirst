package com.cheers.taskfirst.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.dao.TaskDao;
import com.cheers.taskfirst.model.Task;

@Service
public class TaskServiceBean implements TaskService {

	@Autowired
	TaskDao taskDao;

	@Override
	public List<Task> findByCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task findById(Long id) {
		Task taskFound = null;
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid value for Task ID, ID must be positive number");
		}
		taskFound = taskDao.findOne(id);
		return taskFound;
	}

	@Override
	public List<Task> findBySubject(String subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task addTask(Task task) {
		if (task == null || task.getSubject() == null || task.getSubject().isEmpty()) {
			throw new IllegalArgumentException("Task subject must be provided");
		}
		Task taskCreated = taskDao.save(task);
		return taskCreated;
	}

	@Override
	public Task updateTask(Task task) {
		Task taskFound = findById(task.getId());
		if (taskFound == null) {
			throw new EntityNotFoundException("Task not found with the Id provided.");
		}
		Task taskUpdated = taskDao.save(task);
		return taskUpdated;
	}

	@Override
	public void deleteTask(Task task) {
		Task taskFound = findById(task.getId());
		if (taskFound == null) {
			throw new EntityNotFoundException("Task not found with the Id provided.");
		}
		taskDao.delete(taskFound);
	}

	@Override
	public List<Task> findByAllTextFields(String searchText) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
