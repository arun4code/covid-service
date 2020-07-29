package com.project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.CovidReportCreateRequest;
import com.project.dto.CovidReportResponse;
import com.project.dto.CovidReportUpdateRequest;
import com.project.dto.CovidReportUploadResponse;
import com.project.exception.CovidReportException;
import com.project.model.CovidReport;
import com.project.repo.CovidReportRepositoryJPA;

@Service
public class CovidReportServiceImpl implements CovidReportService {
	private final Logger logger = LoggerFactory.getLogger(CovidReportServiceImpl.class);

	private final CovidReportRepositoryJPA repository;

	@PersistenceContext
	private EntityManager entityManager;

	public CovidReportServiceImpl(CovidReportRepositoryJPA repository) {
		this.repository = repository;
	}

	@Override
	public List<CovidReportResponse> findAllData() {
		logger.info("Service: fetch all report details");
		List<CovidReport> userDetails = repository.findAll();
		if (userDetails == null || userDetails.isEmpty()) {
			throw new CovidReportException("No data found");
		}
		return userDetails.stream().map(CovidReportResponse::of).collect(Collectors.toList());
	}

	@Override
	public CovidReportResponse updateReport(CovidReportUpdateRequest request, String location) {
		logger.info("Service: Update report for location : " + location);
		CovidReport report = findByLocation(location);

		if (report != null) {
			if (request.getActive() != null) {
				report.setActive(request.getActive());
			}
			if (request.getConfirmed() != null) {
				report.setConfirmed(request.getConfirmed());
			}
			if (request.getDead() != null) {
				report.setDead(request.getDead());
			}
			if (request.getRecovered() != null) {
				report.setRecovered(request.getRecovered());
			}
			if (request.getTested() != null) {
				report.setTested(request.getTested());
			}
			return CovidReportResponse.of(repository.save(report));
		} else {
			throw new CovidReportException("No Data found");
		}
	}

	@Override
	public List<CovidReportResponse> findByFilter(CovidReportCreateRequest request) {
		logger.info("Service: fetch all report data based on provided filter");
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<CovidReport> query = criteria.createQuery(CovidReport.class);
		Root<CovidReport> root = query.from(CovidReport.class);
		List<Predicate> predicate = new ArrayList<>();

		if (request.getLocation() != null && !request.getLocation().isEmpty()) {
			predicate.add(criteria.equal(root.get("location"), request.getLocation()));
		}
//		if (request.getActive() != null) {
//			predicate.add(criteria.equal(root.get("active"), request.getActive()));
//		}
//		
//		if (request.getConfirmed() != null) {
//			predicate.add(criteria.equal(root.get("confirmed"), request.getConfirmed()));
//		}
//		if (request.getDead() != null) {
//			predicate.add(criteria.equal(root.get("dead"), request.getDead()));
//		}

		query.where(predicate.toArray(new Predicate[predicate.size()]));

		query.select(root);
		TypedQuery<CovidReport> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList().stream().map(CovidReportResponse::of).collect(Collectors.toList());
	}

	private CovidReport findByLocation(String location) {
		logger.info("Service: fetch all report data based on provided filter");
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<CovidReport> query = criteria.createQuery(CovidReport.class);
		Root<CovidReport> root = query.from(CovidReport.class);
		List<Predicate> predicate = new ArrayList<>();

		if (location != null && !location.isEmpty()) {
			predicate.add(criteria.equal(root.get("location"), location));
		}

		query.where(predicate.toArray(new Predicate[predicate.size()]));

		query.select(root);
		TypedQuery<CovidReport> typedQuery = entityManager.createQuery(query);
		return typedQuery.getSingleResult();
	}

