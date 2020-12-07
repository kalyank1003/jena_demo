package com.vachan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vachan.service.InferenceService;

/**
 * 
 * @author kalyan
 *
 */

@Controller
@RequestMapping("/")
public class TestController {

	@Autowired
	@Qualifier("infService")
	private InferenceService infService;

	@RequestMapping(value = "/loadSingleNode", method = RequestMethod.GET)
	public String loadSingleNode() throws Exception {
		infService.loadSingle();
		return "Completed";
	}

	@RequestMapping(value = "/load500Nodes", method = RequestMethod.GET)
	public String load500Node() throws Exception {
		infService.load500Nodes();
		return "Completed";
	}

	@RequestMapping(value = "/load2500Nodes", method = RequestMethod.GET)
	public String load2500Node() throws Exception {
		infService.load2500Nodes();
		return "Completed";
	}

	@RequestMapping(value = "/load10KNodes", method = RequestMethod.GET)
	public String load10KNode() throws Exception {
		infService.load10kNodes();
		return "Completed";
	}
}
