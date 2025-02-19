package spring.boot.demo.datasources;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "spring.boot.demo.dao.two", sqlSessionFactoryRef = "twoSqlSessionFactory")
public class TwoDataSoruceConfig {

	@Autowired
	private Environment environment;

	@Bean(name = "twoDataSoruce")
	@ConfigurationProperties("spring.datasource.two")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "twoSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("twoDataSoruce") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/two/*.xml"));
		if (isTestEnvironment()) {
			org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
			configuration.setLogImpl(StdOutImpl.class);
			sessionFactoryBean.setConfiguration(configuration);
		}
		return sessionFactoryBean.getObject();
	}

	@Bean("twoSqlSessionTemplate")
	public SqlSessionTemplate test2sqlsessiontemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}

	public boolean isTestEnvironment() {
		String activeProfile = environment.getProperty("spring.profiles.active");
		System.out.println("Active profile: " + activeProfile);
		if(activeProfile.contains("test") || activeProfile.contains("dev")) {
			return true;
		}
		return false;
	}
}
