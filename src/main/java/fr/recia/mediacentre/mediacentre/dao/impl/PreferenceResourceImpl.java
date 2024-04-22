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
package fr.recia.mediacentre.mediacentre.dao.impl;

import fr.recia.mediacentre.mediacentre.dao.PreferenceResource;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
@Profile("!mock")
@Slf4j
public class PreferenceResourceImpl implements PreferenceResource {

    private static final String FAVORITES_PREF = "mediacentreFavorites";

    @Override
    public List<String> getUserFavorites(SoffitHolder soffit) {
        List<String> favorites = soffit.getUserInfo("favorites");
        if (log.isDebugEnabled()) {
            log.debug("Retrieved Favorites are {}", favorites);
        }
        return favorites;
    }

    @Override
    public void addToUserFavorites(SoffitHolder soffit, @NotNull final String favorite){
        List<String> userFavorites = soffit.getUserInfo("favorites");
        userFavorites.add(favorite);
        soffit.getUserInfos().put("favorites",userFavorites);
    }

    @Override
    public void removeToUserFavorites(SoffitHolder soffit, @NotNull final String favorite) {
        List<String> userFavorites = soffit.getUserInfo("favorites");
        userFavorites.remove(favorite);
        soffit.getUserInfos().put("favorites",userFavorites);
    }
}
