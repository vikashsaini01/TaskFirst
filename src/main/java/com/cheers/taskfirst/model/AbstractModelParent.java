package com.cheers.taskfirst.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

/**
 * Mapped super class that defines common fields for entities
 * @author hp1
 *
 */
@MappedSuperclass
public abstract class AbstractModelParent implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	

	@Id
	@GeneratedValue
	protected Long id;
	
	
	protected Date createdOn;
	

	
	
	protected Date updatadOn;
	
	
	protected String createdBy;
	
	
	protected String updatedBy;
	
	
	@Version
	protected Integer version; 


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public Date getUpdatadOn() {
		return updatadOn;
	}


	public void setUpdatadOn(Date updatadOn) {
		this.updatadOn = updatadOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	

	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	@PrePersist
	protected void beforePersist(){
		setCreatedOn(Calendar.getInstance().getTime());
	}
	
	@PreUpdate
	protected void beforeUpdate(){
		setUpdatadOn(Calendar.getInstance().getTime());
	}
	
	
}
