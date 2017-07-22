package org.sagittarius.web.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@RequestMapping("/demo.do")
	@ResponseBody
	public void demo() {
		
	}

}
