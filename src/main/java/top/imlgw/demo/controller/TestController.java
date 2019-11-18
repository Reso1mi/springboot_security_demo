package top.imlgw.demo.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/17 17:34
 */
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启这个注解才能有角色验证
public class TestController {

    @RequestMapping("/hello")
    public  String hello(){
        return "hello spring security";
    }

    @RequestMapping("/")
    public  String home(){
        return  "hello spring";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')") //只有ADMIN角色的用户才能登陆
    @RequestMapping("/admin")
    public String roleTest(){
        return  "admin role";
    }

    @RequestMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN') and #id<10 and principal.username.equals(#username) and #user.username.equals('abc')")
    @PostAuthorize("returnObject%2==0") //调用完成后,对返回值校验
    public Integer roleTest2(Integer id,String username,User user){
        return  100;
    }

    @PostFilter("filterObject%2==0") //只有list中的偶数能传递进去
    @PreFilter("filterObject%2==0")
    public List<Integer> roleTest3(List<Integer> list){
        return  list;
    }
}
