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
package fr.recia.mediacentre.mediacentre.service.impl;

import fr.recia.mediacentre.mediacentre.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.mediacentre.dao.PreferenceResource;
import fr.recia.mediacentre.mediacentre.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.mediacentre.model.filter.FilterEnum;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import fr.recia.mediacentre.mediacentre.service.LdapService;
import fr.recia.mediacentre.mediacentre.service.MediaCentreService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    LdapService ldapService;

    @Autowired
    private MediaCentreResourceJacksonImpl mediaCentreResource;

    @Autowired
    private SoffitHolder soffit;

    @Autowired
    private CategoriesByProfilesProperties categoriesByFilters;

    @Override
    public List<String> getUserLinkedEtablissements() {
        return soffit.getEtabIds();
    }

    @Override
    public String getUserCurrentEtablissement() {
        String currentEtabId = soffit.getCurrentEtabId().get(0);
        /**if (currentEtabId.size() > 1) {
         // should not happen
         log.warn("User info has more than one value, the service will return only the first one !");
         }
         return userInfos.get(0);*/
        return currentEtabId;
    }

    @Override
    public List<Ressource> getUserFavorites() {
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

    @Override
    public List<Ressource> retrieveListRessource() throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("Preference mediacentre url is {}", urlRessources);
        }

        if (Objects.isNull(urlRessources) || urlRessources.trim().isEmpty()) {
            // a changer par une exception
            return new ArrayList<>();
        }

        List<String> isMemberOf = ldapService.getIsMemberOf();

        Map<String,List<String>> userInfos = new HashMap<>();

        userInfos.put("etabIds",soffit.getEtabIds());
        userInfos.put("currentEtabId",soffit.getCurrentEtabId());
        userInfos.put("uid",soffit.getUid());
        userInfos.put("profils", Collections.singletonList(soffit.getProfil()));
        userInfos.put("isMemberOf",isMemberOf);

        List<Ressource> listRessources = mediaCentreResource.retrieveListRessource(urlRessources, userInfos);

//        List<String> listeFavoris = this.getUserFavorites();

//        int id = 1;
//        for (Ressource ressource : listRessources) {
//            ressource.setIdInterne(id++);
//            String idRessource = ressource.getIdRessource();
//            if (!idRessource.isBlank()) {
//                if ((listeFavoris.contains(idRessource))) {
//                    ressource.setFavorite(true);
//                } else {
//                    ressource.setFavorite(false);
//                }
//            }
//        }
        return listRessources;
    }

    @Override
    public List<FilterEnum> retrieveFiltersList() {
        String userProfile = soffit.getProfil();
        return getFiltersByProfile(userProfile);
    }

    private List<FilterEnum> getFiltersByProfile(String profile){
        List<CategoriesByProfilesProperties.ProfilesMap> profilesMapList = categoriesByFilters.getCategoriesByProfiles();
        for(CategoriesByProfilesProperties.ProfilesMap item : profilesMapList){
            log.info("profile mappé : {}", item);
            if(item.getProfiles().contains(profile)){
                log.info("les filtres : {}",item.getFilters());
                return item.getFilters();
            }
        }
        return new ArrayList<>();
    }
}
