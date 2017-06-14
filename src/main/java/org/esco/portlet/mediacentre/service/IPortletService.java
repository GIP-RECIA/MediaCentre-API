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
package org.esco.portlet.mediacentre.service;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.validation.constraints.NotNull;

/**
 * Created by jgribonvald on 06/06/17.
 */
public interface IPortletService {

    List<String> getUserLinkedEtablissements(@NotNull final PortletRequest portletRequest);

    String getUserCurrentEtablissement(@NotNull final PortletRequest portletRequest);

    List<String> getUserGroups(@NotNull final PortletRequest portletRequest);

    String getCurrentUserId (@NotNull final PortletRequest portletRequest);

    List<String> getUserFavorites(@NotNull final PortletRequest portletRequest);

    /** Best method call to obtain a user attribute value. */
    List<String> getUserInfoOnAttribute(@NotNull final PortletRequest portletRequest, @NotNull final String attributeKey);

    /** Should be used to share user info. */
    Map<String, List<String>> getUserInfos(@NotNull final PortletRequest portletRequest);

    void setAndSaveUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final List<String> favorites);

    void addToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite);

    void removeToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite);

}
