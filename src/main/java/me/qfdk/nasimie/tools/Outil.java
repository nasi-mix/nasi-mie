package me.qfdk.nasimie.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.util.Base64;

public class Outil {
    public static String getSSRUrl(String host,String port, String password) throws UnsupportedEncodingException {
        String pass = Base64.getEncoder().encodeToString(password.getBytes("utf-8")).replace("=", "");
//        ssr://base64(host:port:protocol:method:obfs:base64pass
        String tmp = host+":"+port+":origin:rc4-md5:plain:" + pass;
        String str = Base64.getEncoder().encodeToString(tmp.getBytes("utf-8")).replace("=", "");
        return str;
    }
    public static String getRandomPort() throws IOException {
        // 生成随机可用端口
        ServerSocket s = new ServerSocket(0);
        String port=String.valueOf(s.getLocalPort());
        s.close();
        return port;
    }
}
