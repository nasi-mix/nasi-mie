package me.qfdk.nasimie.controler;

import com.spotify.docker.client.exceptions.DockerException;
import me.qfdk.nasimie.entity.User;
import me.qfdk.nasimie.repository.UserRepository;
import me.qfdk.nasimie.tools.Outil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller()
public class AdminUserControler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    RestTemplate restTemplate;

    private int currentPage;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String show(Authentication authentication, Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("users", userRepository.findAll(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("locations", getLocations());
        this.currentPage = page;
        return "admin";
    }

    @PostMapping("/save")
    public String save(User user) throws IOException {
        // 新用户
        if (user.getId() == null || StringUtils.isEmpty(user.getContainerId())) {
            String pass = new StringBuffer(user.getWechatName()).reverse().toString();
            String port = Outil.getRandomPort();

            String containerId = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/createContainer?wechatName=" + user.getWechatName(), String.class).getBody();
            String status = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/info?id=" + containerId, String.class).getBody();
            String host = client.getInstances(user.getContainerLocation()).get(0).getHost();
            user.setContainerId(containerId);
            user.setContainerStatus(status);
            user.setContainerPort(port);
            user.setQrCode(Outil.getSSRUrl(host, port, pass));
        }
        userRepository.save(user);
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        redisTemplate.opsForValue().set(user.getWechatName(), user);
        return "redirect:/admin?page=" + this.currentPage;
    }

    @GetMapping("/deleteContainer")
    public String deleteContainer(@RequestParam("id") String containerId, @RequestParam("role") String role) throws DockerException, InterruptedException {
        User user = userRepository.findByContainerId(containerId);
        restTemplate.getForEntity("http://" + user.getContainerLocation() + "/deleteContainer?id=" + containerId, Integer.class);
        user.setContainerStatus("");
        user.setContainerId("");
        user.setContainerPort("");
        user.setContainerLocation("");
        user.setQrCode("");
        userRepository.save(user);
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        redisTemplate.opsForValue().set(user.getWechatName(), user);
        if (role.equals("admin")) {
            return "redirect:/admin?page=" + this.currentPage;
        }
        return "redirect:/user/findUserByWechatName?wechatName=" + role;
    }

    @GetMapping("/startContainer")
    public String startContainer(@RequestParam("id") String containerId, @RequestParam("role") String role) {
        User user = userRepository.findByContainerId(containerId);
        String status = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/startContainer?id=" + containerId, String.class).getBody();
        user.setContainerStatus(status);
        userRepository.save(user);
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        redisTemplate.opsForValue().set(user.getWechatName(), user);
        if (role.equals("admin")) {
            return "redirect:/admin?page=" + this.currentPage;
        }
        return "redirect:/user/findUserByWechatName?wechatName=" + role;
    }

    @GetMapping("/restartContainer")
    public String restartContainer(@RequestParam("id") String containerId, @RequestParam("role") String role) {
        User user = userRepository.findByContainerId(containerId);

        String status = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/restartContainer?id=" + containerId, String.class).getBody();
        user.setContainerStatus(status);
        userRepository.save(user);
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        redisTemplate.opsForValue().set(user.getWechatName(), user);
        if (role.equals("admin")) {
            return "redirect:/admin?page=" + this.currentPage;
        }
        return "redirect:/user/findUserByWechatName?wechatName=" + role;
    }

    @GetMapping("/stopContainer")
    public String stopContainer(@RequestParam("id") String containerId, @RequestParam("role") String role) {
        User user = userRepository.findByContainerId(containerId);
        String status = restTemplate.getForEntity("http://" + user.getContainerLocation() + "/stopContainer?id=" + containerId, String.class).getBody();
        user.setContainerStatus(status);
        userRepository.save(user);
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        redisTemplate.opsForValue().set(user.getWechatName(), user);
        if (role.equals("admin")) {
            return "redirect:/admin?page=" + this.currentPage;
        }
        return "redirect:/user/findUserByWechatName?wechatName=" + role;
    }

    @GetMapping("/delete")
    public String delete(Integer id, @RequestParam("role") String role) {
        User user = userRepository.findById(id).get();
        String containerId = user.getContainerId();
        if (!StringUtils.isEmpty(user.getContainerLocation())) {
            restTemplate.getForEntity("http://" + user.getContainerLocation() + "/deleteContainer?id=" + containerId, Integer.class).getBody();
        }
        userRepository.deleteById(id);
        redisTemplate.opsForValue().getOperations().delete(id.toString());
        redisTemplate.opsForValue().getOperations().delete(user.getWechatName());
        if (role.equals("admin")) {
            return "redirect:/admin?page=" + this.currentPage;
        }
        return "redirect:/findUserByWechatName?wechatName=" + role;
    }

    @GetMapping("/findUserById")
    @ResponseBody
    public User findUserById(@RequestParam("id") Integer id) {
        User user;
        if (redisTemplate.opsForValue().get(id.toString()) != null) {
            user = (User) redisTemplate.opsForValue().get(id.toString());
        } else {
            user = userRepository.findById(id).get();
            redisTemplate.opsForValue().set(id.toString(), user);
            redisTemplate.opsForValue().set(user.getWechatName(), user);
        }
        return user;
    }

    public List<String> getLocations() {
        List<String> services = client.getServices();
        List<String> avalibleServices = new ArrayList<>();
        for (String service : services) {
            if (service.indexOf("campur") != -1) {
                avalibleServices.add(service);
            }
        }
        return avalibleServices;
    }
}
