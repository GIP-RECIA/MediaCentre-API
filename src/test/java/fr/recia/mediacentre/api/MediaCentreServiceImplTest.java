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
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.IsMemberOf;
import fr.recia.mediacentre.api.model.resource.IdEtablissement;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.mediacentre.MediaCentreService;
import fr.recia.mediacentre.api.service.utils.MapUtils;
import fr.recia.mediacentre.api.service.utils.UserInfosBuilder;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
import static org.mockito.Mockito.when;

@Slf4j
@DirtiesContext
@SpringBootTest
@Import(ConfigurationTest.class)
@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class MediaCentreServiceImplTest {
  @NonNull
  @Value("${url.ressources.mediacentre}")
  @Setter
  private String urlRessources;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private UserInfosBuilder userInfosBuilder;

  @MockitoBean
  private MediaCentreResource mediaCentreResource;

  @MockitoBean
  private CacheManager cacheManager;

  @MockitoBean
  MappingProperties mappingProperties;

  @MockitoBean
  private CategoriesByProfilesProperties categoriesByFilters;

  private List<Ressource> listeRessourcesMediaCentre;

  @MockitoBean
  private RestTemplate restTemplate;

  @MockitoBean
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

  private Map<String, List<String>> userInfos;

  private static final String testUai = "TestUai";

  private List<String> currentUAI;
  private List<String> listUAI;

  @BeforeEach
  public void init() throws IOException {
    listeRessourcesMediaCentre = objectMapper.readValue(new File(resourcesFilePath), new TypeReference<>() {
    });
    isMemberOf = objectMapper.readValue(new File(isMemberOfFilePath), new TypeReference<>() {
    });

    this.listUAI = listeRessourcesMediaCentre.stream()
      .flatMap(x -> x.getIdEtablissement().stream())
      .map(IdEtablissement::getUai)
      .distinct()
      .toList();

    currentUAI = Collections.singletonList(listUAI.getFirst());

    doReturn("currentUai").when(mappingProperties).getUaiCurrent();
    doReturn("profile").when(mappingProperties).getProfiles();
    doReturn("listUai").when(mappingProperties).getUaiList();
    doReturn("garId").when(mappingProperties).getGarId();
    doReturn("isMemberOf").when(mappingProperties).getGroups();

    doReturn(currentUAI).when(soffit).getUaiCurrent();
    doReturn(listUAI).when(soffit).getUaiList();
    doReturn(List.of("profile1")).when(soffit).getProfiles();
    doReturn(List.of("garId")).when(soffit).getGarId();

    userInfos = userInfosBuilder.getUserInfos(soffit, isMemberOf.getIsMemberOf());

    Map<String, List<String>> deepCopiedMap = MapUtils.stringListStringDeepCopy(userInfos);
    deepCopiedMap.put(mappingProperties.getProfiles(), soffit.getProfiles());
    deepCopiedMap.put(mappingProperties.getUaiCurrent(), soffit.getUaiCurrent());
    deepCopiedMap.put(mappingProperties.getUaiList(), soffit.getUaiList());
    deepCopiedMap.put(mappingProperties.getGarId(), soffit.getGarId());

    doReturn(deepCopiedMap).when(soffit).getUserInfosWithoutIsMemberOf();

    CategoriesByProfilesProperties.ProfilesMap profilesMap = new CategoriesByProfilesProperties.ProfilesMap();
    profilesMap.setProfiles(List.of("profile1"));
    profilesMap.setFilters(List.of(FilterEnum.TYPE_PRESENTATION_FILTER, FilterEnum.NIVEAU_EDUCATIF_FILTER, FilterEnum.UAI_FILTER));
    when(categoriesByFilters.getCategoriesByProfiles()).thenReturn(List.of(profilesMap));
  }

  // retrieveRessourceById() tests :

  @Test
  public void retrieveRessourceByNameForCurrentEtabRemoveOtherUais() {
    String nomRessourceRequested = "nomRessource3";

    //make sure that test data are relevant
    //test does not prove that listUAI is filtered in request if there is nothing to filter
    assertNotEquals(currentUAI, listUAI);
    assertTrue(listeRessourcesMediaCentre.stream().filter(x -> nomRessourceRequested.equals(x.getNomRessource())).toList().getFirst().getIdEtablissement().size() > 1);

    doReturn(listeRessourcesMediaCentre).when(mediaCentreResource).retrieveListRessource(eq(urlRessources), any());

    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceByName(nomRessourceRequested, isMemberOf.getIsMemberOf(), false, true);

    assertTrue(optionalRessource.isPresent());
    assertEquals(1, optionalRessource.get().getIdEtablissement().size());
    assertEquals(currentUAI.getFirst(), optionalRessource.get().getIdEtablissement().getFirst().getUai());
  }

  @Test
  public void retrieveRessourceByNameForAllEtabDoesNotRemoveOtherUais() {
    String nomRessourceRequested = "nomRessource3";

    //make sure that test data are relevant
    //test does not prove that listUAI is filtered in request if there is nothing to filter
    assertNotEquals(currentUAI, listUAI);
    assertTrue(listeRessourcesMediaCentre.stream().filter(x -> nomRessourceRequested.equals(x.getNomRessource())).toList().getFirst().getIdEtablissement().size() > 1);

    doReturn(listeRessourcesMediaCentre).when(mediaCentreResource).retrieveListRessource(eq(urlRessources), any());
    Optional<Ressource> optionalRessource = mediaCentreService.retrieveRessourceByName(nomRessourceRequested, isMemberOf.getIsMemberOf(), false, false);

    Ressource targetRessource = listeRessourcesMediaCentre.stream().filter(x -> nomRessourceRequested.equals(x.getNomRessource())).toList().getFirst();

    assertTrue(optionalRessource.isPresent());
    assertEquals(targetRessource.getIdEtablissement().size(), optionalRessource.get().getIdEtablissement().size());
    assertEquals(targetRessource.getIdEtablissement().stream().map(IdEtablissement::getUai).toList(), optionalRessource.get().getIdEtablissement().stream().map(IdEtablissement::getUai).toList());
  }

  // retrieveListRessource() tests :

  @Test
  public void retrieveListRessource_OK() throws YmlPropertyNotFoundException, MediacentreWSException {
    doReturn(listeRessourcesMediaCentre).when(mediaCentreResource).retrieveListRessource(eq(urlRessources), any());
    List<Ressource> result = mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());
    assertNotNull(result);
    assertEquals(result.size(), listeRessourcesMediaCentre.size());

    for (int i = 0; i < listeRessourcesMediaCentre.size(); i++) {
      Ressource expected = listeRessourcesMediaCentre.get(i);
      Ressource actual = result.get(i);
      assertEquals(expected.getIdRessource(), actual.getIdRessource());
    }
  }

  @Test
  public void retrieveListRessource_When_No_Resource_OK() throws YmlPropertyNotFoundException, MediacentreWSException {
    when(mediaCentreResource.retrieveListRessource(urlRessources, userInfos)).thenReturn(new ArrayList<>());
    List<Ressource> result = mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());

    assertNotNull(result);
    assertEquals(result.size(), 0);
  }

  // retrieveFiltersList() test :

  @Test
  public void retrieveFiltersList_OK() throws IOException, YmlPropertyNotFoundException {
    List<FilterEnum> result = mediaCentreService.retrieveFiltersList();
    assertNotNull(result);
    assertEquals(3, result.size());
    List<FilterEnum> filters = objectMapper.readValue(new File(filtersFilePath), new TypeReference<>() {
    });
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
