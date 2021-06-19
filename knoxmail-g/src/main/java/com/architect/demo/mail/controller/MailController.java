package com.architect.demo.mail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.mail.DTO.MailDraft;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class MailController {

	@ApiOperation(value = "knox메일 전송 (서킷브레이커 적용)")
	@RequestMapping(value= "/mail/send", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendMailCircuit() throws Exception{
		log.info("knox mail IF를 성공적으로 call");
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "knox mail IF를 성공적으로 call"), HttpStatus.OK);
	}
}
