package me.qfdk.nasimie.tools;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

@Component
public class Tools {
    public static String getSSRUrl(String host, String port, String password, String remark) throws UnsupportedEncodingException {
        String pass = new String(Base64.getEncoder().encode(password.getBytes("UTF-8"))).replace("=", "");
        //ssr://base64(host:port:protocol:method:obfs:base64pass
        String tmp = host + ":" + port + ":origin:rc4-md5:plain:" + pass + "/?remarks=" + remark;
        return new String(Base64.getEncoder().encode(tmp.getBytes("UTF-8"))).replace("=", "");
    }

    @Async
    public void refreshUserNetwork(User user, UserRepository userRepository, RestTemplate restTemplate, Logger logger) {
        try {
            Map<String, Double> traffic = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/getNetworkStats?id=" + user.getContainerId(), Map.class).getBody();
            user.setNetworkTx(traffic.get("txBytes"));
            user.setNetworkRx(traffic.get("rxBytes"));
            userRepository.save(user);
            logger.info("[Network Traffic](OK)-> " + user.getWechatName());
        } catch (Exception e) {
            logger.error("[Network Traffic](KO)-> " + user.getWechatName());
            user.setNetworkTx(0.0);
            user.setNetworkRx(0.0);
            userRepository.save(user);
        }
    }
}
