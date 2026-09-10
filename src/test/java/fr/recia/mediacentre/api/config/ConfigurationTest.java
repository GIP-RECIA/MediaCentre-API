/**
 * Copyright © 2017 GIP-RECIA (https://www.recia.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.recia.mediacentre.api.config;

import fr.recia.mediacentre.api.configuration.bean.ConfigProperties;
import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import org.mockito.Mockito;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
@EnableConfigurationProperties(ConfigProperties.class)
public class ConfigurationTest {
  @Primary
  @Bean(name = "mockTestMediaCentreRessource")
  public MediaCentreResource mediaCentreResource() {
    return Mockito.mock(MediaCentreResourceJacksonImpl.class);
  }

  @Bean
  @Scope(
    value = WebApplicationContext.SCOPE_REQUEST,
    proxyMode = ScopedProxyMode.TARGET_CLASS
  )
  public SoffitHolder soffitHolder() {
    return new SoffitHolder();
  }
}
