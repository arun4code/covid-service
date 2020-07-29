package com.project.controller;

import static com.project.util.HeaderUtil.addError;
import static com.project.util.HeaderUtil.addSuccess;
import static org.springframework.http.MediaType.ALL_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.CovidReportCreateRequest;
import com.project.dto.CovidReportResponse;
import com.project.dto.CovidReportUpdateRequest;
import com.project.dto.CovidReportUploadResponse;
import com.project.dto.ResponseStatus;
import com.project.service.CovidReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("RestAPI")
@Api(value = "UserDataSet", description = "Perform CRUD operation user data using Rest API")
public class CovidReportController {

	private final CovidReportService service;

	public CovidReportController(CovidReportService service) {
		this.service = service;
	}

	@GetMapping(value = "/cReport")
    @ApiOperation(value = "Places a new transaction on the system.", notes = "Creates a new transaction in the system. See the schema of the Transaction parameter for more information ", tags={ "transaction", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Another transaction with the same messageId already exists in the system. No transaction was created."),
        @ApiResponse(code = 201, message = "The transaction has been correctly created in the system"),
        @ApiResponse(code = 400, message = "The transaction schema is invalid and therefore the transaction has not been created.", response = String.class),
        @ApiResponse(code = 415, message = "The content type is unsupported"),
        @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.") })
	public ResponseEntity<ResponseStatus<List<CovidReportResponse>>> getAllData() {
		ResponseStatus<List<CovidReportResponse>> responseStatus =new ResponseStatus<>();
		List<CovidReportResponse> listResp = null;
		try {
			listResp = service.findAllData();
		}catch(Exception e) {
	    	 responseStatus.setMsg(e.getMessage());
	 	     responseStatus.setStatus(false);
	 	     return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		responseStatus.setData(listResp);
	    responseStatus.setMsg("OTP is send to your email Id");
	    responseStatus.setStatus(true);
	    return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@GetMapping(value = "/cReport/{id}")
	@ApiOperation(value = "fetch Report by its Id", response = CovidReportResponse.class)
	public CovidReportResponse getReportById(@PathVariable Long id) {
		return service.findReportById(id);
	}

	@GetMapping(value = "/cReport/filter")
	@ApiOperation(value = "Filter Report by location, active, confirmed and dead cases", response = CovidReportResponse.class)
	public List<CovidReportResponse> getReportByFilter(
			@RequestParam(value = "location", required = true) String location,
			@RequestParam(value = "active", required = false) Integer active,
			@RequestParam(value = "confirmed", required = false) Integer confirmed,
			@RequestParam(value = "dead", required = false) Integer dead) {

		return service.findByFilter(CovidReportCreateRequest.of(location, active, confirmed, dead, null, null));
	}

	@PatchMapping(value = "/cReport/{location}")
	@ApiOperation(value = "Update existing User", response = CovidReportResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Another transaction with the same messageId already exists in the system. No transaction was created."),
        @ApiResponse(code = 201, message = "The transaction has been correctly created in the system"),
        @ApiResponse(code = 400, message = "The transaction schema is invalid and therefore the transaction has not been created.", response = String.class),
        @ApiResponse(code = 415, message = "The content type is unsupported"),
        @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.") })
	public CovidReportResponse updateReport(@PathVariable String location,
			@ApiParam(name = "updateRequest")
			@RequestBody CovidReportUpdateRequest request) {
		return service.updateReport(request, location);
	}

	@PostMapping("/cReport/upload")
	@ApiOperation(nickname = "upload-Covid-report", value = "upload standard-charge from csv sheet", notes = "A Valid sheet must be uploaded "
			+ "If report is not present for given location, it will create record, otherwise update existing record.", consumes = "multipart/form-data", produces = ALL_VALUE, response = CovidReportUploadResponse.class, responseContainer = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Another transaction with the same messageId already exists in the system. No transaction was created."),
        @ApiResponse(code = 201, message = "The transaction has been correctly created in the system"),
        @ApiResponse(code = 400, message = "The transaction schema is invalid and therefore the transaction has not been created.", response = String.class),
        @ApiResponse(code = 415, message = "The content type is unsupported"),
        @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.") })
	public ResponseEntity<String> uploadStandardCharge(@RequestParam("file") MultipartFile readDataFile) {

		CovidReportUploadResponse results = service.upload(readDataFile);
		if (results == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(addError("Upload failed")).build();
		}
		return ResponseEntity.ok().headers(addSuccess("Upload successfully")).body(results.setMessage());

	}
}
