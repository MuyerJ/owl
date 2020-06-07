package owl.user;

import com.owl.dao.UserDao;
import com.owl.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"classpath:/config/springmvc.xml","classpath:/config/mybatis.xml"})
@ComponentScan(basePackages={"com.owl.controller", "com.owl.service","com.owl.dao"})
public class UserTest {

    @Autowired
    private UserDao userDao;
    @Test
    public void test1() {
        User user = userDao.getUserById("1");
        System.out.println(user);
    }

}
