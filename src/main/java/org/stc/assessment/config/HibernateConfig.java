package org.stc.assessment.config;

import liquibase.integration.spring.SpringLiquibase;

import javax.sql.DataSource;
import java.util.Properties;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "org.stc.assessment",
        transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class HibernateConfig {

    private static final String[] ENTITY_MANAGER_PACKAGES_TO_SCAN =
            {"org.stc.assessment"};

    private final Environment env;

    public HibernateConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {

        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        String driverClass = env.getProperty("spring.datasource.driverClassName");
        String url = env.getProperty("spring.datasource.url");

        return DataSourceBuilder.create().username(username).password(password).url(url)
                .driverClassName(driverClass).build();
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean
                .setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());

        return entityManagerFactoryBean;
    }

    private Properties addProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
        properties.setProperty("hibernate.show_sql",
                env.getProperty("spring.jpa.show-sql", "false"));
        properties.setProperty("hibernate.dialect",
                env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.format_sql", env.getProperty(
                "spring.jpa.properties.hibernate.format_sql", "false"));
        return properties;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog-master.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

}
