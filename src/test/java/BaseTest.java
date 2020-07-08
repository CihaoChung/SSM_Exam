import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @PACKAGE_NAME: PACKAGE_NAME
 * @NAME: BaseTest
 * @Author: 钟子豪
 * @DATE: 2020/6/18
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: ssm
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
//@ContextConfiguration("classpath:applicationContext.xml")
public class BaseTest {
  /*  @Autowired
    org.apache.shiro.mgt.SecurityManager securityManager;

    @Autowired
    WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    private void login(String username, String password) {

        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        final Subject subject = SecurityUtils.getSubject();

        subject.login(token);
    }

    @Before
    public void before() {
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        login("wadewhy", "123456");
    }
*/
}

