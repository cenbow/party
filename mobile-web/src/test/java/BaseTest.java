import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
//指定bean注入的配置文件
@ContextConfiguration(locations = { "classpath:spring.xml","classpath:spring-dao.xml","classpath:spring-redis.xml" })
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TestExecutionListeners(listeners={DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
