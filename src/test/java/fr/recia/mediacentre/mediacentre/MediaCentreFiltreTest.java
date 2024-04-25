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
package fr.recia.mediacentre.mediacentre;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.mediacentre.model.filter.Filtre;
import fr.recia.mediacentre.mediacentre.model.filter.category.CategorieFiltres;
import fr.recia.mediacentre.mediacentre.model.filter.category.CategorieFiltresEtablissement;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import fr.recia.mediacentre.mediacentre.service.FiltrageService;
import fr.recia.mediacentre.mediacentre.service.MediaCentreService;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by jgribonvald on 14/09/16.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextFiltre.xml")
@SpringBootTest
public class MediaCentreFiltreTest {

    @Resource
	List<CategorieFiltres> categoriesFiltres;

    @Autowired
    private FiltrageService filtrageService;

    @Autowired
    private MediaCentreService mediaCentreService;

	@Autowired
	@Qualifier("resourceGood1")
	private org.springframework.core.io.Resource resource;
	@Autowired
	@Qualifier("resourceGood2")
	private org.springframework.core.io.Resource resource2Etab;
	@Autowired
	@Qualifier("resourceError")
	private org.springframework.core.io.Resource resourceError;

	@NonNull
	@Value("${url.ressources.mediacentre}")
	@Setter
	private String urlRessources;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SoffitHolder soffit;

	private MockRestServiceServer mockServer;

	@Mock
	PortletRequest request;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Assert.assertNotNull(categoriesFiltres);
		this.mockServer = MockRestServiceServer.createServer(restTemplate);

		Map<String, List<String>> userInfos = soffit.getUserInfos();

		log.debug("UserInfos used {}", userInfos);

