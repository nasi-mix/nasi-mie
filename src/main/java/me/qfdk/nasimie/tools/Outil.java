package me.qfdk.nasimie.tools;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Outil {
    public static String getSSRUrl(String host, String port, String password, String remark) throws UnsupportedEncodingException {
        String pass = new String(Base64.getEncoder().encode(password.getBytes("UTF-8"))).replace("=", "");
        //ssr://base64(host:port:protocol:method:obfs:base64pass
        remark = new String(Base64.getEncoder().encode(remark.getBytes())).replace("=", "");
        String tmp = host + ":" + port + ":origin:rc4-md5:plain:" + pass + "/?remarks=" + remark;
        return new String(Base64.getEncoder().encode(tmp.getBytes("UTF-8"))).replace("=", "");
    }
}
