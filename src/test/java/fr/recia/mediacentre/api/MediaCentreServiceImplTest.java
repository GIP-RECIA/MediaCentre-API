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
import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.model.resource.IdEtablissement;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.IsMemberOf;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.impl.MediaCentreServiceImpl;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
@ActiveProfiles({ "test" })
@TestPropertySource(locations = "classpath:application-test.yml")
public class MediaCentreServiceImplTest {

    @NonNull
    @Value("${url.ressources.mediacentre}")
    @Setter
    private String urlRessources;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private MediaCentreServiceImpl mediaCentreService;

    @MockBean
    private MediaCentreResourceJacksonImpl mediaCentreResource;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private SoffitHolder soffit;

    @MockBean
    MappingProperties mappingProperties;

    @MockBean
    private CategoriesByProfilesProperties categoriesByFilters;

    private List<Ressource> listeRessourcesMediaCentre;

    @MockBean
    private RestTemplate restTemplate;

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

    @Before
    public void init() throws IOException {
        listeRessourcesMediaCentre = objectMapper.readValue(new File(resourcesFilePath),new TypeReference<>(){});
        isMemberOf = objectMapper.readValue(new File(isMemberOfFilePath), new TypeReference<>() {
        });
        userInfos = new HashMap<>();
        userInfos.put("etabIds",List.of("id"));
        userInfos.put("currentEtabId",List.of("idCurrent"));
        userInfos.put("uid",List.of("uid"));
        userInfos.put("profils",List.of("profile1"));
        userInfos.put("isMemberOf", isMemberOf.getIsMemberOf());

        doReturn(List.of("profile1")).when(soffit).getProfiles();

        CategoriesByProfilesProperties.ProfilesMap profilesMap = new CategoriesByProfilesProperties.ProfilesMap();
        profilesMap.setProfiles(List.of("profile1"));
        profilesMap.setFilters(List.of(FilterEnum.TYPE_PRESENTATION_FILTER, FilterEnum.NIVEAU_EDUCATIF_FILTER, FilterEnum.UAI_FILTER));
        when(categoriesByFilters.getCategoriesByProfiles()).thenReturn(List.of(profilesMap));

        String currentEtabUaiKey = "CurrentEtabUai";

        Map<String, List<String>> soffitMap = new HashMap<>();
        soffitMap.put(currentEtabUaiKey, List.of(testUai));

        doReturn(soffitMap).when(soffit).getUserInfosWithoutIsMemberOf();
        doReturn(currentEtabUaiKey).when(mappingProperties).getCurrentEtabUaiKey();
    }

    // retrieveRessourceById() tests :

    @Test
    public void retrieveRessourceById_NoEtab_OK(){

      String idRessourceRequested = "ID-RES";
      String idRessource = idRessourceRequested;

      Ressource matchingRessource = new Ressource();
      matchingRessource.setIdEtablissement(new ArrayList<>());

      matchingRessource.setIdRessource(idRessource);


      doReturn(List.of(matchingRessource)).when(mediaCentreService).retrieveListRessource(any());
      Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceById(idRessourceRequested, isMemberOf.getIsMemberOf(), false);

      assertFalse(optionalRessource.isEmpty());
      assertEquals(idRessourceRequested, optionalRessource.get().getIdRessource());
    }

  @Test
  public void retrieveRessourceById_NoEtab_Base64_OK(){

    String idDecoded = "ID-TEST";

    String idEncoded = new String(Base64.encodeBase64(idDecoded.getBytes()));

    String idRessourceRequested = idEncoded;
    String idRessource = idDecoded;

    Ressource matchingRessource = new Ressource();
    matchingRessource.setIdEtablissement(new ArrayList<>());

    matchingRessource.setIdRessource(idRessource);


    doReturn(List.of(matchingRessource)).when(mediaCentreService).retrieveListRessource(any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceById(idRessourceRequested, isMemberOf.getIsMemberOf(), true);

    assertFalse(optionalRessource.isEmpty());
    assertEquals(idDecoded, optionalRessource.get().getIdRessource());
  }

  @Test
  public void retrieveRessourceById_MatchingEtab_OK(){

    String idRessourceRequested = "ID-RES";
    String idRessource = idRessourceRequested;

    Ressource matchingRessource = new Ressource();

    matchingRessource.setIdEtablissement(new ArrayList<>());

    IdEtablissement idEtablissement = new IdEtablissement();
    idEtablissement.setUAI(testUai);

    matchingRessource.getIdEtablissement().add(idEtablissement);

    matchingRessource.setIdRessource(idRessource);

    doReturn(List.of(matchingRessource)).when(mediaCentreService).retrieveListRessource(any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceById(idRessourceRequested, isMemberOf.getIsMemberOf(), false);

    assertFalse(optionalRessource.isEmpty());
    assertEquals(idRessourceRequested, optionalRessource.get().getIdRessource());
  }

  @Test
  public void retrieveRessourceById_NonMatchingEtab_KO(){

    String idRessourceRequested = "ID-RES";
    String idRessource = idRessourceRequested;

    Ressource matchingRessource = new Ressource();

    matchingRessource.setIdEtablissement(new ArrayList<>());

    IdEtablissement idEtablissement = new IdEtablissement();
    idEtablissement.setUAI(testUai+"-suffix");

    matchingRessource.getIdEtablissement().add(idEtablissement);

    matchingRessource.setIdRessource(idRessource);

    doReturn(List.of(matchingRessource)).when(mediaCentreService).retrieveListRessource(any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceById(idRessourceRequested, isMemberOf.getIsMemberOf(), false);

    assertTrue(optionalRessource.isEmpty());
  }

  @Test
  public void retrieveRessourceById_NotEtab_NonMatchingId_KO(){

    String idRessourceRequested = "ID-RES";
    String idRessource = "ID-TEST";

    Ressource matchingRessource = new Ressource();
    matchingRessource.setIdEtablissement(new ArrayList<>());

    matchingRessource.setIdRessource(idRessource);


    doReturn(List.of(matchingRessource)).when(mediaCentreService).retrieveListRessource(any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceById(idRessourceRequested, isMemberOf.getIsMemberOf(), false);

    assertTrue(optionalRessource.isEmpty());
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
        mediaCentreService.setUrlRessources("");
        assertThrows(YmlPropertyNotFoundException.class, () -> {
            mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());
        });
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
