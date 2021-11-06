package com.perso.cluedohelper.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration class for redis related stuff.
 */
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean(destroyMethod = "shutdown")
  ClientResources clientResources() {
    return DefaultClientResources.create();
  }

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    return new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
  }

  @Bean
  ClientOptions clientOptions() {
    return ClientOptions.builder()
        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
        .autoReconnect(true)
        .build();
  }

  @Bean
  LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options,
                                                      ClientResources clientResources) {
    return LettucePoolingClientConfiguration.builder()
        .poolConfig(new GenericObjectPoolConfig())
        .clientOptions(options)
        .clientResources(clientResources)
        .build();
  }

  @Bean
  RedisConnectionFactory connectionFactory(
      RedisStandaloneConfiguration redisStandaloneConfiguration,
      LettucePoolingClientConfiguration lettucePoolConfig) {
    return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
  }

  /**
   * Provides a default {@link RedisTemplate}.
   *
   * @param redisConnectionFactory {@link #connectionFactory(RedisStandaloneConfiguration,
   *                               LettucePoolingClientConfiguration) connectionFactory}
   * @return the default template
   */
  @Bean
  @Primary
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
