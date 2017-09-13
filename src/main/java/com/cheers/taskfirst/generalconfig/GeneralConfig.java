package com.cheers.taskfirst.generalconfig;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {

	@Bean
	public DozerBeanMapper beanMapper(){
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper(Arrays.asList(new String[]{"dozer_mapping.xml"}));
		return dozerBeanMapper;
	}

	
}
