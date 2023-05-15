package kr.co.monitoringserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MonitoringServerApplication {
		public static void main(String[] args) {
				SpringApplication.run(MonitoringServerApplication.class, args);
		}

}