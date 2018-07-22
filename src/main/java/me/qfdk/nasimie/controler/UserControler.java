package me.qfdk.nasimie.controler;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import net.glxn.qrgen.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserControler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/findUserByWechatName")
    public String findUserByWechatName(Model model, @RequestParam("wechatName") String wechatName) {
        User user;
        if (redisTemplate.opsForValue().get(wechatName.trim()) != null) {
            user = (User) redisTemplate.opsForValue().get(wechatName.trim());
        } else {
            user = userRepository.findByWechatName(wechatName.trim());
            redisTemplate.opsForValue().set(wechatName.trim(), user);
        }
        if (user != null) {
            model.addAttribute("info", "success");
            model.addAttribute("user", user);
        } else {
            model.addAttribute("info", "error");
        }
        return "user";
    }

    @RequestMapping(value = "/qrCode/{qrCode}", method = RequestMethod.GET)
    public HttpEntity<byte[]> qr(@PathVariable String qrCode) {
        byte[] bytes = QRCode.from("ssr://" + qrCode).withSize(250, 250).stream().toByteArray();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}
