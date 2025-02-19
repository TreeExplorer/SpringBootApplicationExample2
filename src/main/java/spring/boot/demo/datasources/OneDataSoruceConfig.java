package spring.boot.demo.datasources;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "spring.boot.demo.dao.one", sqlSessionFactoryRef = "oneSqlSessionFactory")
public class OneDataSoruceConfig {

	@Bean(name = "oneCenterDataSoruce")
	@ConfigurationProperties("spring.datasource.one")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "oneSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("oneCenterDataSoruce") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/one/*.xml"));
		return sessionFactoryBean.getObject();
	}

	@Bean("oneSqlSessionTemplate")
	public SqlSessionTemplate test2sqlsessiontemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
