package com.party.admin.basetest;

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
 * 测试基础类
 * @author wei.li
 * @version 2016/8/1
 */

//指定bean注入的配置文件
@ContextConfiguration(locations = { "classpath:spring-dao.xml","classpath:spring.xml", "classpath:spring-jms.xml" })
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TestExecutionListeners(listeners={DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class BaseTest extends AbstractJUnit4SpringContextTests{
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
