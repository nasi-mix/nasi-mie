package me.qfdk.nasimie.service;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import me.qfdk.nasimie.tools.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class ContainerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public void refreshUserNetwork(User user, RestTemplate restTemplate, Logger logger) {
        try {
            Map<String, Double> traffic = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/getNetworkStats?id=" + user.getContainerId(), Map.class).getBody();
            if (user.getNetworkTx() > traffic.get("txBytes")) {
                user.setNetworkTx(user.getNetworkTx() + traffic.get("txBytes"));
                user.setNetworkRx(user.getNetworkRx() + traffic.get("rxBytes"));
            } else {
                user.setNetworkTx(traffic.get("txBytes"));
                user.setNetworkRx(traffic.get("rxBytes"));
            }

            userRepository.save(user);
            logger.info("[Network Traffic](OK)-> " + user.getWechatName());
        } catch (Exception e) {
            logger.error("[Network Traffic](KO)-> " + user.getWechatName());
            logger.error(e.getMessage());
//            user.setNetworkTx(0.0);
//            user.setNetworkRx(0.0);
//            userRepository.save(user);
        }
    }

    public void reCreateContainer(User user, RestTemplate restTemplate) {
        logger.info("[User][Container](" + user.getWechatName() + ") will create a new container.");
        try {
            Map<String, String> info = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/createContainer?wechatName=" + user.getWechatName() + "&port=" + user.getContainerPort(), Map.class).getBody();
            userRepository.save(Tools.updateInfo(user, info));
            logger.info("[User][Container](OK) : " + user.getContainerId());
        } catch (Exception e) {
            logger.error("[User][Container](KO) -> " + user.getWechatName());
            logger.error(e.toString());
        }
    }

    public void reCreateContainerByUser(User user, RestTemplate restTemplate) {
        logger.info("[User][Container](" + user.getWechatName() + ") will recreate a new container.");
        try {
            Map<String, String> info = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/reCreateContainer?wechatName=" + user.getWechatName(), Map.class).getBody();
            userRepository.save(Tools.updateInfo(user, info));
            logger.info("[User][Container](OK) : " + user.getContainerId());
        } catch (Exception e) {
            logger.error("[User][Container](KO) -> " + user.getWechatName());
            logger.error(e.toString());
        }
    }

    @Scheduled(cron = "${server.schedules}")
    public void refreshNetwork() {
        logger.info("-------------自动定时任务-------------------");
        List<User> listUsers = userRepository.findAll();
        for (User user : listUsers) {
            refreshUserNetwork(user, restTemplate, logger);
        }
        logger.info("-----------------------------------------");
    }

}
