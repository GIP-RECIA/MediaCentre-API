
package org.esco.portlet.mediacentre.model.ressource;

import java.util.List;
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
    "urlSourceEtiquette",
    "urlVignette",
    "validateurTech",
    "description"
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
    private List<TypePedagogique> typePedagogique = null;
    @JsonProperty("typePresentation")
    private List<TypePresentation> typePresentation = null;
    @JsonProperty("typologieDocument")
    private List<TypologieDocument> typologieDocument = null;
    @JsonProperty("urlAccesRessource")
    private String urlAccesRessource;
    @JsonProperty("urlSourceEtiquette")
    private String urlSourceEtiquette;
    @JsonProperty("urlVignette")
    private String urlVignette;
    @JsonProperty("validateurTech")
    private String validateurTech;
    @JsonProperty("description")
    private String description;

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
    public List<TypePedagogique> getTypePedagogique() {
        return typePedagogique;
    }

    @JsonProperty("typePedagogique")
    public void setTypePedagogique(List<TypePedagogique> typePedagogique) {
        this.typePedagogique = typePedagogique;
    }

    @JsonProperty("typePresentation")
    public List<TypePresentation> getTypePresentation() {
        return typePresentation;
    }

    @JsonProperty("typePresentation")
    public void setTypePresentation(List<TypePresentation> typePresentation) {
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

    @JsonProperty("urlSourceEtiquette")
    public String getUrlSourceEtiquette() {
        return urlSourceEtiquette;
    }

    @JsonProperty("urlSourceEtiquette")
    public void setUrlSourceEtiquette(String urlSourceEtiquette) {
        this.urlSourceEtiquette = urlSourceEtiquette;
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

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

}
