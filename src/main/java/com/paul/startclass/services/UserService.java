package com.paul.startclass.services;

import com.paul.startclass.models.User;
import com.paul.startclass.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@Component
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepository userRepository;



    public void cacheViaSession(){
        entityManager.setFlushMode(FlushModeType.AUTO);

        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, 1L);
        boolean contains = session.contains(user);
        System.out.println(session.contains(user));
        User user2 = session.get(User.class, 1L);
    }



    @CacheEvict(value = "users", key = "#user.id")
    public void delete(User user){
        userRepository.delete(user);
    }


    @CacheEvict(value = "users", key = "#userId")
    public void deleteUserById(Long userId) {
        // Perform delete operation
        userRepository.deleteById(userId);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void evictAllUsers() {
        // Perform other operations and evict all users from cache
    }







}
