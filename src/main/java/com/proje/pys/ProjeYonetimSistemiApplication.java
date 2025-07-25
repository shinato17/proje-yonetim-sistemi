package com.proje.pys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.proje.pys.entity")
@EnableJpaRepositories(basePackages = "com.proje.pys.repository")
public class ProjeYonetimSistemiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjeYonetimSistemiApplication.class, args);
	}
}