		when(request.getAttribute(PortletRequest.USER_INFO)).thenReturn(userInfos);
	}

	public static List<Ressource> getRessourcesFromFile(org.springframework.core.io.Resource rs) {
		ObjectMapper xmlMapper = new ObjectMapper();
		String ressourceContent = null;
		try {
			ressourceContent = Files.asCharSource(rs.getFile(), Charset.forName("UTF-8")).read();
			Assert.assertTrue("file in classPath 'gar_ressourcesDiffusables_response.xml' doesn't exist!", ressourceContent != null);
			Assert.assertTrue("file in classPath 'gar_ressourcesDiffusables_response.xml' is Empty!", !ressourceContent.isEmpty());
			log.debug("File content is : {}", Files.asCharSource(rs.getFile(), Charset.forName("UTF-8")).read());

			TypeReference<Ressource[]> typeRef = new TypeReference<Ressource[]>(){};

			Ressource[] ressources = xmlMapper.readValue(rs.getFile(), typeRef);

			log.debug("Json File to object gives {}", ressources);

			return Arrays.asList(ressources);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void testErreurRessource() {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resourceError, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertTrue(ressources.isEmpty());
	}

	@Test
	public void testErreurBadRequest() throws URISyntaxException {
		mockServer.expect(MockRestRequestMatchers.requestTo(urlRessources)).andRespond(MockRestResponseCreators.withBadRequest());

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertTrue(ressources.isEmpty());
	}

	@Test
	public void testErreurUnauthorizedRequest() {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withUnauthorizedRequest());

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertTrue(ressources.isEmpty());
	}

	@Test
	public void testErreurServerError() {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withServerError());

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertTrue(ressources.isEmpty());
	}

    @Test
    public void testFiltre() throws Exception {
		Assert.assertNotNull(categoriesFiltres);
		Assert.assertFalse(categoriesFiltres.isEmpty());
    	for (CategorieFiltres categorie : categoriesFiltres) {
    		log.debug("CategorieFiltre {}", categorie.toString());
    		Assert.assertNotNull(categorie);
    		Assert.assertNotNull(categorie.getId());
    		Assert.assertNotNull(categorie.getLibelle());
    	};
    }

	@Test
	public void testFiltrage() throws Exception {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

		int nbRessources = ressources.size();
		log.debug("filtres " + categoriesFiltres.toString());
		log.debug("nb ressources " + nbRessources);

		List<Ressource> ressourcesFiltrees = filtrerRessources(categoriesFiltres, ressources);
		log.debug("Ressources filtrées : " + ressourcesFiltrees.size());

		Assert.assertEquals(nbRessources, ressourcesFiltrees.size());
	}

    public List<Ressource> filtrerRessources(List<CategorieFiltres> categoriesFiltres,  List<Ressource> ressources) throws Exception {
    	List<CategorieFiltres> categoriesFiltresCandidats = new ArrayList<>();
    	List<Ressource> ressourcesCandidates = new ArrayList<>();
    	Map<String, List<String>> userInfos = soffit.getUserInfos();

    	log.debug("userInfos {}", userInfos);

		String ressourcesParFiltre = filtrageService.preparerFiltrage(categoriesFiltres, ressources, categoriesFiltresCandidats, ressourcesCandidates);

    	return ressourcesCandidates;
    }

    @Test
    public void testFiltrageCategorieFiltresEtabEns() throws Exception {

		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

		List<CategorieFiltres> categorieFiltresFiltrees = filtrerCategorieFiltre(categoriesFiltres, ressources);
		log.debug("CategorieFiltres filtrées : " + categorieFiltresFiltrees.size());
		log.debug(categorieFiltresFiltrees.toString());
		for (CategorieFiltres cat: categorieFiltresFiltrees) {
			log.debug("keep {}", cat.getId());
		}

		Assert.assertEquals(2, categorieFiltresFiltrees.size());
    }

	@Test
	public void testFiltrageCategorieFiltresEns() throws Exception {

		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource2Etab, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

		for (CategorieFiltres cat: categoriesFiltres) {
			log.debug("CategorieFiltres en entrée {}", cat.getId());
		}

		List<CategorieFiltres> categorieFiltresFiltrees = filtrerCategorieFiltre(categoriesFiltres, ressources);
		log.debug("CategorieFiltres filtrées : " + categorieFiltresFiltrees.size());
		log.debug(categorieFiltresFiltrees.toString());
		for (CategorieFiltres cat: categorieFiltresFiltrees) {
			log.debug("keep {}", cat.getId());
		}

		// cat supprimée id=typeMedia car 1 seule valeur
		// cat supprimée id=discipline car pour les élèves
		Assert.assertEquals(3, categorieFiltresFiltrees.size());
	}

	@Test
	public void testFiltrageCategorieFiltresEns2() throws Exception {
		List<Ressource> ressources = getRessourcesFromFile(this.resource2Etab);

//		log.info(ressources.toString());
//		log.info(String.valueOf(ressources.isEmpty()));
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

		for (CategorieFiltres cat: categoriesFiltres) {
			log.debug("CategorieFiltres en entrée {}", cat.getId());
		}

		List<CategorieFiltres> categorieFiltresFiltrees = filtrerCategorieFiltre(categoriesFiltres, ressources);
		log.debug("CategorieFiltres filtrées : " + categorieFiltresFiltrees.size());
		log.debug(categorieFiltresFiltrees.toString());
		for (CategorieFiltres cat: categorieFiltresFiltrees) {
			log.debug("keep {}", cat.getId());
		}

		// cat supprimée id=établissement car pour les profs
		Assert.assertEquals(3, categorieFiltresFiltrees.size());
	}

    public List<CategorieFiltres> filtrerCategorieFiltre(List<CategorieFiltres> categoriesFiltres, List<Ressource> ressources) throws Exception {
		List<CategorieFiltres> categoriesFiltresCandidats = new ArrayList<>();
		List<Ressource> ressourcesCandidates = new ArrayList<>();
		Map<String, List<String>> userInfos = soffit.getUserInfos();

		log.debug("userInfos {}", userInfos);

		String ressourcesParFiltre = filtrageService.preparerFiltrage(categoriesFiltres, ressources, categoriesFiltresCandidats, ressourcesCandidates);

		return categoriesFiltresCandidats;
	}


    @Test
    public void testReflect() throws Exception {

		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

		Ressource ressource =  ressources.get(0);

		List<String> valeurs = ressource.getValeursAttribut("domaineEnseignement.nom");

		log.debug("Valeur de l'attribut domaineEnseignement.nom {}", valeurs);

		Assert.assertTrue(valeurs.size() > 0);
    }

    @Test
    public void testFiltrageRessource() throws Exception {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));

    	List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

    	List<CategorieFiltres> categoriesFiltresCandidats = new ArrayList<CategorieFiltres>();
    	List<Ressource> ressourcesCandidates = new ArrayList<Ressource>();
    	Map<String, List<String>> userInfoMap = soffit.getUserInfos();

    	String parametrage = filtrageService.preparerFiltrage(categoriesFiltres, ressources, categoriesFiltresCandidats, ressourcesCandidates);


    	Assert.assertEquals(ressourcesCandidates.size(), ressources.size());
    }

