package me.qfdk.nasimie.controler;

import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("/findUserByWechatName")
    public String findUserByWechatName(Model model, @RequestParam("wechatName") String wechatName) {
        User user = userRepository.findByWechatName(wechatName.trim());

        if (user != null) {
            model.addAttribute("info", "success");
            model.addAttribute("user", user);
        } else {
            model.addAttribute("info", "error");
        }
        return "user";
    }
}
