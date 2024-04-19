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
package fr.recia.mediacentre.mediacentre.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
public class SoffitInterceptor implements HandlerInterceptor {

  private final SoffitHolder soffitHolder;

  public SoffitInterceptor(SoffitHolder soffitHolder) {
    this.soffitHolder = soffitHolder;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String path = request.getRequestURI().substring(request.getContextPath().length());
    String token = request.getHeader("Authorization");

    if (token == null) {
      log.debug("No Authorization header found");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }

    Base64.Decoder decoder = Base64.getUrlDecoder();
    String payload = new String(decoder.decode(token.replace("Bearer ", "").split("\\.")[1]));

    Map<String, String> soffit = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      soffit = objectMapper.readValue(payload, new TypeReference<>() {
      });
      soffitHolder.setSub(soffit.get("sub"));

      soffitHolder.getUserInfos().put("currentEtabId", List.of(soffit.get("ESCOSIRENCourant")));

      soffitHolder.getUserInfos().put("etabIds", List.of(soffit.get("ESCOSIREN")));

      soffitHolder.getUserInfos().put("groups", List.of(soffit.get("isMemberOf")));

      soffitHolder.getUserInfos().put("uid", List.of(soffit.get("ENTPersonGARIdentifiant")));

      soffitHolder.getUserInfos().put("profils", List.of(soffit.get("ENTPersonProfils")));

      soffitHolder.getUserInfos().put("favorites", List.of(soffit.get("mediacentreFavorites")));

    } catch (IOException ignored) {
      log.error("Unable to read soffit");
    }
    log.debug("Soffit : {}", soffit);
    return true;
  }

}
