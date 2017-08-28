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
package org.esco.portlet.mediacentre.dao;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.validation.constraints.NotNull;

/**
 * Created by jgribonvald on 06/06/17.
 */
public interface IPreferenceResource {

    List<String> getUserFavorites(@NotNull final PortletRequest portletRequest);

    void setUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final List<String> favorites) throws ReadOnlyException;

    void addToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite) throws ReadOnlyException;

    void removeToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite) throws ReadOnlyException ;

}
