package org.esco.portlet.mediacentre.model.filtres;

import java.io.Serializable;
import java.util.List;

public class CategorieFiltreModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -979373924543584085L;

	private List<CategorieFiltres> listCategorieFiltre;
	
	
	public CategorieFiltreModel() {
	}


	/**
	 * @return the listCategorieFiltre
	 */
	public List<CategorieFiltres> getListCategorieFiltre() {
		return listCategorieFiltre;
	}


	/**
	 * @param listCategorieFiltre the listCategorieFiltre to set
	 */
	public void setListCategorieFiltre(List<CategorieFiltres> listCategorieFiltre) {
		this.listCategorieFiltre = listCategorieFiltre;
	}
}
