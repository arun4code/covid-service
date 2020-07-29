package com.project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.dto.CovidReportCreateRequest;
import com.project.dto.CovidReportResponse;
import com.project.dto.CovidReportUpdateRequest;
import com.project.dto.CovidReportUploadResponse;

public interface CovidReportService {

	public List<CovidReportResponse> findAllData();

	public CovidReportUploadResponse upload(MultipartFile readDataFile);

	public CovidReportResponse updateReport(CovidReportUpdateRequest request, String location);

	public List<CovidReportResponse> findByFilter(CovidReportCreateRequest covidRequest);

	public CovidReportResponse findReportById(Long id);

}
