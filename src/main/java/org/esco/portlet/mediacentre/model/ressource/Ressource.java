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
package org.esco.portlet.mediacentre.model.ressource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "distributeurTech",
    "domaineEnseignement",
    "idEditeur",
    "idRessource",
    "idEtablissement",
    "idType",
    "niveauEducatif",
    "nomEditeur",
    "nomRessource",
    "sourceEtiquette",
    "typePedagogique",
    "typePresentation",
    "typologieDocument",
    "urlAccesRessource",
    "urlVignette",
    "validateurTech"
})
public class Ressource extends AbstractJson {

    @JsonProperty("distributeurTech")
    private String distributeurTech;
    @JsonProperty("domaineEnseignement")
    private List<DomaineEnseignement> domaineEnseignement = null;
    @JsonProperty("idEditeur")
    private String idEditeur;
    @JsonProperty("idRessource")
    private String idRessource;
    @JsonProperty("idEtablissement")
    private List<IdEtablissement> idEtablissement = null;
    @JsonProperty("idType")
    private String idType;
    @JsonProperty("niveauEducatif")
    private List<NiveauEducatif> niveauEducatif = null;
    @JsonProperty("nomEditeur")
    private String nomEditeur;
    @JsonProperty("nomRessource")
    private String nomRessource;
    @JsonProperty("sourceEtiquette")
    private String sourceEtiquette;
    @JsonProperty("typePedagogique")
    private List<Object> typePedagogique = null;
    @JsonProperty("typePresentation")
    private TypePresentation typePresentation;
    @JsonProperty("typologieDocument")
    private List<TypologieDocument> typologieDocument = null;
    @JsonProperty("urlAccesRessource")
    private String urlAccesRessource;
    @JsonProperty("urlVignette")
    private String urlVignette;
    @JsonProperty("validateurTech")
    private String validateurTech;

    @JsonIgnore
    private boolean favorite;

    @JsonIgnore
    private int idInterne;    
    
    @JsonProperty("distributeurTech")
    public String getDistributeurTech() {
        return distributeurTech;
    }

    @JsonProperty("distributeurTech")
    public void setDistributeurTech(String distributeurTech) {
        this.distributeurTech = distributeurTech;
    }

    @JsonProperty("domaineEnseignement")
    public List<DomaineEnseignement> getDomaineEnseignement() {
        return domaineEnseignement;
    }

    @JsonProperty("domaineEnseignement")
    public void setDomaineEnseignement(List<DomaineEnseignement> domaineEnseignement) {
        this.domaineEnseignement = domaineEnseignement;
    }

    @JsonProperty("idEditeur")
    public String getIdEditeur() {
        return idEditeur;
    }

    @JsonProperty("idEditeur")
    public void setIdEditeur(String idEditeur) {
        this.idEditeur = idEditeur;
    }

    @JsonProperty("idRessource")
    public String getIdRessource() {
        return idRessource;
    }

    @JsonProperty("idRessource")
    public void setIdRessource(String idRessource) {
        this.idRessource = idRessource;
    }

    @JsonProperty("idEtablissement")
    public List<IdEtablissement> getIdEtablissement() {
        return idEtablissement;
    }

    @JsonProperty("idEtablissement")
    public void setIdEtablissement(List<IdEtablissement> idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    @JsonProperty("idType")
    public String getIdType() {
        return idType;
    }

    @JsonProperty("idType")
    public void setIdType(String idType) {
        this.idType = idType;
    }

    @JsonProperty("niveauEducatif")
    public List<NiveauEducatif> getNiveauEducatif() {
        return niveauEducatif;
    }

    @JsonProperty("niveauEducatif")
    public void setNiveauEducatif(List<NiveauEducatif> niveauEducatif) {
        this.niveauEducatif = niveauEducatif;
    }

    @JsonProperty("nomEditeur")
    public String getNomEditeur() {
        return nomEditeur;
    }

    @JsonProperty("nomEditeur")
    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    @JsonProperty("nomRessource")
    public String getNomRessource() {
        return nomRessource;
    }

    @JsonProperty("nomRessource")
    public void setNomRessource(String nomRessource) {
        this.nomRessource = nomRessource;
    }

    @JsonProperty("sourceEtiquette")
    public String getSourceEtiquette() {
        return sourceEtiquette;
    }

    @JsonProperty("sourceEtiquette")
    public void setSourceEtiquette(String sourceEtiquette) {
        this.sourceEtiquette = sourceEtiquette;
    }

    @JsonProperty("typePedagogique")
    public List<Object> getTypePedagogique() {
        return typePedagogique;
    }

    @JsonProperty("typePedagogique")
    public void setTypePedagogique(List<Object> typePedagogique) {
        this.typePedagogique = typePedagogique;
    }

    @JsonProperty("typePresentation")
    public TypePresentation getTypePresentation() {
        return typePresentation;
    }

    @JsonProperty("typePresentation")
    public void setTypePresentation(TypePresentation typePresentation) {
        this.typePresentation = typePresentation;
    }

    @JsonProperty("typologieDocument")
    public List<TypologieDocument> getTypologieDocument() {
        return typologieDocument;
    }

    @JsonProperty("typologieDocument")
    public void setTypologieDocument(List<TypologieDocument> typologieDocument) {
        this.typologieDocument = typologieDocument;
    }

    @JsonProperty("urlAccesRessource")
    public String getUrlAccesRessource() {
        return urlAccesRessource;
    }

    @JsonProperty("urlAccesRessource")
    public void setUrlAccesRessource(String urlAccesRessource) {
        this.urlAccesRessource = urlAccesRessource;
    }

    @JsonProperty("urlVignette")
    public String getUrlVignette() {
        return urlVignette;
    }

    @JsonProperty("urlVignette")
    public void setUrlVignette(String urlVignette) {
        this.urlVignette = urlVignette;
    }

    @JsonProperty("validateurTech")
    public String getValidateurTech() {
        return validateurTech;
    }

    @JsonProperty("validateurTech")
    public void setValidateurTech(String validateurTech) {
        this.validateurTech = validateurTech;
    }

	/**
	 * @return the favorite
	 */
    @JsonIgnore
	public boolean getFavorite() {
		return favorite;
	}

	/**
	 * @param favorite the favorite to set
	 */
    @JsonIgnore
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	/**
	 * Getter de la propriété idInterne
	 * @return la propriété idInterne
	 */
    @JsonIgnore
	public int getIdInterne() {
		return idInterne;
	}

	/**
	 * Setter de la propriété idInterne
	 * @param idInterne id interne
	 */
    @JsonIgnore
	public void setIdInterne(int idInterne) {
		this.idInterne = idInterne;
	}

}
