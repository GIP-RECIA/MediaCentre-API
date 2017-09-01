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
package org.esco.portlet.mediacentre;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.filtres.Filtre;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jgribonvald on 14/09/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextFiltre.xml")
public class MediaCentreFiltreTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource 
    List<CategorieFiltres> categoriesFiltres;
    
    @Test
    public void testFiltre() throws Exception {
    	
    	for (CategorieFiltres categorie : categoriesFiltres) {
    		System.out.println(categorie);
    	}
    	System.out.println("Ok");
    }
    
    
    @Test
    public void testFiltrageRessourceGET() throws Exception {
    	
		String urlWS = "http://localhost:8090/mediacentre-web/json/mediacentre.json";
		List<Ressource> ressources = null; 
				
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        if (response != null && Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()) {
        	ressources = response.readEntity(new GenericType<List<Ressource>>(){});	

        	List<Ressource> ressourcesFiltrees = filtrerRessources(categoriesFiltres, ressources);
        	System.out.println("Ressources filtrées : " + ressourcesFiltrees.size());
        	System.out.println(ressourcesFiltrees);
        	
        }    	
    	
    	System.out.println("Ok");
    }    
    
    
    public List<Ressource> filtrerRessources(List<CategorieFiltres> categoriesFiltres,  List<Ressource> ressources) throws Exception {
    	List<Ressource> ressourcesFiltrees = new ArrayList<Ressource>();
    	
    	for (Ressource ressource : ressources) {
    		boolean categoriePassant = true;
    		
    		// Verifie si la ressource correspond à toutes les catégories
	    	for (CategorieFiltres categorie : categoriesFiltres ) {
	    		
	    		// Verifie si la ressource correspond à au moins un filtre de la categorie
	    		boolean filtrePassant = false;
	    		for (Filtre filtre : categorie.getFiltres()) {
	    			if (filtre.estPassante(ressource)) {
	    				filtrePassant = true;
	    				break;
	    			}
	    		}
	    		categoriePassant = categoriePassant && filtrePassant;
	    		if (!categoriePassant) {
	    			break;
	    		}
	    	}
	    	if (categoriePassant) {
	    		ressourcesFiltrees.add(ressource);
	    	}
    	}
    	
    	return ressourcesFiltrees;
    }
    
    @Test
    public void testFiltrageCategorieFiltresGET() throws Exception {
    	
		String urlWS = "http://localhost:8090/mediacentre-web/json/mediacentre.json";
		List<Ressource> ressources = null; 
				
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        if (response != null && Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()) {
        	ressources = response.readEntity(new GenericType<List<Ressource>>(){});	

        	List<CategorieFiltres> categorieFiltresFiltrees = filtrerCategorieFiltre(categoriesFiltres, ressources);
        	System.out.println("CategorieFiltres filtrées : " + categorieFiltresFiltrees.size());
        	System.out.println(categorieFiltresFiltrees);
        	
        }    	
    	
    	System.out.println("Ok");
    }    
    
    public List<CategorieFiltres> filtrerCategorieFiltre(List<CategorieFiltres> categoriesFiltres, List<Ressource> ressources) throws Exception {
		List<CategorieFiltres> categoriesFiltresFiltrees = new ArrayList<CategorieFiltres>();
		
		for(CategorieFiltres categorieFiltres : categoriesFiltres){
			CategorieFiltres categorie = (CategorieFiltres) categorieFiltres.clone();
			
			List<Filtre> listFiltre = new ArrayList<Filtre>();
			
			for(Filtre filtre : categorieFiltres.getFiltres()){
				
				boolean filtreMatchWithRessource = false;
				
				for(Ressource ressource : ressources){
					
					if(filtre.estPassante(ressource)){
						filtreMatchWithRessource = true;
						break;
					}
				}
				if(filtreMatchWithRessource){
					//categorieFiltres.getFiltres().remove(filtre);
					listFiltre.add((Filtre) filtre.clone());
				}
			}
			categorie.setFiltres(listFiltre);
			
			if(categorie.getFiltres().size() > 1){
				categoriesFiltresFiltrees.add(categorie);
			}
		}
		
		return categoriesFiltresFiltrees;
	}
    
    
    @Test
    public void testReflect() throws Exception {
    	
		String urlWS = "http://localhost:8090/mediacentre-web/json/mediacentre.json";
		List<Ressource> ressources = null; 
				
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlWS);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        if (response != null && Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()) {
        	ressources = response.readEntity(new GenericType<List<Ressource>>(){});	
        	
        	Ressource ressource =  ressources.get(0);
        	
        	List<Object> valeurs = ressource.getValeursAttribut("domaineEnseignement.nom");
        	
        	System.out.println("=> " + valeurs);
        }    	
    	
    	System.out.println("Ok");
    }     
}
