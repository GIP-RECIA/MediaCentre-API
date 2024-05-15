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
package fr.recia.mediacentre.mediacentre.dao;

import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
public interface PreferenceResource {

    List<Ressource> getUserFavorites(SoffitHolder soffit);

    void addToUserFavorites(SoffitHolder soffit, @NotNull final String idFavorite);

    void removeToUserFavorites(SoffitHolder soffit, @NotNull final String idFavorite);

}
