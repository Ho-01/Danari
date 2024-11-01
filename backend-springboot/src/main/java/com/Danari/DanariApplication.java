package com.Danari;

import com.Danari.domain.Member;
import com.Danari.repository.MemberJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DanariApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanariApplication.class, args);
	}

	// 초기 데이터 집어넣는 코드
//	@Bean
//	public CommandLineRunner dataLoader(MemberJpaRepository memberJpaRepository){
//		return args -> {
//			memberJpaRepository.save(Member.builder().name("InitMember").studentId(32190000).password("InitPW").username("InitID").build());
//		};
//	}
}
