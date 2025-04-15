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
package fr.recia.mediacentre.api.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jgribonvald on 13/09/16.
 */

@Slf4j
@Service
public class MediaCentreResourceJacksonImpl implements MediaCentreResource {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SoffitHolder soffitHolder;

    @Autowired
    private MappingProperties mappingProperties;


    public List<Ressource> retrieveListRessource(String mediaUrl,Map<String, List<String>> userInfos) throws MediacentreWSException{
        return this.getServiceMediaCentre(mediaUrl,userInfos, userInfos.get(mappingProperties.getGarId()).get(0));
    }

  @Cacheable(cacheNames = "userResourcesCache", key = "#userId")
    private List<Ressource> getServiceMediaCentre(String url,Map<String, List<String>> userInfos, String userId) throws MediacentreWSException {
        if (log.isDebugEnabled()) {
        log.debug("Requesting mediacentre on URL {}", url );
    }
        List<Ressource> listRessourceMediaCentre = null;
        ObjectMapper objectMapper = new ObjectMapper();

        Cache cache = cacheManager.getCache("userResourcesCache");

        try{
          List<Ressource> userResources = (List<Ressource>) cache.get(soffitHolder.getSub(), List.class);
          if(!Objects.isNull(userResources)){
            return userResources;
          }
        }catch (Exception ignored){
          log.warn("Unable to read cache");
        }

        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Map<String, List<String>>> requestEntity = new HttpEntity<Map<String, List<String>>>(userInfos, requestHeaders);
            ResponseEntity<Ressource[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Ressource[].class);
            listRessourceMediaCentre = Lists.newArrayList(response.getBody());

          try {
            cache.putIfAbsent(soffitHolder.getSub(),listRessourceMediaCentre);
          } catch (Exception e) {
            log.warn("Unable to write cache");
          }

        } catch (HttpClientErrorException e) {
            // providing the error stacktrace only on debug as the custom logged error should be suffisant.
            log.warn("Error client request on URL {}, returned status {}, with response {}", url, e.getStatusCode(), e.getResponseBodyAsString(),e);
            throw new MediacentreWSException(e.getMessage(), e.getStatusCode());
        } catch (RestClientException ex) {
            log.warn("Error getting MediaCentre from url '{}'", url, ex.getLocalizedMessage(), ex);
            return Lists.newArrayList();
        } catch (HttpMessageNotReadableException ex) {
            log.warn("Error getting MediaCentre from url '{}' the object doesn't map MediaCentre Object properties with a such response {}", url, ex.getLocalizedMessage(), ex);
            return Lists.newArrayList();
        }

        return listRessourceMediaCentre;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}

