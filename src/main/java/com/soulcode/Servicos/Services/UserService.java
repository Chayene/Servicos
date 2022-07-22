package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listar(){
        return userRepository.findAll();
    }

    public User cadastrar (User user){
        return userRepository.save(user);
    }

//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration
//                .defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(5))
//                .disableCachingNullValues()
//                .serializeValuesWith(serializationPair);
//    }
}
