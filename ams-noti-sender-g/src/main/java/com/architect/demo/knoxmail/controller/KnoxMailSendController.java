package com.architect.demo.knoxmail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.knoxmail.DTO.MailDraft;
import com.architect.demo.knoxmail.service.KnoxMailSendService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class KnoxMailSendController {
	@Autowired KnoxMailSendService knoxMailSendService;
	
	@ApiOperation(value="Knox 메일 전송 (서킷)")
	@RequestMapping(value="/send/knoxmail/circuit", method=RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendknoxMailCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws JsonProcessingException{
		String str = "knox mail sender - circuit";
		log.info(str);
		knoxMailSendService.sendKnoxMailCircuit(draft);
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, str), HttpStatus.OK);		
	}
	
	@ApiOperation(value="Knox 메일 전송 (서킷 미적용)")
	@RequestMapping(value="/send/knoxmail/not-circuit", method=RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendknoxMailNotCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws JsonProcessingException{
		String str = "knox mail sender - not circuit";
		log.info(str);
		knoxMailSendService.sendKnoxMailNotCircuit(draft);
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, str), HttpStatus.OK);		
	}
}
