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
package fr.recia.mediacentre.api;

import fr.recia.mediacentre.api.web.rest.HealthCheckController;
import fr.recia.mediacentre.api.web.rest.MediaCentreExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@Slf4j
@ActiveProfiles("test")
@WebMvcTest
public class HealthCheckControllerTest {
  private static final String HEALTHCHECK_URI = "/health-check";

  private MockMvc mockMvc;

  @Before
  public void setup() {
    HealthCheckController healthCheck = new HealthCheckController();

    mockMvc = MockMvcBuilders
      .standaloneSetup(healthCheck)
      .setControllerAdvice(new MediaCentreExceptionHandler())
      .build();
  }

  @Test
  public void healthCheck_OK() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .get(HEALTHCHECK_URI)
      .characterEncoding(StandardCharsets.UTF_8);

    MvcResult result = mockMvc
      .perform(requestBuilder)
      .andReturn();

    assertEquals(HttpStatus.SC_OK, result.getResponse().getStatus());
  }
}
