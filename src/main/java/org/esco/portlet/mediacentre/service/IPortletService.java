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

import javax.portlet.PortletRequest;

/**
 * Created by jgribonvald on 06/06/17.
 */
public interface IPortletService {

    List<String> getUserLinkedEtablissements(final PortletRequest portletRequest);

    String getUserCurrentEtablissement(final PortletRequest portletRequest);

    List<String> getUserGroups(final PortletRequest portletRequest);

    String getCurrentUserId (final PortletRequest portletRequest);

    List<String> getUserFavorites(final PortletRequest portletRequest);

    void setAndSaveUserFavorites(final PortletRequest portletRequest, final List<String> favorites);

    void addToUserFavorites(final PortletRequest portletRequest, final String favorite);

    void removeToUserFavorites(final PortletRequest portletRequest, final String favorite);

}
