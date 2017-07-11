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
package org.esco.portlet.mediacentre.dao.impl;

import java.util.List;

import javax.portlet.PortletRequest;

import com.google.common.collect.Lists;
import org.esco.portlet.mediacentre.dao.IPreferenceResource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
@Profile("mock")
public class MockPreferenceResourceImpl implements IPreferenceResource {

    @Override
    public List<String> getUserFavorites(PortletRequest portletRequest) {
        return Lists.newArrayList();
    }

    @Override
    public void setUserFavorites(PortletRequest portletRequest, List<String> favorites) {
    }

    @Override
    public void addToUserFavorites(PortletRequest portletRequest, String favorite) {
    }

    @Override
    public void removeToUserFavorites(PortletRequest portletRequest, String favorite) {
    }
}
