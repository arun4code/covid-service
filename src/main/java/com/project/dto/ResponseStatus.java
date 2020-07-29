package com.project.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String msg;
	private T data;
	private boolean status;

	public ResponseStatus(String msg, boolean status) {
		this.msg = msg;
		this.status = status;
	}
}
