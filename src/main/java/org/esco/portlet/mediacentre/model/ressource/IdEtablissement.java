
package org.esco.portlet.mediacentre.model.ressource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "UAI",
    "nom"
})
public class IdEtablissement extends AbstractJson {

    @JsonProperty("UAI")
    private String uAI;
    @JsonProperty("nom")
    private String nom;

    @JsonProperty("UAI")
    public String getUAI() {
        return uAI;
    }

    @JsonProperty("UAI")
    public void setUAI(String uAI) {
        this.uAI = uAI;
    }

    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

}
