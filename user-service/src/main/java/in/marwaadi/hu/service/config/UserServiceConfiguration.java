package in.marwaadi.hu.service.config;

import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for User-Management Service
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "in.marwaadi.hu.service.repository")
@EntityScan(basePackages = "in.marwaadi.hu.service.entity")
public class UserServiceConfiguration {

	/**
	 * Create Bean for {@link ModelMapper} if not found in
	 * {@link ApplicationContext}
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper;
	}

	/**
	 * Create Bean for {@link PasswordEncoder} if not found in
	 * {@link ApplicationContext}
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Create Bean for {@link Validator} if not found in {@link ApplicationContext}
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	/**
	 * Create Bean for {@link MessageSource} if not found in
	 * {@link ApplicationContext}
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(3600);
		return messageSource;
	}

}
