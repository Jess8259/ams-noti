package com.architect.demo.knoxmail.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MailDraftRecipient implements Comparable<MailDraftRecipient>{

	private int seq;
	
	private String recipientType;
	
	private String name;
	
	private String login;
	
	private Long id;
	
	private String email;
	
	private String epid;
	
	@Override
	public int compareTo(MailDraftRecipient o) {
		return this.seq - o.getSeq();
	}

}
