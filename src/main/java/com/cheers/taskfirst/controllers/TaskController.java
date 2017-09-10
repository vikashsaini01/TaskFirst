package com.cheers.taskfirst.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.Task;
import com.cheers.taskfirst.service.TaskService;

@Controller
public class TaskController {

	@Autowired
	TaskService taskService;

	@RequestMapping(value = "/tasks", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getTasksForUser(ModelAndView modelAndView, @ModelAttribute LoginUser loginUser) {
		// Get the list of tasks for the logged in user and return it
		List<Task> listTask = loginUser.getTasks();
		modelAndView.addObject("listTask", listTask);
		
		// Add loginUser again as part of binding to get it back
		// Has to be removed in future and some other tricky way to be used.
		modelAndView.addObject("loginUser", loginUser);
		
		modelAndView.setViewName("tasks");
		return modelAndView;
	}

}
