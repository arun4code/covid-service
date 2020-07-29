package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="COVID_REPORT")
public class CovidReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name="LOCATION", unique = true)
	private String location;

	@Column(name="TESTED")
	private Integer tested;

	@Column(name="CONFIRMED")
	private Integer confirmed;

	@Column(name="ACTIVE")
	private Integer active;

	@Column(name="RECOVERED")
	private Integer recovered;

	@Column(name="DEAD")
	private Integer dead;

	public static CovidReport of(String location, Integer active, Integer confirmed, Integer dead,
			Integer recovered, Integer tested) {
		CovidReport request = new CovidReport();
		request.setLocation(location);
		request.setActive(active);
		request.setConfirmed(confirmed);
		request.setDead(dead);
		request.setRecovered(recovered);
		request.setTested(tested);
		return request;
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
		return "CovidReport [id=" + id + ", location=" + location + ", tested=" + tested + ", confirmed=" + confirmed
				+ ", active=" + active + ", recovered=" + recovered + ", dead=" + dead + "]";
	}

}
