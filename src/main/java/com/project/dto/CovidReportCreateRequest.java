package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CovidReportCreateRequest {

	//@NotNull(message = "Location is mandatory")
	//@NotBlank(message = "Location is mandatory")
    @JsonProperty("Location")
	private String location;

	@JsonProperty("Tested")
	private Integer tested;

	@JsonProperty("Confirmed")
	private Integer confirmed;

	@JsonProperty("Active")
	private Integer active;

	@JsonProperty("Recovered")
	private Integer recovered;

	@JsonProperty("Dead")
	private Integer dead;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getTested() {
		return tested;
	}

	public void setTested(Integer tested) {
		this.tested = tested;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getRecovered() {
		return recovered;
	}

	public void setRecovered(Integer recovered) {
		this.recovered = recovered;
	}

	public Integer getDead() {
		return dead;
	}

	public void setDead(Integer dead) {
		this.dead = dead;
	}

	public static CovidReportCreateRequest of(String location, Integer active, Integer confirmed, Integer dead,
			Integer recovered, Integer tested) {
		
		CovidReportCreateRequest request = new CovidReportCreateRequest();
		request.setLocation(location);
		request.setActive(active);
		request.setConfirmed(confirmed);
		request.setDead(dead);
		request.setRecovered(recovered);
		request.setTested(tested);
		return request;
		
	}
}
