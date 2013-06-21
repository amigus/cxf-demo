package org.migus.web.content.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContentController {

	@RequestMapping(value = {"/", "index.html" }, method = RequestMethod.GET)
	public String  index() { return "index"; }
}
