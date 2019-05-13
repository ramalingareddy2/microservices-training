package com.pyro.cvps.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig {
	

	
	
	@Bean
    public ThreadPoolTaskExecutor threadPoolTaskScheduler(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(30);
		taskExecutor.setMaxPoolSize(40);
		taskExecutor.setQueueCapacity(50);
		taskExecutor.setThreadNamePrefix("ThreadPoolTask");
        return taskExecutor;
    }
	
	
	@Bean(name="conf")
	public PropertiesConfiguration dynamicConfiguration(){
		PropertiesConfiguration conf = null;
		try {
			conf = new PropertiesConfiguration();
			conf.setDelimiterParsingDisabled(true);
			conf.load("./config/application.properties");
			conf.setReloadingStrategy(new FileChangedReloadingStrategy());
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}		
		return conf;
	}
	
//	@Bean(name="circleconf")
//	public PropertiesConfiguration dynamicCircleConfiguration(){
//		PropertiesConfiguration circleNamesconfiguration = null;
//		try {
//			circleNamesconfiguration = new PropertiesConfiguration();
//			circleNamesconfiguration.setDelimiterParsingDisabled(true);
//			circleNamesconfiguration.load("./config/circle_name_config.properties");
//			circleNamesconfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());			
//		} catch (ConfigurationException e) {
//			e.printStackTrace();
//		}
//		return circleNamesconfiguration;
//	}
	

}


