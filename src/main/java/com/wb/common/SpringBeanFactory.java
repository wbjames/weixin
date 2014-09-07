/**
 * 
 */
package com.wb.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wb_james
 *
 */
public class SpringBeanFactory implements ApplicationContextAware {
	private static ApplicationContext context;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringBeanFactory.context = applicationContext;
	}
	
	
	/**
	 * 返回Spring的上下文
	 * @return
	 */
	public static ApplicationContext getBeanFactory() {
		return SpringBeanFactory.context;
	}
	
	/**
	 * 返回Bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return context != null ? SpringBeanFactory.context.getBean(beanName) : null ;
	}
	
}
