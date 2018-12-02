package me.qfdk.nasimie.service;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import me.qfdk.nasimie.tools.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class UpdateContainer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "${server.schedules}")
    public void refreshNetwork() {
        List<User> listUsers = userRepository.findAll();
        for (User user : listUsers) {
            Tools.refreshUserNetwork(user, userRepository, restTemplate, logger);
        }
        logger.info("-----------------------------------------");
    }

}