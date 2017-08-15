package com.cheers.taskfirst.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	protected LocalDateTime createdOn;
	
	
	protected LocalDateTime updatadOn;
	
	
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


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public LocalDateTime getUpdatadOn() {
		return updatadOn;
	}


	public void setUpdatadOn(LocalDateTime updatadOn) {
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

	
	
	
	
}
