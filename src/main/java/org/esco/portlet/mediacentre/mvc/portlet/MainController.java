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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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

/**
 * Main portlet view.
 */
@Controller
//@Scope("session")
@RequestMapping("VIEW")
public class MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IMediaCentreService mediaCentreService;

    @Autowired
    private IFiltrageService filtrageService;    
    
    @Resource 
    List<CategorieFiltres> categoriesFiltres;
    
    @RenderMapping 
    public ModelAndView showMainView(final RenderRequest request, final RenderResponse response) throws Exception {
        final String viewName = "main";        
        final ModelAndView mav = new ModelAndView(viewName);
        
        if(log.isDebugEnabled()) {
            log.debug("Using view name " + viewName + " for main view");
        }
        
        List<Ressource> listeRessourceMediaCenter = this.mediaCentreService.retrieveListRessource(request);
        
        List<Ressource> ressources = filtrageService.filtrerRessources(categoriesFiltres, listeRessourceMediaCenter); 
        mav.addObject("ressources", ressources);
        mav.addObject("categoriesFiltre", categoriesFiltres);
        
        if(log.isDebugEnabled()) {
            log.debug("Rendering main view");
        }
        
        return mav;
    }
 
  //  @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    	log.debug("action mapping");
    	
    }

    public IMediaCentreService getMediaCentreService() {
        return mediaCentreService;
    }

    public void setMediaCentreService(IMediaCentreService mediaCentreService) {
        this.mediaCentreService = mediaCentreService;
    }

    
    @RenderMapping(params = "action=showForm")
    public String viewByParameter(Map<String, Object> map) {
        log.info("############## Je passe ###########");
        System.out.println("Je passe");
        return "main";
    }
    
//    @RequestMapping(value="VIEW",params="action=affichageDR")
//    protected ModelAndView renderViewAffichageDR(RenderRequest request,  RenderResponse response) throws Exception {
//    	
//        final String viewName = "main";        
//        final ModelAndView mav = new ModelAndView(viewName);
//        
//        System.out.println("Je passe");
//        return mav;
//    }
}
