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
package fr.recia.mediacentre.api.service.config.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fr.recia.mediacentre.api.configuration.bean.ConfigProperties;
import fr.recia.mediacentre.api.model.pojo.ConfigElement;
import fr.recia.mediacentre.api.model.pojo.ParamUserEtabsResponseWrapper;
import fr.recia.mediacentre.api.service.config.ConfigService;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {


  ConfigProperties configProperties;

  @Autowired
  private RestTemplate restTemplate;


  private Map<String, String> etabUaiEtabNameMap = new HashMap<>();

  public ConfigServiceImpl(ConfigProperties configProperties){
    this.configProperties = configProperties;
  }

  @Override
  public List<ConfigElement> getEtabsNames(List<String> uais) {


    List<ConfigElement> configElementList = new ArrayList<>(uais.size());

    List<String> uaisToRequest = new ArrayList<>(uais.size());

    for (String uai: uais){
      if(etabUaiEtabNameMap.containsKey(uai)){
        configElementList.add(new ConfigElement(uai, etabUaiEtabNameMap.get(uai)));
      }else {
        uaisToRequest.add(uai);
      }
    }

    if(!uaisToRequest.isEmpty()){
      List<ConfigElement> configElementRequested = requestEtabsNames(uaisToRequest);

      for (ConfigElement configElement: configElementRequested){
        etabUaiEtabNameMap.put(configElement.getKey(), configElement.getValue());
        log.info(configElement.getValue());
      }
      configElementList.addAll(configElementRequested);
    }




    return configElementList;
  }

  @Override
  public List<ConfigElement> getGroups() {
    List<ConfigElement> groupsConfigElementList = new ArrayList<ConfigElement>();
    for (String groupValue : configProperties.getGroups()){
      groupsConfigElementList.add(new ConfigElement("groups", groupValue));
    }
    return groupsConfigElementList;
  }


  @NoArgsConstructor
  @Getter
  @Setter
  private class ResponseStructureWrapper{

    @JsonFormat(shape = JsonFormat.Shape.ANY)
    Map<String, Object> map;

  }

  private class ResponseStructure{

    String string;
    Map<String, Object> map;

  }


  private List<ConfigElement> requestEtabsNames(List<String> uais){


    log.warn("request etabs names");
    StringBuilder stringURI = new StringBuilder( configProperties.getParamUserEtabsURl());
    stringURI.append(String.join(",", uais));
    URI uri = null;
    try {
      uri = new URL(stringURI.toString()).toURI();
    } catch (MalformedURLException | URISyntaxException e) {
      log.error("exception when creating uri:", e);
      throw new RuntimeException(e);
    }

    try {
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Map<String, List<String>>> requestEntity = new HttpEntity<>(null, requestHeaders);

      ResponseEntity<ParamUserEtabsResponseWrapper> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ParamUserEtabsResponseWrapper.class);

      List<ConfigElement> configElementList = new ArrayList<>();
        if (Objects.nonNull(response.getBody())) {
            for(Map.Entry<String, JsonNode> entry : response.getBody().getMap().entrySet()){
              JsonNode jsonNode = entry.getValue();
              JsonNode displayNameJsonNode =jsonNode.get(configProperties.getParamUserEtabsDisplayNameKey());
              configElementList.add(new ConfigElement(entry.getKey(), displayNameJsonNode.asText()));
            }
        }
        return configElementList;
    } catch (HttpClientErrorException e) {
      // providing the error stacktrace only on debug as the custom logged error should be suffisant.
      log.warn("Error client request on URL {}, returned status {}, with response {}", uri, e.getStatusCode(), e.getResponseBodyAsString(),e);
      log.warn(e.getStatusCode().toString());
      throw new MediacentreWSException(e.getMessage(), e.getStatusCode());
    } catch (RestClientException ex) {
      log.warn("Error getting MediaCentre from url '{}'", uri, ex.getMessage(), ex);
      return Lists.newArrayList();
    } catch (HttpMessageNotReadableException ex) {
      log.warn("Error getting MediaCentre from url '{}' the object doesn't map MediaCentre Object properties with a such response {}", uri, ex.getLocalizedMessage(), ex);
      return Lists.newArrayList();
    }
  }

}
