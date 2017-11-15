package dk.controllers;

import dk.models.entities.User;
import dk.models.repositories.IUserRepository;
import dk.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    HashMap<String, User> users = new HashMap();

    @Autowired
    IUserRepository userRepository = new UserRepository();

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user){
        userRepository.create(user);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(Model model){
        String ID = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = users.get(ID);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user){
        userRepository.update(user);
        return "redirect:/profile";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user){
        User member = userRepository.login(user);
        if(member.getName() == null && member.getPassword() == null) {
            return "redirect:/fail";
        }
        users.put(RequestContextHolder.currentRequestAttributes().getSessionId(),member);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute(users.get(RequestContextHolder.currentRequestAttributes().getSessionId()));
        return "profile";
    }
    @PostMapping("/profile")
    public String profile(){
        return "profile";
    }
    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }
    @GetMapping("/delete")
    public String delete(Model model){
        String ID = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = users.get(ID);
        model.addAttribute(user);
        return "delete";
    }
    @PostMapping("/delete")
    public String delete(@ModelAttribute User user){
        User member = userRepository.login(user);
        if(member.getName() == null && member.getPassword() == null) {
            return "redirect:/delete";
        }
        userRepository.delete(member.getUserId());
        return "redirect:/";
    }
}
