package com.architect.demo.knoxmailIF.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.architect.demo.common.ApiService;
import com.architect.demo.common.BaseRes;
import com.architect.demo.common.CommonConst;
import com.architect.demo.common.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import javax.annotation.Resource;

@Log4j2
@Service
public class GetFromRedisExcutor {
	@Autowired private ApiService<Map> apiService;
	@Autowired RedisTemplate<String, Object> redisTemplate;
	
	public void FailedMailHandler() {
		try {
			while(true){
				final Object redisData = redisTemplate.opsForList().leftPop("knox-mail", 100, TimeUnit.MILLISECONDS);
				log.info("try to get data from redis =>" + redisData);
				if(redisData != null) {
					sendKnoxMail(redisData);
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
	}

	private void sendKnoxMail(Object redisData) {
		log.info("Handling redis data=>" + redisData);
		//String url = "http://localhost:8084/mail/send";
		String url = "http://knoxmail-g:8084/api/knoxmail/mail/send";
		String jsonInString = "";
		try {
			ResponseEntity<Map> res = apiService.post(url, Map.class);
			ObjectMapper mapper = new ObjectMapper();
			
			jsonInString = mapper.writeValueAsString(res.getBody());
			if(res.getStatusCode().equals(HttpStatus.OK)){
				log.info("Send Knox mail is completed successfully");
			}
		} catch (Exception e) {
			redisTemplate.opsForList().rightPush("knox-mail", redisData.toString());
			log.info("Send Knox mail is failed. push to the redis again");
		}

	}

}
