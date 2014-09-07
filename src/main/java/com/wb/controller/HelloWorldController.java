package com.wb.controller;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wb.jpa.DataAdpter;

@SuppressWarnings("rawtypes")
@Controller
public class HelloWorldController {
	String message = "Welcome to Spring MVC!";
	 
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller");
 
		
		
		ModelAndView mv = new ModelAndView("helloworld");
		
		DataAdpter da = new DataAdpter();
		JdbcTemplate jt = da.getJdbcTemplate();
		Map m = jt.queryForMap("select * from wx_user where userid = 'wb_james'");
		if(m != null){
			mv.addObject("name", m.get("username"));
			System.out.println(m.get("username"));
		}
		
		mv.addObject("message", message);
		
		return mv;
	}

}