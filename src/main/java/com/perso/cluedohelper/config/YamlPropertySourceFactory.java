package com.perso.cluedohelper.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Enables the possibility to use YAML files with {@link PropertySource}
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

	// TODO: 2021-10-20 Is this good enough? 

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
		YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
		factory.setResources(encodedResource.getResource());

		Properties properties = factory.getObject();

		return new PropertiesPropertySource(
			Objects.requireNonNull(encodedResource.getResource().getFilename()),
			Objects.requireNonNull(properties));
	}
}
