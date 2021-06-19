package com.architect.demo.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(value = Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class BaseRes implements Serializable{
	private String result;
	private Object content;
}
