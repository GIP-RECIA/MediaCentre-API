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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esco.portlet.mediacentre.model.affectation.GestionAffectation;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltreModel;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.filtres.Filtre;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.esco.portlet.mediacentre.service.IFiltrageService;
import org.esco.portlet.mediacentre.service.IMediaCentreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    
    List<CategorieFiltres> categoriesFiltresUser;
    
    CategorieFiltres categorieEtablissement;
    
    CategorieFiltreModel categorieFiltreModel;
    
    @Resource
    List<GestionAffectation> affectation;
    
    List<Ressource> listeRessourceMediaCenter;
    
    List<Ressource> ressourcesFiltrees;
    
    ModelAndView mav = new ModelAndView();
    
    @RenderMapping
    public ModelAndView showMainView(final RenderRequest request, final RenderResponse response) throws Exception {
        final String viewName = "main";        
        //final ModelAndView mav = new ModelAndView(viewName);
        mav = new ModelAndView(viewName);
        
        if(log.isDebugEnabled()) {
            log.debug("Using view name " + viewName + " for main view");
        }
        
        //List<Ressource> listeRessourceMediaCenter = this.mediaCentreService.retrieveListRessource(request);
        listeRessourceMediaCenter = this.mediaCentreService.retrieveListRessource(request);
        
        // ajout de la catégorie LinkedEtablissement
        List<String> userLinkedEtablissements = this.mediaCentreService.getUserLinkedEtablissements(request);
        List<String> userCurrentEtablissement = this.mediaCentreService.getUserCurrentEtablissement(request);
        if(categoriesFiltresUser == null){
        	categoriesFiltresUser = new ArrayList<CategorieFiltres>(categoriesFiltres);
        }
        initialiserEtablissementCategorieFiltre(userLinkedEtablissements, userCurrentEtablissement);
        
        //CategorieFiltreModel categorieFiltreModel = new CategorieFiltreModel();
        if(categorieFiltreModel == null){
        	categorieFiltreModel = new CategorieFiltreModel();
        }
        categorieFiltreModel.setListCategorieFiltre(categoriesFiltresUser);
        
        //List<Ressource> ressources = filtrageService.filtrerRessources(categoriesFiltres, listeRessourceMediaCenter);
        ressourcesFiltrees = filtrageService.filtrerRessources(categoriesFiltresUser, listeRessourceMediaCenter);
        
        mav.addObject("ressources", ressourcesFiltrees);
        mav.addObject("categorieFiltreModel", categorieFiltreModel);
        mav.addObject("affectation", affectation);
        
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

    @RequestMapping(value = { "VIEW" }, params = { "action=filterAllRessources" })
	public void filterAllRessources (@ModelAttribute("categorieFiltreModel") CategorieFiltreModel categorieFiltreModel,
			ActionRequest request, ActionResponse response, ModelMap model) {
    	
    	// mise à jour des attributs de filtres en fonction du model --> réutilisé dans le view
    	categoriesFiltresUser = new ArrayList<CategorieFiltres>(categorieFiltreModel.getListCategorieFiltre());
    	
		model.addAttribute("ressources", ressourcesFiltrees);
		model.addAttribute("categorieFiltreModel", categorieFiltreModel);
		model.addAttribute("affectation", affectation);
	}
    
    @ModelAttribute("categorieFiltreModel")
	public CategorieFiltreModel getCategorieFiltreModel() {
		return categorieFiltreModel;
	}
    
    /**
     * Méthode qui ajoute la categorie de filtre des etablissements (info User reçues du portail)
     * 
     * @param userLinkedEtablissements
     * @param userCurrentEtablissement
     */
    private void initialiserEtablissementCategorieFiltre(List<String> userLinkedEtablissements, List<String> userCurrentEtablissement){
    	
    	if(categorieEtablissement == null){
    	
	    	if(userLinkedEtablissements.size() >1){
	    		categorieEtablissement = new CategorieFiltres();
	    		categorieEtablissement.setId("etablissement");
	    		categorieEtablissement.setLibelle("Etablissement");
	    		categorieEtablissement.setValeursMultiples(true);
		    	List<Filtre> filtreEtab = new ArrayList<Filtre>();
		    	for(String linkedEtablissement : userLinkedEtablissements){
		    		Filtre filtre = new Filtre();
		    		filtre.setId(linkedEtablissement);
		    		filtre.setLibelle(linkedEtablissement);
		    		boolean actif = false;
		    		if(userCurrentEtablissement.contains(linkedEtablissement)){
		    			actif = true;
		    		}
		    		filtre.setActif(actif);
		    		filtre.setNomAttribut("idEtablissement.UAI");
		    		filtre.setRegexpAttribut(linkedEtablissement);
		    		filtreEtab.add(filtre);
		    	}
		    	categorieEtablissement.setFiltres(filtreEtab);
		    	categoriesFiltresUser.add(1, categorieEtablissement);
	    	}
    	}
    }
    
    
    public IMediaCentreService getMediaCentreService() {
        return mediaCentreService;
    }

    public void setMediaCentreService(IMediaCentreService mediaCentreService) {
        this.mediaCentreService = mediaCentreService;
    }
    
}
