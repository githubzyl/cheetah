package top.zylsite.cheetah.web.backstage.configuation;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * Description: 数据源配置，如果有其他数据源，则可按照该文件配置
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
@Configuration
@tk.mybatis.spring.annotation.MapperScan(basePackages = "top.zylsite.cheetah.backstage.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MasterDataSourceConfiguration {

	private final static String MASTER_MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	private final static String MASTER_MAPPER_LOCATION = "classpath:mybatis/mapper/master/*Mapper.xml";
	
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Primary
    public DataSource setDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public PlatformTransactionManager setTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
    	SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(masterDataSource);
		factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(MASTER_MYBATIS_CONFIG));
		factoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MASTER_MAPPER_LOCATION));
        return factoryBean.getObject();
    }

    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory masterSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(masterSqlSessionFactory);
    }
}