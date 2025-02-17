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
package fr.recia.mediacentre.api.model.pojo;

import lombok.Data;

@Data
public class GestionAffectation {

  private String id;
  private boolean link;
  private String title;
  private String description;
  private String regexp;

  @Override
  public String toString(){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("id: ");
    stringBuilder.append(id);
    stringBuilder.append(",\n");

    stringBuilder.append("link: ");
    stringBuilder.append(link);
    stringBuilder.append(",\n");

    stringBuilder.append("title: ");
    stringBuilder.append(title);
    stringBuilder.append(",\n");

    stringBuilder.append("description: ");
    stringBuilder.append(description);
    stringBuilder.append(",\n");

    stringBuilder.append("regexp: ");
    stringBuilder.append(regexp);
    stringBuilder.append("\n");


    return stringBuilder.toString();
  }

}
