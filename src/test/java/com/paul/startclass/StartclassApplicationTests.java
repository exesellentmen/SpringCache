package com.paul.startclass;

import com.paul.startclass.models.User;
import com.paul.startclass.repository.UserRepository;
import com.paul.startclass.services.UserService;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.hibernate.RedissonRegionFactory;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


//@Transactional
@SpringBootTest
//@PropertySource("classpath:application.properties")
class StartclassApplicationTests {

	@Autowired
	UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UserService userService;


	//--------Third level hibernate cache-----------

//	@Autowired
//	private RedissonClient redissonClient;

	@Test
	void userCachingOfThirdLevel() throws InterruptedException {
		User user = entityManager.find(User.class, 1L);
		User user1 = entityManager.find(User.class, 1L);
		User user2 = entityManager.find(User.class, 1L);
	}



	//--------Second level hibernate cache-----------

	// Entity caching by way of @Cacheable and @Cache by User example
	// We have to see 1 requests
	@Test
	void userCachingOfSecondLevel() throws InterruptedException {
		User user = entityManager.find(User.class, 1L);
//		TimeUnit.SECONDS.sleep(100);
		User user1 = entityManager.find(User.class, 1L);
		User user2 = entityManager.find(User.class, 1L);
	}

	// Second Level Hibernate caching with Expiration, that set cache time(5 seconds) via jcache.xml
	// We have to see 2 request because because in 3th our cache time's up
	@Test
	void secondCacheExpiration() throws InterruptedException {
		// 1 request
		User user = entityManager.find(User.class, 1L);
		TimeUnit.SECONDS.sleep(4);
		User user2 = entityManager.find(User.class, 1L);

		TimeUnit.SECONDS.sleep(10);
		// 2 request
		User user3 = entityManager.find(User.class, 1L);
	}


	// Evition. Removing entity from Second Level Hibernate cache
	@Test
	void secondCachEviction(){
		User user = entityManager.find(User.class, 1L);
		Cache cache = entityManager.getEntityManagerFactory().getCache();
		cache.evict(User.class,1L);
		User user2 = entityManager.find(User.class, 1L);

		userRepository.findById(1L);
	}


	// Evict by way of annotation
	@Test
	void evictionViaAnnotation(){
		User user = new User();
		userRepository.save(user);
		User user1 = entityManager.find(User.class, user.getId());
		userRepository.delete(user);
		User user2 = entityManager.find(User.class, user.getId()); // we have to see this query, because we delete this user with cache
	}

	@CacheEvict(value = "users", key = "#user.id")
	public void delete(User user){
		userRepository.delete(user);
	}





	//--------First level hibernate cache-----------


	@Test
	void session(){
	}


	@Test
	void cacheViaUserServiceSession(){
		userService.cacheViaSession();
	}


	// Doesn't work
	@Test
	void cacheViaSession(){
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, 1L);
		System.out.println(session.contains(user));
		User user2 = session.get(User.class, 1L);
	}

	// Work
	@Test
	@Transactional
	void test3(){
		User user = entityManager.find(User.class, 1L);
		User user2 = entityManager.find(User.class, 1L);
		User user3 = entityManager.find(User.class, 1L);
	}


	// Doesn't work
	@Test
	void contextLoads() {
		Optional<User> user = userRepository.findById(1L);
		Optional<User> user1 = userRepository.findById(1L);
		Optional<User> user2 = userRepository.findById(1L);
	}

	// Doesn't work
	@Test
	@Commit
	void test(){
		Optional<User> user = userRepository.findById(1L);
		Optional<User> user1 = userRepository.findById(1L);
		Optional<User> user2 = userRepository.findById(1L);
	}

	//--------End: First level hibernate cache-----------



}
