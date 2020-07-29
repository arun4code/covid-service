package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.model.CovidReport;

/**
 * @author arunb
 *
 */
public class CovidReportResponse {

	@JsonProperty("Id")
	private Long id;

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

	public static CovidReportResponse of(CovidReport report) {
		CovidReportResponse resp = new CovidReportResponse();
		resp.setActive(report.getActive());
		resp.setConfirmed(report.getConfirmed());
		resp.setDead(report.getDead());
		resp.setId(report.getId());
		resp.setLocation(report.getLocation());
		resp.setRecovered(report.getRecovered());
		resp.setTested(report.getTested());
		return resp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "CovidReportResponse [id=" + id + ", location=" + location + ", tested=" + tested + ", confirmed="
				+ confirmed + ", active=" + active + ", recovered=" + recovered + ", dead=" + dead + "]";
	}

}
