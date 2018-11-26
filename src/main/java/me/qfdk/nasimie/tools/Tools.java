package me.qfdk.nasimie.tools;

import com.spotify.docker.client.messages.NetworkStats;
import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import me.qfdk.nasimie.service.DockerService;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.util.Base64;

public class Tools {
    public static String getSSRUrl(String host, String port, String password, String remark) throws UnsupportedEncodingException {
        String pass = new String(Base64.getEncoder().encode(password.getBytes("UTF-8"))).replace("=", "");
        //ssr://base64(host:port:protocol:method:obfs:base64pass
        String tmp = host + ":" + port + ":origin:rc4-md5:plain:" + pass + "/?remarks=" + remark;
        return new String(Base64.getEncoder().encode(tmp.getBytes("UTF-8"))).replace("=", "");
    }

    public static String getRandomPort() throws IOException {
        // 生成随机可用端口
        ServerSocket s = new ServerSocket(0);
        String port = String.valueOf(s.getLocalPort());
        s.close();
        return port;
    }

    public static void refreshUserNetwork(User user, DockerService dockerService, UserRepository userRepository, Logger logger) {
        try {
            NetworkStats traffic = dockerService.getContainerState(user.getContainerId()).networks().get("eth0");
            user.setNetworkTx(traffic.txBytes() / 1000000.0);
            user.setNetworkRx(traffic.rxBytes() / 1000000.0);
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
