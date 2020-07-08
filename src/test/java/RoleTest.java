import org.apache.log4j.Logger;
import org.junit.Test;
import xyz.wadewhy.after.sys.service.RoleService;
import javax.annotation.Resource;
import java.util.List;

/**
 * @PACKAGE_NAME: PACKAGE_NAME
 * @NAME: RoleTest
 * @Author: 钟子豪
 * @DATE: 2020/6/16
 * @MONTH_NAME_FULL: 六月
 * @DAY: 16
 * @DAY_NAME_FULL: 星期二
 * @PROJECT_NAME: ssm
 **/
public class RoleTest extends BaseTest{
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(RoleTest.class);

  /*  @Resource
    private UserService userService;
    @Resource
    private IUserDao iUserDao;
    */
  @Resource
  private RoleService roleService;
//    @Test
    public void findAll() {
        List<Integer> list = roleService.queryRoleIdsByUserId(1);
        for (Integer i:list
             ) {
            System.err.println(i+"---------------------");
        }
    }
//    @Test
    public void logTest2(){
        logger.trace("Trace 日志...");
        logger.debug("Debug 日志...");
        logger.info("Info 日志...");
        logger.warn("Warn 日志...");
        logger.error("Error 日志...");

    }
}
