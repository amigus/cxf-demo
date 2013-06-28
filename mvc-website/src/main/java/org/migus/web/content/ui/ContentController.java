package org.migus.web.content.ui;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContentController {
	private String origins="http://localhost:8081";

	@RequestMapping(value = {"/", "index.html" }, method = RequestMethod.GET)
	public String  index(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Credentials", "false");
		response.addHeader("Access-Control-Allow-Origin", origins);
		return "index";
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
    public void options(HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Credentials", "false");
		response.addHeader("Access-Control-Allow-Methods",
				"DELETE, GET, OPTIONS, POST, PUT");
		response.addHeader("Access-Control-Allow-Origin", origins);
    }
}