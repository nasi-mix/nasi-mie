package me.qfdk.nasimie.service;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import me.qfdk.nasimie.tools.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
//@EnableScheduling
public class UpdateContainer {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ContainerService containerService;
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Scheduled(cron = "${server.schedules}")
//    public void refreshNetwork() {
//        List<User> listUsers = userRepository.findAll();
//        for (User user : listUsers) {
//            containerService.refreshUserNetwork(user, userRepository, restTemplate, logger);
//        }
//        logger.info("-----------------------------------------");
//    }

}