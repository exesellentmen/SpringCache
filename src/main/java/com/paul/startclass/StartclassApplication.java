package com.paul.startclass;

import com.paul.startclass.models.BIblock;
import com.paul.startclass.models.User;
import com.paul.startclass.repository.BlockRepository;
import com.paul.startclass.repository.UserRepository;
import com.paul.startclass.services.Test;
import com.paul.startclass.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
@EnableCaching
public class StartclassApplication {

//	@Autowired
//	UserService userService;

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(StartclassApplication.class, args);
		UserService userService = applicationContext.getBean(UserService.class);
//		User userById = userService.getUserById(1);
//		User userById1 = userService.getUserById(1);
//		User userById2 = userService.getUserById(1);



//		org.redisson.hibernate.RedissonRegionFactory.class.get


//		jakarta.persistence.spi.PersistenceProvider

//		org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider

//		org.hibernate.cache.ehcache.EhCacheRegionFactory

//		EhcacheRegionFactory

//		Optional<User> user = userRepository.findById(1L);
//		Optional<User> user1 = userRepository.findById(1L);
//		Optional<User> user2 = userRepository.findById(1L);

//		//TestInject
//		Test test = applicationContext.getBean(Test.class);
//		BlockRepository blockRepository = applicationContext.getBean(BlockRepository.class);
//		Iterable<BIblock> bIblocks = blockRepository.findAll();
//		System.out.println("test");
	}

}
