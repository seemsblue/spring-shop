package com.apple.myShop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MyUserDetailService userService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    String hello(){
        return ("index.html");    //기본폴더가 static 폴더라 함
    }
    @GetMapping("/signUp")
    public String signUp(){
        return "signUp.html";
    }
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/my-page")
    public String mypage(Authentication auth){
        if(auth==null){
            return "login.html";
        }
        System.out.println(auth);
        System.out.println(auth.getName());
        System.out.println(auth.isAuthenticated());
        return "mypage.html";
    }

    @PostMapping("/member")
    String register(String displayName, String username, String password){
        Member member = new Member();
        member.setDisplayName(displayName);
        member.setUsername(username);
        member.setPassword(new BCryptPasswordEncoder().encode(password));
        memberRepository.save(member);
        return "redirect:/list";
    }


}
