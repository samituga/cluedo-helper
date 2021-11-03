package com.perso.cluedohelper.api.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

/**
 * Enables the use of YAML files with {@link PropertySource}
 * by referencing this class in the factory attribute
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

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
