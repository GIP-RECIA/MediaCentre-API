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
package fr.recia.mediacentre.mediacentre.service.impl;

import fr.recia.mediacentre.mediacentre.dao.PreferenceResource;
import fr.recia.mediacentre.mediacentre.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import fr.recia.mediacentre.mediacentre.service.MediaCentreService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Slf4j
@Service
@NoArgsConstructor
public class MediaCentreServiceImpl implements MediaCentreService {

    @NonNull
    @Value("${url.ressources.mediacentre}")
    @Setter
    private String urlRessources;

    @Autowired
    private PreferenceResource preferenceResource;

//    @Autowired
//    private MediaUrlBuilder mediaUrlBuilder;

    @Autowired
    private MediaCentreResourceJacksonImpl mediaCentreResource;

    @Autowired
    private SoffitHolder soffit;

    @Override
    public List<String> getUserLinkedEtablissements() {
        return soffit.getUserInfo("etabIds");
    }

    @Override
    public List<String> getUserCurrentEtablissement() {
        List<String> currentEtabId = soffit.getUserInfo("currentEtabId" );
        /**if (currentEtabId.size() > 1) {
         // should not happen
         log.warn("User info has more than one value, the service will return only the first one !");
         }
         return userInfos.get(0);*/
        return currentEtabId;
    }

    @Override
    public List<String> getUserGroups() {
        return soffit.getUserInfo("groups");
    }

    @Override
    public List<String> getUserFavorites() {
        return preferenceResource.getUserFavorites(soffit);
    }

    @Override
    public boolean addToUserFavorites(@NotNull String idFavorite) {
        if (!idFavorite.isEmpty()) {
            preferenceResource.addToUserFavorites(soffit, idFavorite);
            return true;
        } else {
            log.warn("Tried to add an empty string passed as favorite !");
            return false;
        }
    }

    @Override
    public boolean removeToUserFavorites(@NotNull String favorite) {
        if (!favorite.isEmpty()) {
            preferenceResource.removeToUserFavorites(soffit, favorite);
            return true;
        } else {
            log.warn("Tried to remove an empty string passed as favorite !");
            return false;
        }
    }

    /**
     * @return the mediaUrlBuilder
     */
    /**public MediaUrlBuilder getMediaUrlBuilder() {
     return mediaUrlBuilder;
     }*/

    /**
     * @param mediaUrlBuilder the mediaUrlBuilder to set
     */
    /**public void setMediaUrlBuilder(MediaUrlBuilder mediaUrlBuilder) {
     this.mediaUrlBuilder = mediaUrlBuilder;
     }*/

//    /**
//     * @return the media Pref Url
//     */
//    public static String getPrefMediaUrl() {
//        return PREF_MEDIA_URL;
//    }

    @Override
    public List<Ressource> retrieveListRessource() {

        if (log.isDebugEnabled()) {
            log.debug("Preference mediacentre url is {}", urlRessources);
        }

        if (Objects.isNull(urlRessources) || urlRessources.trim().isEmpty() ) {
            return new ArrayList<>();
        }

        // case of url is relative
        /**String rewroteUrl = mediaUrlBuilder.transform(request, urlRessources);
         if (rewroteUrl == null || rewroteUrl.trim().isEmpty()) {
         return Lists.newArrayList();
         }

         if (log.isDebugEnabled()) {
         log.debug("After url completion mediacentreUrl is {}", urlRessources);
         }*/

        List<Ressource> listRessources = mediaCentreResource.retrieveListRessource(urlRessources,soffit.getUserInfos());

        List<String> listeFavoris = this.getUserFavorites();

        int id=1;
        for(Ressource ressource : listRessources) {
            ressource.setIdInterne(id++);
            String idRessource = ressource.getIdRessource();
            if(!idRessource.isBlank()){
                if ((listeFavoris.contains(idRessource))) {
                    ressource.setFavorite(true);
                } else {
                    ressource.setFavorite(false);
                }
            }
        }
        return listRessources;
    }
}
