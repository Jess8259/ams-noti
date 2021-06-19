package com.architect.demo.knoxmailIF.repository;

import org.springframework.data.repository.CrudRepository;

import com.architect.demo.knoxmailIF.DTO.MailDraft;

public interface MailRepository extends CrudRepository<MailDraft, Long>{
	
}
