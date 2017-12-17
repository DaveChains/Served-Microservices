package com.serveme.payment;

import com.serveme.payment.util.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.serveme.payment.api",
		"com.serveme.payment.persistence.converters",
        "com.serveme.payment.schedule",
        "com.serveme.payment.service",
        "com.serveme.payment.payment",
        "com.serveme.payment.util",
        "com.serveme.payment.config",
        "com.serveme.payment.health"
})
@EnableFeignClients("com.serveme.payment.service")
@EnableJpaRepositories(basePackages = "com.serveme.payment.persistence")
public class App {

	public static void main(String[] args) {
		Environment.setProfile(getSpringProfile(args));
		SpringApplication.run(App.class, args);
	}


	private static String getSpringProfile(String[] args) {
		if (args != null) {
			for (String arg : args) {
				if (arg.startsWith("--spring.profiles.active=")) {
					return arg.substring("--spring.profiles.active=".length());
				}
			}
		}

		String envProfile = System.getenv("SPRING_PROFILE");
		if (envProfile != null) {
			return envProfile;
		}

		return "dev";
	}
}
