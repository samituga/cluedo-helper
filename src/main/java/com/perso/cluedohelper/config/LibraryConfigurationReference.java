package com.perso.cluedohelper.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Scans components from cluedohelperlibrary.
 */
@Configuration
@ComponentScan("io.github.samituga.cluedohelperlibrary.config")
public class LibraryConfigurationReference {}