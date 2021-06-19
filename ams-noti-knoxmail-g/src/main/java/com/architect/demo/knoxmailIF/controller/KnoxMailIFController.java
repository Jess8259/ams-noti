package com.architect.demo.knoxmailIF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.knoxmailIF.DTO.MailDraft;
import com.architect.demo.knoxmailIF.service.KnoxMailIFService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class KnoxMailIFController { 

	@Autowired KnoxMailIFService knoxMailIFService;
	
	@ApiOperation(value = "knox메일 전송 (서킷브레이커 적용)")
	@RequestMapping(value= "/knoxmail/if/send/circuit", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendMailCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws Exception{
		return knoxMailIFService.sendCircuit(draft);
	}
	
	@ApiOperation(value = "knox메일 전송 (서킷브레이커 미적용)")
	@RequestMapping(value= "/knoxmail/if/send/not-circuit", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendMailNotCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws Exception{
		return knoxMailIFService.sendNotCircuit(draft);
	}

	@ApiOperation(value = "1000개의 미통보 메시지 발생")
	@RequestMapping(value= "/knoxmail/if/send/test-bunch/{number}", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> add1000BunchData(@PathVariable(value = "number") Long number) throws Exception{
		return knoxMailIFService.add1000BunchData(number);
	}
}
