package me.qfdk.nasimie.common;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import net.glxn.qrgen.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Api {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/api/qrCode", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> qr(@RequestParam("str") String qrCode) {
        byte[] bytes = QRCode.from(qrCode).withSize(250, 250).stream().toByteArray();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/getProxyList")
    @ResponseBody
    public List<User> getProxyPort(@RequestParam("location") String location) {
        return userRepository.findUsersByPontLocation(location);
    }

}