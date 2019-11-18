package top.imlgw.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.provider.MD5;

import java.util.Random;

/**
 * @author imlgw.top
 * @date 2019/11/18 16:31
 */
public class MyPassWordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String salt=randomSalt(6);
        String password=charSequence.toString();
        password=password+salt;
        return salt+DigestUtils.md5Hex(password.getBytes());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String password=charSequence.toString();
        String salt=s.substring(0,6);
        return s.equals(salt+DigestUtils.md5Hex((password+salt).getBytes()));
    }

    private static String randomSalt(int count){
        //RandomStringUtils.random(6); 乱码
        String chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        //线程安全
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<count;i++){
            sb.append(chars.charAt(random.nextInt(62)));
        }
        return  sb.toString();
    }

    public static void main(String[] args) {
        String password=new MyPassWordEncoder().encode("123456");
        System.out.println(password);
    }
}
