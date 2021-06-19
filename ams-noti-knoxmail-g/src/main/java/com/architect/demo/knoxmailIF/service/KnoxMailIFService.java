package com.architect.demo.knoxmailIF.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.architect.demo.common.ApiService;
import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.knoxmailIF.DTO.MailDraft;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KnoxMailIFService {
	@Autowired private ApiService<Map> apiService;
	@Autowired RedisTemplate<String, Object> redisTemplate;
	
	public ResponseEntity<BaseRes> getFallback(MailDraft draft){
		String str = "API를 처리하는 도중 오류가 발생하였습니다. Redis에 해당 request를 저장";
		log.info(str);
		redisTemplate.opsForList().rightPush("knox-mail", draft.toString());
		return null;
		//throw new RuntimeException();
		//return new ResponseEntity<>(new BaseRes(CommonConst.RST_FAIL, str), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<BaseRes> add1000BunchData(Long number){
		String str = number + "개의 데이터를 redis에 저장";
		log.info(str);
		MailDraft draft = new MailDraft();
		for(int i = 0 ; i < number ; i++){
			redisTemplate.opsForList().rightPush("knox-mail", draft.toString());
		}
		return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, str), HttpStatus.OK);
		//throw new RuntimeException();
		//return new ResponseEntity<>(new BaseRes(CommonConst.RST_FAIL, str), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//10초동안 에러율이 10이상일때 / 8번 연속 에러가 났을 경우 서킷브레이커 발동 => 5초간 open 상태유지
	//5초 이후 단 한개의 circuit을 유지한후, 이것이 성공하면 circuit breaker close (half open 상태)
//	@HystrixCommand(fallbackMethod = "getFallback", commandProperties= {
//		@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="10000"),
//		@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="10"),
//		@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="8"),
//		@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
//	})
	public ResponseEntity<BaseRes> sendCircuit(MailDraft draft) throws JsonProcessingException {
		log.info("send Knox API - Circuit");
		//String url = "http://localhost:8084/mail/send";
		String url = "http://knoxmail-g:8084/api/knoxmail/mail/send";
		String jsonInString = "";
		
		ResponseEntity<Map> res = apiService.post(url, draft);
		ObjectMapper mapper = new ObjectMapper();
		jsonInString = mapper.writeValueAsString(res.getBody());
		if(res.getStatusCode().equals(HttpStatus.OK)){
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<BaseRes> sendNotCircuit(MailDraft draft) throws JsonProcessingException {
		log.info("send Knox API - Not Circuit");
		//String url = "http://localhost:8084/mail/send";
		String url = "http://knoxmail-g:8084/api/knoxmail/mail/send";
		String jsonInString = "";
		
		ResponseEntity<Map> res = apiService.post(url, draft);
		ObjectMapper mapper = new ObjectMapper();
		jsonInString = mapper.writeValueAsString(res.getBody());
		if(res.getStatusCode().equals(HttpStatus.OK)){
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
