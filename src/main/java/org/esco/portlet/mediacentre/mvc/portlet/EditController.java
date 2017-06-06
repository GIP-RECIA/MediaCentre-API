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
package org.esco.portlet.mediacentre.mvc.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * EditController renders the editing interface and persists user selections
 * to the portlet's preferences.
 */
@Controller
@RequestMapping("EDIT")
public class EditController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    
    @RenderMapping
    public ModelAndView showMainView(
            final RenderRequest request, final RenderResponse response) {

        final String viewName = "edit";        
        final ModelAndView mav = new ModelAndView(viewName);
        
        if(log.isDebugEnabled()) {
            log.debug("Using view name " + viewName + " for edit view");
        }

        return mav;

    }

    @ActionMapping
    public void editPreferences() {
    }

}