	public List<CovidReport> findByMultiLocation(List<String> location) {
		logger.info("Service: fetch all report data based on provided filter");
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<CovidReport> query = criteria.createQuery(CovidReport.class);
		Root<CovidReport> root = query.from(CovidReport.class);
		List<Predicate> predicate = new ArrayList<>();

		/*
		 * List<String> locationList = new ArrayList<>(); if(location.contains(",")) {
		 * String[] columns = location.split(","); locationList =
		 * Arrays.asList(columns); }
		 */
		if (location != null && !location.isEmpty()) {
			// predicate.add(criteria.equal(root.get("location"), location));

			predicate.add(root.get("location").in(location));
		}

		query.where(predicate.toArray(new Predicate[predicate.size()]));

		query.select(root);
		TypedQuery<CovidReport> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}

	@Override
	public CovidReportResponse findReportById(Long id) {
		logger.info("Service: fetch all report details by id : " + id);
		Optional<CovidReport> report = repository.findById(id);

		if (report == null || !report.isPresent() || report.get() == null) {
			throw new CovidReportException("No Data found for id = " + id);
		}
		return CovidReportResponse.of(report.get());
	}

	@Override
	public CovidReportUploadResponse upload(MultipartFile readDataFile) {
		CovidReportUploadResponse resp = new CovidReportUploadResponse();
		if (readDataFile != null && readDataFile.getOriginalFilename().contains("csv")) {
			List<CovidReport> cList = processCSVFile(readDataFile);
			if (cList != null && cList.size() > 0) {
				List<String> locationList = new ArrayList<>();
				cList.stream().forEach(item -> {
					locationList.add(item.getLocation());
				});

				List<CovidReport> updateReportList = new ArrayList<>();

				List<CovidReport> reportList = findByMultiLocation(locationList);
				Map<String, CovidReport> reportMap = reportList.stream()
						.collect(Collectors.toMap(x -> x.getLocation(), x -> x));

				cList.stream().forEach(item -> {
					if (reportMap.containsKey(item.getLocation())) { // update existing
						CovidReport orig = reportMap.get(item.getLocation());
						updateObject(item, orig);
						updateReportList.add(orig);
						resp.setUpdatedCount(resp.getUpdatedCount() + 1);
					} else { // create new
						CovidReport newRecord = createObject(item);
						updateReportList.add(newRecord);
						resp.setCreatedCount(resp.getCreatedCount() + 1);
					}
				});

				repository.saveAll(updateReportList);
			}
		} else {
			throw new CovidReportException("File format not supported. Please use specified CSV format");
		}
		return resp;
	}

	private CovidReport createObject(CovidReport item) {
		return CovidReport.of(item.getLocation(), item.getActive(), item.getConfirmed(), item.getDead(),
				item.getRecovered(), item.getTested());
	}

	private void updateObject(CovidReport item, CovidReport orig) {
		if (item.getActive() != null) {
			orig.setActive(item.getActive());
		}
		if (item.getConfirmed() != null) {
			orig.setConfirmed(item.getConfirmed());
		}
		if (item.getDead() != null) {
			orig.setDead(item.getDead());
		}
		if (item.getRecovered() != null) {
			orig.setRecovered(item.getRecovered());
		}
		if (item.getTested() != null) {
			orig.setTested(item.getTested());
		}
	}

	private List<CovidReport> processCSVFile(MultipartFile readDataFile) {

		String line;
		InputStream is;
		BufferedReader br = null;
		try {
			is = readDataFile.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<CovidReport> cList = new ArrayList<>();
		try {
			int i = 0;
			while ((line = br.readLine()) != null) {
				if(i == 0) {
					i++;
					continue;
				}
				if (line != null) {
					String[] columns = line.split(",");
					if (columns[0] == null || columns[0].isEmpty()) {
						throw new CovidReportException("Location missing in csv file");
					}
					CovidReport report = CovidReport.of(columns[0], 
							columns[3] == null ? null : new Integer(columns[3]),
							columns[2] == null ? null : new Integer(columns[2]),
							columns[5] == null ? null : new Integer(columns[5]),
							columns[4] == null ? null : new Integer(columns[4]),
							columns[1] == null ? null : new Integer(columns[1]));
					cList.add(report);
				}
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		return cList;
	}

}
