/**
 * Copyright © 2016 ESUP-Portail (https://www.esup-portail.org/)
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
package org.esco.portlet.mediacentre.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.esco.portlet.mediacentre.dao.IMediaCentreResource;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

/**
 * Created by jgribonvald on 13/09/16.
 */
public class MediaCentreResourceJacksonImpl implements IMediaCentreResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(cacheNames = "listeRessourcesMedia", key = "#mediaUrl")
    public List<Ressource> retrieveListRessource(String mediaUrl, PortletRequest request, Map<String, List<String>> userInfos) {
        return this.getServiceMediaCentre(mediaUrl, request, userInfos);
    }

    private List<Ressource> getServiceMediaCentre(String url, PortletRequest request, Map<String, List<String>> userInfos) {
        if (log.isDebugEnabled()) {
            log.debug("Requesting mediacentre on URL {}", url );
        }

        List<Ressource> listRessourceMediaCentre = new ArrayList<>();

        try {
            //listRessourceMediaCentre = Lists.newArrayList(restTemplate.getForObject(url, Ressource[].class));
        	
        	HttpHeaders requestHeaders = new HttpHeaders();
        	requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        	HttpEntity<Map<String, List<String>>> requestEntity = new HttpEntity<Map<String, List<String>>>(userInfos, requestHeaders);
        	
        	ResponseEntity<Ressource[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Ressource[].class);
        	
        	listRessourceMediaCentre = Lists.newArrayList(response.getBody());
        	
        } catch (RestClientException ex) {
            log.warn("Error getting MediaCentre from url '{}'", url, ex);
            return Lists.newArrayList();
        } catch (HttpMessageNotReadableException ex) {
            log.warn("Error getting MediaCentre from url '{}' the object doesn't map MediaCentre Object properties", url, ex);
            return Lists.newArrayList();
        }

        return listRessourceMediaCentre;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
