package com.project.dataloader;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.project.repo.CovidReportRepositoryJPA;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final CovidReportRepositoryJPA repository;

	public DataLoader(CovidReportRepositoryJPA repository) {
		this.repository = repository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*
		 * CovidReport report = CovidReport.of("Shanti", "Ramar", "24", "Marketing");
		 * repository.save(user1);
		 * 
		 * user1 = User.of("Andie", "Flower", "34", "Marketing");
		 * 
		 * repository.save(user1);
		 * 
		 * user1 = User.of("Manu", "Ronnie", "45", "IT"); repository.save(user1);
		 */
	}

}
