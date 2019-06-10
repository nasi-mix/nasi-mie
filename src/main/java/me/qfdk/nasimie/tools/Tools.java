package me.qfdk.nasimie.tools;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
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
        String newRemark = new String(Base64.getEncoder().encode(remark.getBytes("UTF-8")));
        String tmp = host + ":" + port + ":origin:rc4-md5:plain:" + pass + "/?remarks=" + newRemark;
        return new String(Base64.getEncoder().encode(tmp.getBytes())).replace("=", "");
    }

    public static void updateInfo(DiscoveryClient client, User user, Map<String, String> info) throws UnsupportedEncodingException {
        String host = client.getInstances(user.getContainerLocation()).get(0).getHost();
        user.setContainerId(info.get("containerId"));
        user.setContainerStatus(info.get("status"));
        user.setContainerPort(info.get("port"));
        user.setQrCode(Tools.getSSRUrl(host, info.get("port"), info.get("pass"), user.getContainerLocation()));
    }

}
