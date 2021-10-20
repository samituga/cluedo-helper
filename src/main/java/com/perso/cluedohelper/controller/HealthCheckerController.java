package com.perso.cluedohelper.controller;

import com.perso.cluedohelper.appinfo.AppInfoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthCheckerController {

	private final AppInfoProperties appInfoProperties;

	@GetMapping
	public Health health() { // TODO: 2021-10-20 Also check if redis is up
		return Health
			.up()
			.withDetail("version", appInfoProperties.getVersion())
			.build();
	}
}
