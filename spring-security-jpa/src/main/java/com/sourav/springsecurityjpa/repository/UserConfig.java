//package com.sourav.springsecurityjpa.repository;
//
//import com.sourav.springsecurityjpa.models.User;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class UserConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository repository) {
//        return args -> {
//            User user=new User("user1","pass",true,"ROLE_USER");
//            User admin=new User("user2","pass",true,"ROLE_ADMIN");
//            repository.saveAll(List.of(user,admin));
//        };
//    }
//}
