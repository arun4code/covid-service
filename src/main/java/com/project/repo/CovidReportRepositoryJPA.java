package com.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.CovidReport;

@Repository
public interface CovidReportRepositoryJPA extends JpaRepository<CovidReport, Long> {

}
