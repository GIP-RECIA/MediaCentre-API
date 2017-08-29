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

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.validation.constraints.NotNull;

import org.esco.portlet.mediacentre.dao.IPreferenceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.Setter;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
@Profile("mock")
public class MockPreferenceResourceImpl implements IPreferenceResource {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String SPLIT_SEP = ",";
	
	@NonNull
    @Value("${userInfo.mediacentre.favorites}")
    @Setter
    private String mediacentreFavorites;
	
	@Override
    public List<String> getUserFavorites(@NotNull final PortletRequest portletRequest) {
		Assert.hasText(this.mediacentreFavorites, "No Favorite Codes user info key configured !");
		
		final String[] favorite = System.getProperty("mediacentre.userFavorite", "http://n2t.net/ark:/99999/RSF000001").split(SPLIT_SEP);
		
		return Lists.newArrayList(favorite);
    }

    @Override
    public void setUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final List<String> favorites) {
    }

    @Override
    public void addToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite) {
    }

    @Override
    public void removeToUserFavorites(@NotNull final PortletRequest portletRequest, @NotNull final String favorite) {
    }
}
