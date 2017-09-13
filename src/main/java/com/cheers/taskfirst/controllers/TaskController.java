package com.cheers.taskfirst.controllers;

import java.util.List;

import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cheers.taskfirst.dto.TaskDTO;
import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.Task;
import com.cheers.taskfirst.service.TaskService;
import com.cheers.taskfirst.utils.DozerHelper;

@Controller
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	DozerBeanMapper dozerBeanMapper;

	@RequestMapping(value = "/tasks", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getTasksForUser(ModelAndView modelAndView) {
		// Get the list of tasks for the logged in user and return it
		List<Task> listTask = taskService.findByCreatedBy(getLoginUser().getId());
		modelAndView.addObject("listTaskDTO", DozerHelper.mapList(dozerBeanMapper, listTask, TaskDTO.class));

		modelAndView.addObject("taskDTO", new TaskDTO());

		modelAndView.setViewName("tasks");
		return modelAndView;
	}
	
	private LoginUser getLoginUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object myUser = (auth != null) ? auth.getPrincipal() :  null;

		if (myUser instanceof LoginUser) {
		     LoginUser user = (LoginUser) myUser;
		     return user;
		}
		return null;
	}

	@RequestMapping(value="/addtask" , method=RequestMethod.POST)
	public ModelAndView addTask(ModelAndView modelAndView, @ModelAttribute @Valid TaskDTO taskDTO, BindingResult bindingResult) {
		Long loggedInUserID = getLoginUser().getId();
		modelAndView.setViewName("tasks");

		//If validation success, add createdBy to task and save it
		if (!bindingResult.hasErrors()) {
			Task task = dozerBeanMapper.map(taskDTO, Task.class);
			task.setCreatedBy(loggedInUserID);
			Task taskAdded = taskService.addTask(task);
			if (taskAdded == null) {
				bindingResult.reject("message.taskCouldNotBeAdded");
				modelAndView.addObject("taskDTO", taskDTO);   //Send same taskDTO as error
			}
			else{
				modelAndView.addObject("taskDTO", new TaskDTO()); // Send new taskDTO as succeed
			}
		} else {
			modelAndView.addObject("taskDTO", taskDTO); // send same taskDTO as validation errors
		}
		List<Task> listTask = taskService.findByCreatedBy(loggedInUserID);
		modelAndView.addObject("listTaskDTO", DozerHelper.mapList(dozerBeanMapper, listTask, TaskDTO.class));		
		return modelAndView;
	}

}
