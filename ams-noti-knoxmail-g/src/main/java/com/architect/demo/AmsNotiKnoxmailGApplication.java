package com.architect.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.architect.demo.knoxmailIF.service.GetFromRedisExcutor;

@SpringBootApplication
public class AmsNotiKnoxmailGApplication implements CommandLineRunner{

	@Autowired AutowireCapableBeanFactory autowireCapableBeanFactory;

	public static void main(String[] args) {
		SpringApplication.run(AmsNotiKnoxmailGApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0 && args[0].equals("exitcode")) {
			throw new ExitException();
		}
		initGetFromRedisProcessor();
	}

	private void initGetFromRedisProcessor() {
		GetFromRedisExcutor getFromRedisExcutor = new GetFromRedisExcutor();
		autowireCapableBeanFactory.autowireBean(getFromRedisExcutor);
		new Thread(()-> {
			getFromRedisExcutor.FailedMailHandler();
		}).start();
	}
	
	static class ExitException extends RuntimeException implements ExitCodeGenerator {

		@Override
		public int getExitCode() {
			return 10;
		}
	}

}
