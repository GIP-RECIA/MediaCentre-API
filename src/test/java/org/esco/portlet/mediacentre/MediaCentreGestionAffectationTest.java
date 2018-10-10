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
package org.esco.portlet.mediacentre;

import java.util.List;

import javax.annotation.Resource;

import org.esco.portlet.mediacentre.model.affectation.GestionAffectation;
import org.esco.portlet.mediacentre.service.IFiltrageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jgribonvald on 14/09/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContextGestionAffectation.xml")
public class MediaCentreGestionAffectationTest {

    @SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IFiltrageService filtrageService;

	@Resource
	private List<GestionAffectation> gestionAffectation;

    @Test
    public void testGestionAffectation() throws Exception {
		Assert.assertNotNull(filtrageService);
		Assert.assertFalse(gestionAffectation.isEmpty());
    	for (GestionAffectation gestionAffectation : gestionAffectation) {
    		log.debug(gestionAffectation.toString());
    		Assert.assertNotNull(gestionAffectation);
    	};
    }

}