//    @Test
//    public void testFiltreUser() {
//		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));
//
//		List<Ressource> ressources = mediaCentreService.retrieveListRessource(this.request);
//		Assert.assertNotNull(ressources);
//		Assert.assertFalse(ressources.isEmpty());
//
//		CategorieFiltresUtilisateur filtreUser = null;
//		for (CategorieFiltres catFiltre: categoriesFiltres) {
//			if (catFiltre instanceof CategorieFiltresUtilisateur) {
//				filtreUser = (CategorieFiltresUtilisateur) catFiltre;
//			}
//		}
//
//		Assert.assertNotNull(filtreUser);
//
//    	Map<String, List<String>> userInfoMap = userResource.getUserInfoMap(this.request);
//		filtreUser.initialiser(userInfoMap, ressources );
//
//		boolean filtreIsApplied = false;
//    	for (Filtre filtre : filtreUser.getFiltres()) {
//    		if (filtre.concerneUtilisateur(userInfoMap)) filtreIsApplied = true;
//    		log.debug(filtre.getId() + " : " + filtre.concerneUtilisateur(userInfoMap));
//    	}
//
//    	Assert.assertTrue(filtreIsApplied);
//    }


    @Test
    public void testFiltreEtab() throws Exception {
		mockServer.expect(MockRestRequestMatchers.requestTo(this.urlRessources)).andRespond(MockRestResponseCreators.withSuccess(this.resource, MediaType.APPLICATION_OCTET_STREAM));

		List<Ressource> ressources = mediaCentreService.retrieveListRessource();
		Assert.assertNotNull(ressources);
		Assert.assertFalse(ressources.isEmpty());

    	CategorieFiltresEtablissement filtreEtab = null;
		for (CategorieFiltres catFiltre: categoriesFiltres) {
			if (catFiltre instanceof CategorieFiltresEtablissement) {
				filtreEtab = (CategorieFiltresEtablissement)catFiltre;
			}
		}

		Assert.assertNotNull(filtreEtab);

//		Map<String, List<String>> userInfoMap = soffit.getUserInfos();
		Map<String, List<String>> userInfoMap = new HashMap<String, List<String>>();
		filtreEtab.initialiser(userInfoMap, ressources );

		Assert.assertTrue(filtreEtab.getFiltres().size() > 0);

		boolean filtreIsApplied = false;
		for (Filtre filtre : filtreEtab.getFiltres()) {
			log.debug("filtre {}", filtre);
			if (filtrageService.concerneUtilisateur(filtre, userInfoMap)) filtreIsApplied = true;
			log.debug(filtre.getId() + " : " + filtreIsApplied);
		}

		Assert.assertTrue(filtreIsApplied);
    }    
}



