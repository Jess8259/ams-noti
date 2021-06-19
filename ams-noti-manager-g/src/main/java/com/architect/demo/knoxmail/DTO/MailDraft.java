package com.architect.demo.knoxmail.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailDraft{
	
	private String title;
	
	private String content;
	
	private String senderMailAddress;
	
	private String userName;
	
	private String fullName;
	
	protected List<String> recipient;

}
