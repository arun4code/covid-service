package com.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class CovidReportUploadResponse {
	@JsonIgnore
	private int createdCount;
	
	@JsonIgnore
    private int updatedCount;
    
	@JsonInclude
    private String message;
    
	public String setMessage() {
		return "Total new created records : " + createdCount + " and updated records : " + updatedCount;
	}

	public int getCreatedCount() {
		return createdCount;
	}

	public void setCreatedCount(int createdCount) {
		this.createdCount = createdCount;
	}

	public int getUpdatedCount() {
		return updatedCount;
	}

	public void setUpdatedCount(int updatedCount) {
		this.updatedCount = updatedCount;
	}
}
