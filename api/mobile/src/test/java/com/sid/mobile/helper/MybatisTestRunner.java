package com.sid.mobile.helper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.api.ServiceLocatorListener;
import org.glassfish.jersey.internal.inject.Injections;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.mybatis.guice.XMLMyBatisModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

public class MybatisTestRunner extends BlockJUnit4ClassRunner {
    public MybatisTestRunner(Class<?> klass) throws InitializationError {
        super(klass);

        Injector injector = Guice.createInjector(
                new XMLMyBatisModule() {
                    @Override
                    protected void initialize() {
                        setEnvironmentId("development");
                        setClassPathResource("mybatis/mybatis-config.xml");
                    }

                }
        );

        ServiceLocatorFactory.getInstance().addListener(new ServiceLocatorListener() {
            @Override
            public void initialize(Set<ServiceLocator> initialLocators) {
//                initialLocators.stream().forEach(serviceLocator -> bridge(serviceLocator, injector));
            }

            @Override
            public void locatorAdded(ServiceLocator added) {
                bridge(added, injector);
            }

            @Override
            public void locatorDestroyed(ServiceLocator destroyed) {

            }
        });
    }

    private void bridge(ServiceLocator serviceLocator, Injector injector) {
        getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> rules = new ArrayList<>();
        rules.add(rollbackSessionManager);
        rules.addAll(super.getTestRules(target));
        return rules;
    }

    private final TestRule rollbackSessionManager = (base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            SqlSessionManager sqlSessionManager = Injections.createLocator().getService(SqlSessionManager.class);
            sqlSessionManager.startManagedSession();
            try {
                base.evaluate();
            } finally {
                try {
                    sqlSessionManager.rollback(true);
                } finally {
                    sqlSessionManager.close();
                }
            }
        }
    };

}
