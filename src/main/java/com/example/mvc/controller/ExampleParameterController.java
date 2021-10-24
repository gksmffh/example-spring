package com.example.mvc.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/example/parameter")
public class ExampleParameterController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/example1")
	public void example1(@RequestParam String id, @RequestParam String code, Model model) {
		model.addAttribute("id",id);
		model.addAttribute("code",code);
	}
	
	@GetMapping("/example2")
	public void example2(@RequestParam Map<String, Object> paraMap, Model model) {
		model.addAttribute("paraMap", paraMap);
		
	}
	@GetMapping("/example3")
	public void example3(ExampleParameter parameter, Model model) { //클래스로 받을경우 @RequestParam안씀
		model.addAttribute("parameter", parameter);
		
	}
	
	@GetMapping("/example4/{id}/{code}") //@pathvariable사용할 경우 뷰페이지 리턴해줘야됨
	public String example4(@PathVariable String id,@PathVariable String code, Model model) { 
		model.addAttribute("id", id);
		model.addAttribute("code", code);

		return "/example/parameter/example4";
	}
	
	@GetMapping("/example5") 
	public void example5(@RequestParam String[] ids, Model model) { 
		model.addAttribute("ids", ids);
		
	}
	
	@GetMapping("/example6/form") 
	public void form() { 
		
	}
	
	@PostMapping("/example6/saveData") 
	@ResponseBody
	public Map<String, Object> example6(@RequestBody ExampleRequestBodyUser requestBody) { 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		logger.info("requestBody: {}", requestBody);
		return resultMap;
	}
}
