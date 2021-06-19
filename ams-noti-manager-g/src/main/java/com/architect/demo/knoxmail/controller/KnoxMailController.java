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
import com.architect.demo.knoxmail.service.KnoxMailService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class KnoxMailController {
	@Autowired KnoxMailService knoxMailService;
	
	@ApiOperation(value = "knox메일 통보 메타정보 저장")
	@RequestMapping(value= "/knoxmail", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> createKnoxInfo() throws Exception{
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "AMS"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "knox메일 통보 메타정보 수정")
	@RequestMapping(value= "/knoxmail/{solution}/{id}", method = RequestMethod.PATCH, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> updateKnoxInfo() throws Exception{
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "AMS"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "knox메일 통보 메타정보 삭제")
	@RequestMapping(value= "/knoxmail/{solution}/{id}", method = RequestMethod.DELETE, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> deleteKnoxInfo() throws Exception{
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "AMS"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "knox메일 통보 메타정보 리스트 조회")
	@RequestMapping(value= "/knoxmail/{solution}", method = RequestMethod.GET, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> getKnoxInfoList() throws Exception{
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "AMS"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "knox메일 통보 메타정보 상세 조회")
	@RequestMapping(value= "/knoxmail/{solution}/{id}", method = RequestMethod.GET, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> getKnoxInfo() throws Exception{
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, "AMS"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "knox메일 전송 (서킷브레이커 적용)")
	@RequestMapping(value= "/knoxmail/{solution}/{id}/send/circuit", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendMailCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws Exception{
		return knoxMailService.sendCircuit(draft); 
	}
	
	@ApiOperation(value = "knox메일 전송 (서킷브레이커 미 적용) ")
	@RequestMapping(value= "/knoxmail/{solution}/{id}/send/not-circuit", method = RequestMethod.POST, produces = {CommonConst.API_JSON})
	public ResponseEntity<BaseRes> sendMailNotCircuit(@ApiParam(value = "Mail draft", required = true) @RequestBody MailDraft draft) throws Exception{
		return knoxMailService.sendNotCircuit(draft);
	}
}
