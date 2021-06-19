package com.architect.demo.knoxmail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.architect.demo.common.ApiService;
import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.knoxmail.DTO.MailDraft;
import com.architect.demo.knoxmail.DTO.MailWithSol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class KnoxMailService {
	
	@Autowired private ApiService<Map> apiService;
	
	public ResponseEntity<BaseRes> getFallback(MailDraft draft){
		String str = "API瑜� 泥섎━�븯�뒗 �룄以� �삤瑜섍� 諛쒖깮�븯���뒿�땲�떎.";
		throw new RuntimeException();
		//return new ResponseEntity<>(new BaseRes(CommonConst.RST_FAIL, str), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//10珥덈룞�븞 �뿉�윭�쑉�씠 10�씠�긽�씪�븣 / 8踰� �뿰�냽 �뿉�윭媛� �궗�쓣 寃쎌슦 �꽌�궥釉뚮젅�씠而� 諛쒕룞 => 5珥덇컙 open �긽�깭�쑀吏�
	//5珥� �씠�썑 �떒 �븳媛쒖쓽 circuit�쓣 �쑀吏��븳�썑, �씠寃껋씠 �꽦怨듯븯硫� circuit breaker close (half open �긽�깭)
//	@HystrixCommand(fallbackMethod = "getFallback", commandProperties= {
//			@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="10000"),
//			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="10"),
//			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="8"),
//			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
//	})
	public ResponseEntity<BaseRes> sendCircuit(MailDraft draft) throws JsonProcessingException {
		log.info("send Knox API - Circuit");
		//String url = "http://localhost:8082/send/knoxmail/circuit";
		String url = "http://ams-noti-sender-g:8082/api/ams-noti-sender/send/knoxmail/circuit";
		String jsonInString = "";
		
		MailWithSol mailWithSol = new MailWithSol(draft, "AMS", "ko", "Asis/Seoul");
		ResponseEntity<Map> res = apiService.post(url, mailWithSol);
		ObjectMapper mapper = new ObjectMapper();
		jsonInString = mapper.writeValueAsString(res.getBody());
		if(res.getStatusCode().equals(HttpStatus.OK)){
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<BaseRes> sendNotCircuit(MailDraft draft) throws JsonProcessingException {
		log.info("send Knox API");
		//String url = "http://localhost:8082/send/knoxmail/not-circuit";
		String url = "http://ams-noti-sender-g:8082/api/ams-noti-sender/send/knoxmail/not-circuit";
		String jsonInString = "";
		
		MailWithSol mailWithSol = new MailWithSol(draft, "AMS", "ko", "Asis/Seoul");
		ResponseEntity<Map> res = apiService.post(url, mailWithSol);
		ObjectMapper mapper = new ObjectMapper();
		jsonInString = mapper.writeValueAsString(res.getBody());
		if(res.getStatusCode().equals(HttpStatus.OK)){
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new BaseRes(CommonConst.RST_SUCCESS, jsonInString), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
