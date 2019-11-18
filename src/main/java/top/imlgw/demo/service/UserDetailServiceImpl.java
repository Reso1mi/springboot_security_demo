package top.imlgw.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/11/18 16:21
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> grantAuths=new ArrayList();
        grantAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return  new User("rootimlgw","NTJMFq82dfd421f08f118c101ef47aa50f76f2",grantAuths);
    }
}