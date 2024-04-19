/**
 * Copyright © ${project.inceptionYear} GIP-RECIA (https://www.recia.fr/)
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
package fr.recia.mediacentre.mediacentre.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

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
    "validateurTech",
    "description"
})
@Getter
@Resource
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

    @JsonProperty("description")
    private String description;

    @Setter
    @JsonIgnore
    private boolean favorite;

    @Setter
    @JsonIgnore
    private int idInterne;

}

