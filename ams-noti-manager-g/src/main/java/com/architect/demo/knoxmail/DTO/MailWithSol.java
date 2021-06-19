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
public class MailWithSol{
	
	private String title;
	private String content;
	private String senderMailAddress;
	private String userName;
	private String fullName;
	protected List<String> recipient;
	private String systemKey;
	private String locale;
	private String timezone;
	
	
	public MailWithSol(MailDraft draft, String systemKey, String locale, String timezone) {
		this.title = draft.getTitle();
		this.content = draft.getContent();
		this.senderMailAddress = draft.getSenderMailAddress();
		this.userName = draft.getUserName();
		this.fullName = draft.getFullName();
		this.recipient = draft.getRecipient();
		
		this.systemKey = systemKey;
		this.locale = locale;
		this.timezone = timezone;
	}

}
