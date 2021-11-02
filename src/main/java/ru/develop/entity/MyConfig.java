package ru.develop.entity;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MyConfig {
    /*@Bean
    public DataSource dataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/my_storage?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("postgres");
            dataSource.setPassword("Dune1488");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }*/

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/my_storage?useSSL=false&serverTimezone=UTC");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("Dune1488");
        return dataSourceBuilder.build();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.develop.entity");

        Properties hibernateProps = new Properties();
        hibernateProps.setProperty("hibernate.dialects","org.hibernate.dialect.PostgreSQLDialect");
        hibernateProps.setProperty("hibernate.show_sql","true");

        sessionFactory.setHibernateProperties(hibernateProps);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());

        return transactionManager;
    }
}
