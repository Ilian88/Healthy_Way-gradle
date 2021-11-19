package com.example.healthy_way.web;

import com.example.healthy_way.model.binding.LoginBindingModel;
import com.example.healthy_way.model.binding.UserRegisterBindingModel;
import com.example.healthy_way.model.serviceModel.UserServiceModel;
import com.example.healthy_way.service.UserService;
import org.hibernate.validator.constraints.ModCheck;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final String REDIRECT_HOME = "redirect:/";
    private final String REDIRECT_REGISTER = "redirect:register";

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public LoginBindingModel loginBindingModel() {
        return new LoginBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String errorLogin() {
        return "login-error";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute @Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.passwordsMatch()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);

            return REDIRECT_REGISTER;
        }

        this.userService.saveUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return REDIRECT_HOME;

        //TODO: enter the errors in the HTML and gender enum field to be <select>
    }

//    @PostMapping("/login")
//    public String loginConfirm(@ModelAttribute LoginBindingModel loginBindingModel,
//                               BindingResult bindingResult,
//                               RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("loginBindingModel", loginBindingModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
//                    bindingResult);
//
//
//            return "redirect:login";
//        }
//        if (this.userService.checkIfUserExistsByEmail(loginBindingModel.getEmail())) {
//            redirectAttributes.addFlashAttribute("loginBindingModel", loginBindingModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
//                    bindingResult);
//        }
//
//        return "redirect:/";
//
//    }
}
