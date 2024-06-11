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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.IsMemberOf;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.impl.MediaCentreServiceImpl;
import fr.recia.mediacentre.api.web.rest.MediaCentreController;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MediaCentreController.class)
@DirtiesContext
@AutoConfigureMockMvc(addFilters = false)
public class MediaCentreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaCentreServiceImpl mediaCentreService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Ressource> listeRessourcesMediaCentre;
    private List<FilterEnum> lesFiltres;
    private String isMemberOf;

    private IsMemberOf isMemberOfObject;

    private static String GETRESOURCES_URI = "/api/resources";
    private static String GETFILTERS_URI = "/api/resources/filters";

    private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJodHRwczovL3Rlc3QtbHljZWUuZ2lwcmVjaWEubmV0L3BvcnRhaWwiLCJzdWIiOiJGMjAwMGh0cSIsImF1ZCI6Imh0dHBzOi8vdGVzdC1seWNlZS5naXByZWNpYS5uZXQvcG9ydGFpbCIsImV4cCI6MTcxNDczNjczNiwiaWF0IjoxNzE0NzM2NDM2LCJwcm9maWxlIjoiTmF0aW9uYWxfRU5TIiwiRVNDT1NJUkVOQ291cmFudCI6WyIwMDAwMDAwMDAwMDAwMSJdLCJFTlRQZXJzb25HQVJJZGVudGlmaWFudCI6WyJlN2MyNDlhOS1jZDYzLTQxMzYtYmQ2MS1mMzViNmMwMDE1YzUiXSwiRVNDT1NJUkVOIjpbIjAwMDAwMDAwMDAwMDAxIl19.0Wsymge34QAMcXqj6K20j6iBMUCwYy2pvz5IVsnoY8QI-0Z1Zri7KN36LxsmzgtTJYI26jVf2fYj_NRvrwEh-Q";

    @Before
    public void init() throws IOException {
        listeRessourcesMediaCentre = objectMapper.readValue(new File("src/test/resources/json/resources-example.json"),new TypeReference<>(){});
        lesFiltres = objectMapper.readValue(new File("src/test/resources/json/filters.json")
                ,new TypeReference<>(){});
        File isMemberOfFile = new File("src/test/resources/json/isMemberOf.json");
        isMemberOf = Files.readString(isMemberOfFile.toPath());
        isMemberOfObject = objectMapper.readValue(isMemberOf, new TypeReference<>() {
        });
    }

//     getResources() tests :
    @Test
    public void getResources_OK() throws Exception, YmlPropertyNotFoundException {

        this.listeRessourcesMediaCentre = objectMapper.readValue(new File("src/test/resources/json/resources-example.json")
                ,new TypeReference<>(){});

        Mockito.when(mediaCentreService.retrieveListRessource(isMemberOfObject.getIsMemberOf())).thenReturn(listeRessourcesMediaCentre);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GETRESOURCES_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(isMemberOf);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(result.getResponse().getStatus(), HttpStatus.SC_OK);

        List<Ressource> retrievedResources = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(retrievedResources.size(), listeRessourcesMediaCentre.size());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(listeRessourcesMediaCentre), result.getResponse().getContentAsString(StandardCharsets.UTF_8), false);
    }

    @Test
    public void getResources_When_No_resource_OK() throws Exception, YmlPropertyNotFoundException {
        Mockito.when(mediaCentreService.retrieveListRessource(isMemberOfObject.getIsMemberOf())).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GETRESOURCES_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(isMemberOf);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.SC_OK);
        assertEquals(0, result.getResponse().getContentLength());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(new ArrayList<>()), result.getResponse().getContentAsString(StandardCharsets.UTF_8), false);
    }

    @Test
    public void getResources_When_No_Token_KO() throws Exception, YmlPropertyNotFoundException {
        Mockito.when(mediaCentreService.retrieveListRessource(isMemberOfObject.getIsMemberOf())).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GETRESOURCES_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(isMemberOf);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.SC_UNAUTHORIZED, result.getResponse().getStatus());
        assertEquals(0, result.getResponse().getContentLength());
    }




//     getFilters() tests :
    @Test
    public void getFilters_OK() throws Exception {


        Mockito.when(mediaCentreService.retrieveFiltersList()).thenReturn(lesFiltres);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GETFILTERS_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.SC_OK);

        List<FilterEnum> retrievedFilters = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(lesFiltres.size(),retrievedFilters.size());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(lesFiltres), result.getResponse().getContentAsString(StandardCharsets.UTF_8), false);
    }

    @Test
    public void getFilters_When_No_Token_KO() throws Exception {
        Mockito.when(mediaCentreService.retrieveFiltersList()).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GETFILTERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.SC_UNAUTHORIZED, result.getResponse().getStatus());
        assertEquals(0, result.getResponse().getContentLength());
    }

    @Test
    public void getFilters_When_No_Filters_OK() throws Exception {
        Mockito.when(mediaCentreService.retrieveFiltersList()).thenReturn(lesFiltres);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GETFILTERS_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.SC_OK);
        assertEquals(0, result.getResponse().getContentLength());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(lesFiltres), result.getResponse().getContentAsString(StandardCharsets.UTF_8), false);
    }
}
