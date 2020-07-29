package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("updateRequest")
public class CovidReportUpdateRequest {

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

}
