package com.architect.demo.knoxmail.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.architect.demo.common.ApiService;
import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.knoxmail.DTO.MailDraft;
import com.architect.demo.knoxmail.DTO.RefinedMail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KnoxMailSendService {

	@Autowired private ApiService<Map> apiService;
	
	public ResponseEntity<BaseRes> getFallback(MailDraft draft){
		String str = "API를 처리하는 도중 오류가 발생하였습니다.";
		throw new RuntimeException();
		//return new ResponseEntity<>(new BaseRes(CommonConst.RST_FAIL, str), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//10초동안 에러율이 10이상일때 / 8번 연속 에러가 났을 경우 서킷브레이커 발동 => 5초간 open 상태유지
	//5초 이후 단 한개의 circuit을 유지한후, 이것이 성공하면 circuit breaker close (half open 상태)
//	@HystrixCommand(fallbackMethod = "getFallback", commandProperties= {
//			@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="10000"),
//			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="10"),
//			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="8"),
//			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
//	})
	public ResponseEntity<BaseRes> sendKnoxMailCircuit(MailDraft draft) throws JsonProcessingException {
			log.info("send Knox API - Circuit");
			//String url = "http://localhost:8083/knoxmail/if/send/circuit";
			String url = "http://ams-noti-knoxmail-g:8083/api/ams-noti-knoxmail/knoxmail/if/send/circuit";
			String jsonInString = "";
			ObjectMapper mapper = new ObjectMapper();
			
			RefinedMail refinedMail = new RefinedMail();
			ResponseEntity<Map> res = apiService.post(url, refinedMail);
			
			jsonInString = mapper.writeValueAsString(res.getBody());
			if(res.getStatusCode().equals(HttpStatus.OK)){
				return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

	public ResponseEntity<BaseRes> sendKnoxMailNotCircuit(MailDraft draft) throws JsonProcessingException {
		log.info("send Knox API - Not Circuit");
		//String url = "http://localhost:8083/knoxmail/if/send/not-circuit";
		String url = "http://ams-noti-knoxmail-g:8083/api/ams-noti-knoxmail/knoxmail/if/send/not-circuit";
		String jsonInString = "";
		ObjectMapper mapper = new ObjectMapper();
		
		RefinedMail refinedMail = new RefinedMail();
		ResponseEntity<Map> res = apiService.post(url, refinedMail);
		
		jsonInString = mapper.writeValueAsString(res.getBody());
		if(res.getStatusCode().equals(HttpStatus.OK)){
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
