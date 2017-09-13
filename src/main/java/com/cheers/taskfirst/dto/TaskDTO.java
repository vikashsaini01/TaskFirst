package com.cheers.taskfirst.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Main class to hold task details 
 * @author hp1
 *
 */

public class TaskDTO {
	


	enum TaskStatus{
		PENDING("PENDING"), STARTED("STARTED"), INPROGRESS("INPROGRESS"), COMPLETED("COMPLETED"), REJECTED("REJECTED") ; 
		private String taskStatus;
		TaskStatus(String str){
			taskStatus=str;
		}
		
		String getTaskStatus(){
			return taskStatus;
		}
	};
	
	@NotNull
	@Size(min=4, max=140)
	private String subject;
	
	@Size(max=400)
	private String details;
	
	@NotNull
	private TaskStatus taskStatus;
	
	private Date targetDate;
	
	private Date actualEndDate;
	
	@NotNull
	private boolean showReminder;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus status) {
		this.taskStatus= status;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public boolean isShowReminder() {
		return showReminder;
	}

	public void setShowReminder(boolean showReminder) {
		this.showReminder = showReminder;
	}
	
	/** To be added later, feature either to have parent task and a list of child tasks
	 * or a parentTaskList which is another class holding child tasks and/or other task lists 
	private parentTask;
	*/
	
	
}
