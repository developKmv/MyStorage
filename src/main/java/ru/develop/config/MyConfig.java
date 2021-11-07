package ru.develop.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.develop.controller.AppController;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
public class MyConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;/*= "jdbc:postgresql://localhost:5432/my_storage?useSSL=false&serverTimezone=UTC";*/

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("Dune1488");
        return dataSourceBuilder.build();
    }

  /*  @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/my_storage?useSSL=false&serverTimezone=UTC");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("Dune1488");
        return dataSourceBuilder.build();
    }*/
/*
    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
*/

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
