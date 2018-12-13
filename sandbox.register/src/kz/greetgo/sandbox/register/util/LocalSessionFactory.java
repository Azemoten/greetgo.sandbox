package kz.greetgo.sandbox.register.util;

import kz.greetgo.db.DbProxyFactory;
import kz.greetgo.db.GreetgoTransactionManager;
import kz.greetgo.db.TransactionManager;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.sandbox.controller.model.UserCan;
import kz.greetgo.sandbox.register.util.my_batis.CustomBooleanTypeHandler;
import kz.greetgo.sandbox.register.util.my_batis.CustomEnumTypeHandler;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

public abstract class LocalSessionFactory implements BeanReplacer, HasAfterInject,
    DataSourceGetter {

  private final TransactionManager transactionManager = new GreetgoTransactionManager();
  private final TransactionFactory transactionFactory = new LocalTransactionFactory(
      transactionManager);
  private final DbProxyFactory dbProxyFactory = new DbProxyFactory(transactionManager);

  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {

    if (!returnClass.isInterface()) {
      return originalBean;
    }

    return dbProxyFactory.createProxyFor(originalBean, returnClass);
  }

  @Bean
  public JdbcSandbox getJdbcSandbox() {
    return jdbcSandbox;
  }

  private JdbcSandbox jdbcSandbox = null;

  private SqlSessionFactory sqlSessionFactory = null;

  protected abstract DataSource createDataSource() throws Exception;

  protected abstract String databaseEnvironmentId();

  private DataSource dataSource;

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public void afterInject() throws Exception {
    dataSource = createDataSource();

    dataSource = DbLoggingProxyFactory
        .create(dataSource, new DbLoggingProxyFactory.AbstractSqlViewer() {
          final Logger logger = Logger.getLogger("DIRECT_SQL");

          @Override
          protected void logTrace(String message) {
            if (logger.isTraceEnabled()) {
              logger.trace(message);
            }
          }
        });

    jdbcSandbox = new JdbcSandbox(dataSource, transactionManager);

    Environment environment = new Environment(databaseEnvironmentId(), transactionFactory,
                                              dataSource);

    Configuration configuration = new Configuration(environment);
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    configuration.setLogImpl(Log4jImpl.class);

    configuration.setMapUnderscoreToCamelCase(true);

    TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

    typeHandlerRegistry.register(Boolean.class, new CustomBooleanTypeHandler());
    typeHandlerRegistry.register(boolean.class, new CustomBooleanTypeHandler());
    typeHandlerRegistry.register(JdbcType.BOOLEAN, new CustomBooleanTypeHandler());
    typeHandlerRegistry.register(UserCan.class, new CustomEnumTypeHandler<>(UserCan.class));

    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);
  }


  public Configuration getConfiguration() {
    return sqlSessionFactory.getConfiguration();
  }

  public SqlSession sqlSession() {
    return sqlSessionFactory.openSession(true);
  }
}
