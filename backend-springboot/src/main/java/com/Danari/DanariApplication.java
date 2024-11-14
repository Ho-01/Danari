package com.Danari;

import com.Danari.domain.Club;
import com.Danari.domain.Member;
import com.Danari.repository.ClubJpaRepository;
import com.Danari.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DanariApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanariApplication.class, args);
	}

	 // 초기 데이터 집어넣는 코드
//	@Bean
//	public CommandLineRunner dataLoader(MemberJpaRepository memberJpaRepository, ClubJpaRepository clubJpaRepository){
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		return args -> {
//			if(memberJpaRepository.findByUsername("InitMember").isEmpty()) {
//				memberJpaRepository.save(Member.builder().name("InitMember").studentId(32190000).password(passwordEncoder.encode("password")).username("username").build());
//			}
//			if(clubJpaRepository.findByClubName("InitClub").isEmpty()){
//				clubJpaRepository.save(Club.builder().clubName("InitClub").description("This is InitClub").department("InitDepartment").roomNumber("101").build());
//			}
//		};
//	}
}
