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
package fr.recia.mediacentre.mediacentre.model.allocation;

import fr.recia.mediacentre.mediacentre.model.FilterUserRight;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.Resource;


@Data
@NoArgsConstructor
@Resource
public class GestionAffectation implements FilterUserRight {

	@NonNull
	private String id;
	@NonNull
	private String nom;
	@NonNull
	private String description;
	@NonNull
	private String lien;
	@NonNull
	private String population;
	@NonNull
	private String regexpPopulation;
}
