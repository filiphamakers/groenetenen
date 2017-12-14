package be.vdab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.WerknemerService;

@Controller
@RequestMapping("/werknemers")
public class WerknemerController {
	// VIEWS
	private static final String WERKNEMERS_VIEW = "werknemers/werknemers";
	// SERVICES
	private final WerknemerService werknemerService;

	WerknemerController(WerknemerService werknemerService) {
		this.werknemerService = werknemerService;
	}

	@GetMapping
	ModelAndView findAll() {
		return new ModelAndView(WERKNEMERS_VIEW, "werknemers", werknemerService.findAll());

	}

}
