package com.cheers.taskfirst.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
/**
 * Main class to hold task details 
 * @author hp1
 *
 */
@Entity
public class Task extends AbstractModelParent implements Serializable{
	
	private static final long serialVersionUID = 1L;

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
	private String subject;
	
	private String details;
	
	@NotNull
	private TaskStatus taskStatus;
	
	private Date targetDate;
	
	private Date actualEndDate;
	
	@NotNull
	private boolean showReminder;  /*boolean not Boolean .. as either it is yes or no (never null) */

	public boolean isShowReminder() {
		return showReminder;
	}

	public void setShowReminder(boolean showReminder) {
		this.showReminder = showReminder;
	}

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

	
	/** To be added later, feature either to have parent task and a list of child tasks
	 * or a parentTaskList which is another class holding child tasks and/or other task lists 
	private parentTask;
	*/
	
	
}
