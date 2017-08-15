package com.cheers.taskfirst.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
/**
 * Main class to hold task details 
 * @author hp1
 *
 */
@Entity
public class Task extends AbstractModelParent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	enum TaskStatus{
		PENDING, STARTED, INPROGRESS, COMPLETED, REJECTED
	};
	
	private String subject;
	
	private String details;
	
	private TaskStatus status;
	
	private LocalDateTime targetDate;
	
	private LocalDateTime actualEndDate;
	
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

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDateTime getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDateTime targetDate) {
		this.targetDate = targetDate;
	}

	public LocalDateTime getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(LocalDateTime actualEndDate) {
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
