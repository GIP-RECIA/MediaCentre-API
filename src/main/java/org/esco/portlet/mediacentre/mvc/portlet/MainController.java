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
package org.esco.portlet.mediacentre.mvc.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.CacheControl;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.esco.portlet.mediacentre.model.affectation.GestionAffectation;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.esco.portlet.mediacentre.service.IFiltrageService;
import org.esco.portlet.mediacentre.service.IMediaCentreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * Main portlet view.
 */
@Controller
@RequestMapping("VIEW")
public class MainController {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IMediaCentreService mediaCentreService;

    @Autowired
    private IFiltrageService filtrageService;    
    
    @Resource
    private List<CategorieFiltres> categoriesFiltres;
    
    @Resource
    private List<GestionAffectation> gestionAffectation;
    
    
	/* 
	 * ===============================================
	 * Constructeurs de la classe 
	 * =============================================== 
	 */

	/* 
	 * ===============================================
	 * Getter / Setter de la classe 
	 * =============================================== 
	 */

	/* 
	 * ===============================================
	 * Méthodes privées de la classe 
	 * =============================================== 
	 */
 
	/* 
	 * ===============================================
	 * Méthodes publiques de la classe 
	 * =============================================== 
	 */	
    
    @RenderMapping
    public ModelAndView showMainView(final RenderRequest request, final RenderResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("main");
        
        if(log.isDebugEnabled()) {
            log.debug("Using view name " + mav.getViewName() + " for main view");
        }
        
        Map<String, List<String>> userInfo = mediaCentreService.getUserInfos(request);
        List<Ressource> listeRessources = mediaCentreService.retrieveListRessource(request);

        List<CategorieFiltres> categoriesFiltresCandidats = new ArrayList<CategorieFiltres>();
    	List<Ressource> ressourcesCandidates = new ArrayList<Ressource>();
    	
    	String ressourcesParFiltre = filtrageService.preparerFiltrage(userInfo, categoriesFiltres, listeRessources, categoriesFiltresCandidats, ressourcesCandidates);
    	
        mav.addObject("ressourcesParFiltre", ressourcesParFiltre);
        mav.addObject("ressources", ressourcesCandidates);
        mav.addObject("categoriesFiltres", categoriesFiltresCandidats);
        mav.addObject("gestionAffectation", filtrageService.filtrerGestionAffectation(gestionAffectation, userInfo));
        
        if(log.isDebugEnabled()) {
            log.debug("Rendering main view");
        }

        response.getCacheControl().setExpirationTime(0);
        
        return mav;
    }
 
    //  @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    	log.debug("action mapping");
    	
    }

    @ResourceMapping(value="ajouterFavori")
    public void ajouterFavori(ResourceRequest request, ResourceResponse response) throws IOException {
    	String id = request.getParameter("id");
    	mediaCentreService.addToUserFavorites(request, id);
    	response.getWriter().write("{\"success\":true}");
    }
    
    @ResourceMapping(value="retirerFavori")
    public void retirerFavori(ResourceRequest request, ResourceResponse response) throws IOException {
    	String id = request.getParameter("id");
    	mediaCentreService.removeToUserFavorites(request, id);
    	response.getWriter().write("{\"success\":true}");
    }
    
}
