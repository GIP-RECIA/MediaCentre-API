package org.esco.portlet.mediacentre.utils;

import java.util.ArrayList;
import java.util.List;

import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;

public class MediacentreUtils {

	public MediacentreUtils() {
	}

	public List<CategorieFiltres> getCopyOfListCategoriesFiltres(List<CategorieFiltres> categoriesFiltres){
		
		List<CategorieFiltres> categoriesFiltresCopy =  new ArrayList<CategorieFiltres>();
		
		if(categoriesFiltres != null){
			for(CategorieFiltres categories : categoriesFiltres){
				CategorieFiltres categorieCLone = (CategorieFiltres) categories.clone();
				categoriesFiltresCopy.add(categorieCLone);
			}
		}
		return categoriesFiltresCopy;
	}
	
}
