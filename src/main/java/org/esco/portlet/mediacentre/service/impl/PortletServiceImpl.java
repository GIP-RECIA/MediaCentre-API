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
package org.esco.portlet.mediacentre.service.impl;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.esco.portlet.mediacentre.dao.IPreferenceResource;
import org.esco.portlet.mediacentre.dao.IUserResource;
import org.esco.portlet.mediacentre.service.IPortletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
@NoArgsConstructor
public class PortletServiceImpl implements IPortletService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @NonNull
    @Value("${userInfo.key.etabIds}")
    @Setter
    private String etabCodesInfoKey;
    @NonNull
    @Value("${userInfo.key.currentEtabId}")
    @Setter
    private String currentEtabCodeInfoKey;
    @NonNull
    @Value("${userInfo.key.groups}")
    @Setter
    private String userGroupsInfokey;

    @Autowired
    @Setter
    private IUserResource userResource;

    @Autowired
    @Setter
    private IPreferenceResource preferenceResource;


    @Override
    public List<String> getUserLinkedEtablissements(PortletRequest portletRequest) {
        return userResource.getUserInfo(portletRequest, etabCodesInfoKey);
    }

    @Override
    public String getUserCurrentEtablissement(PortletRequest portletRequest) {
        List<String> userInfos = userResource.getUserInfo(portletRequest, currentEtabCodeInfoKey);
        if (userInfos.size() > 1) {
            // should not happen
            log.warn("User info has more than one value, the service will return only the first one !");
        }
        return userInfos.get(0);
    }

    @Override
    public List<String> getUserGroups(PortletRequest portletRequest) {
        return userResource.getUserInfo(portletRequest, userGroupsInfokey);
    }

    @Override
    public String getCurrentUserId(PortletRequest portletRequest) {
        return portletRequest.getRemoteUser();
    }

    @Override
    public List<String> getUserFavorites(PortletRequest portletRequest) {
        return preferenceResource.getUserFavorites(portletRequest);
    }

    @Override
    public void setAndSaveUserFavorites(PortletRequest portletRequest, List<String> favorites) {
        try {
            preferenceResource.setUserFavorites(portletRequest,favorites);
        } catch (ReadOnlyException e) {
            log.error("Can't modify Favorites, please watch the portlet definition");
        }
    }

    @Override
    public void addToUserFavorites(PortletRequest portletRequest, String favorite) {
        try {
            preferenceResource.addToUserFavorites(portletRequest, favorite);
        } catch (ReadOnlyException e) {
            log.error("Can't modify Favorites, please watch the portlet definition");
        }
    }

    @Override
    public void removeToUserFavorites(PortletRequest portletRequest, String favorite) {
        try {
            preferenceResource.removeToUserFavorites(portletRequest, favorite);
        } catch (ReadOnlyException e) {
            log.error("Can't modify Favorites, please watch the portlet definition");
        }
    }
}
