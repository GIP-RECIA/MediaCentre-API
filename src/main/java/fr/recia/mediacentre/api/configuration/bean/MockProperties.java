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
package fr.recia.mediacentre.api.configuration.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Configuration
@ConfigurationProperties(
  prefix = "mock",
  ignoreUnknownFields = false
)
@Data
@Validated
@Slf4j
public class MockProperties {

  @NotNull
  int status;

  String mockedDataLocation;

  String mockedDTOLocation;

  @PostConstruct
  private void init() throws JsonProcessingException {

    if(status > 0){
      Assert.isTrue(Objects.nonNull(mockedDataLocation), String.format("Mock status is %s but mockedDataLocation is null", status));
    }
    if(status > 0){
      Assert.isTrue(!mockedDataLocation.trim().isEmpty(), String.format("Mock status is %s but mockedDataLocation is empty", status));
    }
    if(status == 2){
      Assert.isTrue(Objects.nonNull(mockedDTOLocation), String.format("Mock status is %s but mockedDTOLocation is null", status));
    }
    if(status == 2){
      Assert.isTrue(!mockedDTOLocation.trim().isEmpty(), String.format("Mock status is %s but mockedDTOLocation is empty", status));
    }
    log.info("Mock properties: {}", this);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n\tstatus: ");
    stringBuilder.append(status);
    stringBuilder.append("\n");
    stringBuilder.append("\tmockedDataLocation: ");
    stringBuilder.append(mockedDataLocation);
    stringBuilder.append("\n");
    stringBuilder.append("\tmockedDTOLocation: ");
    stringBuilder.append(mockedDTOLocation);
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }


}
