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
import fr.recia.mediacentre.api.config.ConfigurationTest;
import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.service.mediacentre.MediaCentreService;
import fr.recia.mediacentre.api.service.utils.UserInfosBuilder;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.IsMemberOf;
import fr.recia.mediacentre.api.model.resource.Ressource;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
@Import(ConfigurationTest.class)
@ActiveProfiles({ "test" })
@TestPropertySource(locations = "classpath:application-test.yml")
public class MediaCentreServiceImplTest {

    @NonNull
    @Value("${url.ressources.mediacentre}")
    @Setter
    private String urlRessources;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserInfosBuilder userInfosBuilder;

  @MockBean
  private MediaCentreResource mediaCentreResource;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    MappingProperties mappingProperties;

    @MockBean
    private CategoriesByProfilesProperties categoriesByFilters;

    private List<Ressource> listeRessourcesMediaCentre;

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private SoffitHolder soffit;

    @Autowired
    private MediaCentreService mediaCentreService;

    @NonNull
    @Value("${path.resources}")
    private String resourcesFilePath;

    @NonNull
    @Value("${path.filters}")
    private String filtersFilePath;

    @NonNull
    @Value("${path.isMemberOf}")
    private String isMemberOfFilePath;

    private IsMemberOf isMemberOf;

    private Map<String,List<String>> userInfos;

    private static final  String testUai = "TestUai";

  private List<String> currentUAI = List.of("UAI_1");
  private List<String> listUAI = List.of("UAI_1","UAI_2");

    @Before
    public void init() throws IOException {
        listeRessourcesMediaCentre = objectMapper.readValue(new File(resourcesFilePath),new TypeReference<>(){});
        isMemberOf = objectMapper.readValue(new File(isMemberOfFilePath), new TypeReference<>() {
        });

      soffit.setUaiCurrent(currentUAI);
      soffit.setUaiList(listUAI);
      soffit.setProfiles(Collections.singletonList("profile1"));
      soffit.setGarId(Collections.singletonList("garId"));

      userInfos = userInfosBuilder.getUserInfos(soffit, isMemberOf.getIsMemberOf());

      doReturn("currentUai").when(mappingProperties).getUaiCurrent();
      doReturn("profile").when(mappingProperties).getProfiles();
      doReturn("listUai").when(mappingProperties).getUaiList();
      doReturn("garId").when(mappingProperties).getGarId();
      doReturn("isMemberOf").when(mappingProperties).getGroups();

        CategoriesByProfilesProperties.ProfilesMap profilesMap = new CategoriesByProfilesProperties.ProfilesMap();
        profilesMap.setProfiles(List.of("profile1"));
        profilesMap.setFilters(List.of(FilterEnum.TYPE_PRESENTATION_FILTER, FilterEnum.NIVEAU_EDUCATIF_FILTER, FilterEnum.UAI_FILTER));
        when(categoriesByFilters.getCategoriesByProfiles()).thenReturn(List.of(profilesMap));
    }

    // retrieveRessourceById() tests :

  @Captor
  private ArgumentCaptor<Map<String, List<String>>> captor;


  @Test
  public void retrieveRessourceByNameForCurrentEtabRemoveOtherUais(){

    //make sure that test data are relevant
    //test does not prove that listUAI is filtered in request if there is nothing to filter
    assertNotEquals(currentUAI, listUAI);

    String idRessourceRequested = "ID-RES";
    doReturn(null).when(mediaCentreResource).retrieveListRessource(any(), any());
    doReturn(null).when(mediaCentreResource).retrieveListRessource(any(), any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceByName(idRessourceRequested, isMemberOf.getIsMemberOf(), false, true);

    verify(mediaCentreResource).retrieveListRessource(any(), captor.capture());
    assertTrue(captor.getValue().containsKey(mappingProperties.getUaiList()));
    assertEquals(currentUAI, captor.getValue().get(mappingProperties.getUaiList()));
  }

  @Test
  public void retrieveRessourceByNameForAllEtabDoesNotRemoveOtherUais(){

    //make sure that test data are relevant
    //test does not prove that listUAI is not filtered in request if there is nothing to filter
    assertNotEquals(currentUAI, listUAI);

    String idRessourceRequested = "ID-RES";
    doReturn(null).when(mediaCentreResource).retrieveListRessource(eq(idRessourceRequested), any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceByName(idRessourceRequested, isMemberOf.getIsMemberOf(), false, false);

    verify(mediaCentreResource).retrieveListRessource(any(), captor.capture());
    assertTrue(captor.getValue().containsKey(mappingProperties.getUaiList()));
    assertEquals(listUAI, captor.getValue().get(mappingProperties.getUaiList()));
  }

    // retrieveListRessource() tests :

    @Test
    public void retrieveListRessource_OK() throws YmlPropertyNotFoundException, MediacentreWSException {
        doReturn(listeRessourcesMediaCentre).when(mediaCentreResource).retrieveListRessource(eq(urlRessources), any());
        List<Ressource> result = mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());
        assertNotNull(result);
        assertEquals(result.size(),listeRessourcesMediaCentre.size());

        for(int i = 0; i < listeRessourcesMediaCentre.size(); i++){
            Ressource expected = listeRessourcesMediaCentre.get(i);
            Ressource actual = result.get(i);
            assertEquals(expected.getIdRessource(),actual.getIdRessource());
        }
    }

    @Test
    public void retrieveListRessource_When_No_Resource_OK() throws YmlPropertyNotFoundException, MediacentreWSException {
        when(mediaCentreResource.retrieveListRessource(urlRessources,userInfos)).thenReturn(new ArrayList<>());
        List<Ressource> result = mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());

        assertNotNull(result);
        assertEquals(result.size(),0);
    }

    @Test
    public void retrieveListRessource_When_UrlRessources_Is_Missing_In_Yml_Properties_KO() {
//        mediaCentreService.setUrlRessources("");
//        assertThrows(YmlPropertyNotFoundException.class, () -> {
//            mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());
//        });
    }

    // retrieveFiltersList() test :

    @Test
    public void retrieveFiltersList_OK() throws IOException, YmlPropertyNotFoundException {
        List<FilterEnum> result = mediaCentreService.retrieveFiltersList();
        assertNotNull(result);
        assertEquals(3, result.size());
        List<FilterEnum> filters = objectMapper.readValue(new File(filtersFilePath), new TypeReference<>() {});
        assertThat(filters).containsAll(result);
    }

    @Test
    public void retrieveFiltersList_When_No_Yml_Properties_For_Filter_Categories_KO() {
        when(categoriesByFilters.getCategoriesByProfiles()).thenReturn(new ArrayList<>());
        assertThrows(YmlPropertyNotFoundException.class, () -> {
            mediaCentreService.retrieveFiltersList();
        });
    }
}
