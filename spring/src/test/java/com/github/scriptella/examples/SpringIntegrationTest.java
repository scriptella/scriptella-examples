package com.github.scriptella.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scriptella.driver.spring.EtlExecutorBean;
import scriptella.execution.EtlExecutorException;

/**
 * Just a smoke test which calls populateDb executor registered as a Spring bean.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml"})
public class SpringIntegrationTest {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test() throws EtlExecutorException {
        //Executes populate-db.etl.xml job
        //DB schema has already been created in create-db.etl.xml at context initialization time
        EtlExecutorBean executorBean = applicationContext.getBean("populateDb", EtlExecutorBean.class);
        executorBean.execute();
    }
}
