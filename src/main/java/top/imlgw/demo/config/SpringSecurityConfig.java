package top.imlgw.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import top.imlgw.demo.utils.MyPassWordEncoder;

/**
 * @author imlgw.top
 * @date 2019/11/17 19:51
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存验证
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("ADMIN");
        //无加密方式, Spring5之后已被弃用
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("root")
                .password("admin")
                .roles("ADMIN");

        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("imlgw")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("USER");

        //Spring默认的jdbc用户验证,感觉没啥意义
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select ......").authoritiesByUsernameQuery("sele");

        //指定我们定义的UserService和passwordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(new MyPassWordEncoder());

    }

    /**
     * 请求拦截
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //项目主路径允许访问
                .anyRequest().authenticated()  //其它请求必须经过验证
                .and()
                .logout().permitAll() //注销是允许访问的
                .and()
                .formLogin(); //表单登录是允许的
        http.csrf().disable(); //关闭csrf认证
    }

    /**
     * 资源拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    public static void main(String[] args) {
        String pass = "password";
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(pass);
        System.out.println(hashPass);

        boolean f = bcryptPasswordEncoder.matches("password",hashPass);
        System.out.println(f);
    }
}
