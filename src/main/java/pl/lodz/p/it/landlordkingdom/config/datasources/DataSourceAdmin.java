package pl.lodz.p.it.landlordkingdom.config.datasources;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryAdmin",
        transactionManagerRef = "adminTransactionManager",
        basePackages = {
                "pl.lodz.p.it.landlordkingdom.mok.repositories",
                "pl.lodz.p.it.landlordkingdom.mok.authRepositories",
                "pl.lodz.p.it.landlordkingdom.mol.repositories"
        }
)
public class DataSourceAdmin {


    @Value("${transaction.timeout}")
    private int txTimeout;

    @Bean
    @ConfigurationProperties("admin.datasource")
    @Primary
    public DataSource adminDataSource() {
        return new HikariDataSource();
    }


    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        return vendorAdapter;
    }


    @Bean(name = "entityManagerFactoryAdmin")
    public EntityManagerFactory entityManagerFactoryAdmin(@Qualifier("adminDataSource") DataSource dataSource,
                                                          JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("landlordkingdomadmin");
        em.setPackagesToScan("pl.lodz.p.it.landlordkingdom.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("jakarta.persistence.transactionType", "RESOURCE_LOCAL");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("jakarta.persistence.sql-load-script-source", "init.sql, init2.sql");
        properties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.schema.internal.script.MultiLineSqlScriptExtractor");
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean(name = "adminTransactionManager")
    public PlatformTransactionManager adminTransactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager(entityManagerFactory);
        transactionManager.setDefaultTimeout(txTimeout);
        return transactionManager;
    }
}
