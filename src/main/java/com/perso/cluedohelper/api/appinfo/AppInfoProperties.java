package com.perso.cluedohelper.api.appinfo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppInfoProperties {

	@NotBlank
	private String version;
}
