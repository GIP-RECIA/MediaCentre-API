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
package fr.recia.mediacentre.mediacentre.dao.impl.mock;

import fr.recia.mediacentre.mediacentre.dao.PreferenceResource;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Service
@Profile("mock")
@Slf4j
public class MockPreferenceResourceImpl implements PreferenceResource {


	private List<String> favoris = new ArrayList<String>();

	public MockPreferenceResourceImpl() {
		favoris.add("http://n2t.net/ark:/99999/RSF000006");
	}

	@Override
    public List<String> getUserFavorites(SoffitHolder soffit) {
		//List<String> mediacentreFavorites = soffit.getUserInfo("favorites");
		List<String> mediacentreFavorites = new ArrayList<>();
		Assert.hasText(mediacentreFavorites.toString(), "No Favorite Codes user info key configured !");

		//final String[] favorite = System.getProperty("mediacentre.userFavorite", "http://n2t.net/ark:/99999/RSF000001").split(SPLIT_SEP);

		return favoris;
    }

	@Override
    public void addToUserFavorites(SoffitHolder soffit, @NotNull final String idFavorite) {
    	favoris.add(new String(idFavorite));
    	log.debug("addToUserFavorites" + favoris);
    }

    @Override
    public void removeToUserFavorites(SoffitHolder soffit, @NotNull final String idFavorite) {
    	int i = favoris.indexOf(idFavorite);
    	if (i>=0) {
    		favoris.remove(i);
    	}
    	log.debug("removeToUserFavorites" + favoris);
    }
}
