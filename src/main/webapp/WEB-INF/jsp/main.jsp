<%--

    Copyright © 2017 GIP-RECIA (https://www.recia.fr/)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>

<jsp:directive.include file="/WEB-INF/jsp/include.jsp" />
<rs:aggregatedResources path="skin.xml" />

<script type="text/javascript">
	// Bootstrap javascript fails if included multiple times on a page.
	// uPortal Bootstrap best practice: include bootstrap if and only if it is not present and save it to
	// portlets object. Bootstrap functions could be manually invoked via portlets.bootstrapjQuery variable.
	// All portlets using Bootstrap Javascript must use this approach.  Portlet's jQuery should be included
	// prior to this code block.
	var portlets = portlets || {};
	// If bootstrap is not present at uPortal jQuery nor a community bootstrap, dynamically load it.
	<c:choose>
		<c:when test="${usePortalJsLibs}">
		portlets.bootstrapjQuery;
		</c:when>
		<c:otherwise>
		portlets.bootstrapjQuery || document.write('<script src="rs/bootstrap/3.3.5/bootstrap.min.js"><\/script>');
		</c:otherwise>
	</c:choose>
</script>


<div id="mediacentre_${n}" class="mediacentre">

	<%-- FAB --%> 
	<c:if test="${not empty gestionAffectation}">
		<div class="modal affectations">
		    <div class="modal-content">
		        <div class="modal-header">
		            <h4 class="modal-title"><spring:message code="site.affectation.titre" /><i class="mdi mdi-close withripple"></i></h4>
		        </div>
		        <div class="modal-body">
		           	<c:forEach var="gestionAffectation" items="${gestionAffectation}" >
                        <div class="website">
                            <c:choose>
                                <c:when test="${not empty gestionAffectation.lien}">
                                    <a href="<spring:message code="${gestionAffectation.lien}" />" title="<spring:message code="site.affectation.titre" />" target="_blank">
                                        <div class="website-name"><spring:message code="${gestionAffectation.nom}" /><i class="mdi mdi-24px mdi-launch"></i></div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <div class="website-name"><spring:message code="${gestionAffectation.nom}" /></div>
                                </c:otherwise>
                            </c:choose>
                            <div class="website-description"><spring:message code="${gestionAffectation.description}" /></div>
                        </div>
                    </c:forEach>
		        </div>
		        <div class="modal-footer">
		            <a class="btn btn-primary"><spring:message code="site.affectation.fermer" /></a>
		        </div>
		    </div>
		</div>
		<div class="overlay"></div>

		<a href="javascript:void(0)" class="btn btn-primary btn-fab fab-affectations" title="<spring:message code="site.affectation.titre" />">
		    <i class="mdi mdi-settings"></i>
		</a>	
	</c:if>

	<!-- i18n messages -->
	<spring:message code="resource.action.favorite" var="msgResourceActionFavorite" />
	<spring:message code="resource.action.view" var="msgResourceActionView" />
	<spring:message code="resource.action.describe" var="msgResourceActionDescribe" />
	<span class="mediacentrePortletMessages">
	    <span class="resourceActionFavorite">${msgResourceActionFavorite}</span>
	    <span class="resourceActionView">${msgResourceActionView}</span>
	    <span class="resourceActionDescribe">${msgResourceActionDescribe}</span>
	</span>
	
	<div class="container-fluid">
	    <!-- copy from here -->
	    <div class="row">
	        <div class="col-xs-12 col-md-3 col-lg-2 filters">
	            <div class="filters-group">
	                <div class="filters-heading">
	                    <a data-toggle="collapse" href="#collapse-full">
	                        <span><spring:message code="filter.title" /></span>
	                        <i class="mdi mdi-24px mdi-menu-down pull-right"></i>
	                        <i class="mdi mdi-close pull-right"></i>
	                    </a>
	                </div>
	                <div id="collapse-full" class="filters-list collapse">
	                
						<c:forEach var="categorie" items="${categoriesFiltres}" varStatus="cat">
		                    <ul id="${categorie.id}" class="form-group filter ${categorie.id}">
		                        <a data-toggle="collapse" href="#collapse-${categorie.id}" class="filter-title" name="name-listCategorieFiltre[${cat.index}]" >
		                        <span><spring:message code="${categorie.libelle}" /></span>
		                        <i class="mdi mdi-24px mdi-menu-down pull-right"></i></a>
		                        <div id="collapse-${categorie.id}" class="filter-options collapse ${categorie.categorieExpended ? 'in' : ''}" on>
		                        	
		                        	<c:forEach var="filtre" items="${categorie.filtres}" varStatus="loop">
		                        		<c:if test="${not categorie.valeursMultiples}">
				                            <li class="radio withripple" title="<spring:message code="${filtre.libelle}" />">
				                                <label>
				                                	<input type="radio" name="${categorie.id}" id="${filtre.id}" value="" ${filtre.actif ? 'checked' : ''} class="filtreMediacentre">
													<span><spring:message code="${filtre.libelle}" /></span>
				                                </label>
				                            </li>
			                            </c:if>
			                            
			                            <c:if test="${categorie.valeursMultiples}">
											<li class="checkbox withripple" title="<spring:message code="${filtre.libelle}" />">
		                                		<label>
		                                			<input type="checkbox" name="${filtre.id}" id="${filtre.id}" ${filtre.actif ? 'checked' : ''} class="${categorie.id}  ${filtre.caseSelectAll ? 'caseSelectAll' : 'filtreMediacentre caseAutreFiltre'}" >
													<span><spring:message code="${filtre.libelle}" /></span>
		                                		</label>
		                                	</li>
	                                	</c:if>                      
		                            </c:forEach>
		                        </div>
		                    </ul>
						</c:forEach>
	                </div>
	            </div>
	        </div>
	
 	        <div class="col-xs-12 col-md-9 col-lg-10 grid">
	            
	            <div class="container-fluid">
	            	
            		<div id="msgAucuneRessource" style="display:none;"><spring:message code="${empty ressources ? 'resource.error.empty' : 'resource.error.filter'}" /></div>
	            	
	               	<c:forEach var="ressource" items="${ressources}" >
		                <div id="${ressource.idInterne}" class="col-xs-12 col-sm-6 col-lg-4 ressource"	style="display: none;">
		                    <div class="res-card">
		                        <div id="${ressource.idRessource}" class="action-zone">
		                            <a id="${ressource.idInterne}" href="javascript:void(0);" title="<spring:message code="resource.add.favorite" />" class="btn btn-primary add-to-fav ${ressource.favorite ? 'added' : ''}">
		                                <i class="mdi mdi-star-outline"></i>
		                                <i class="mdi mdi-star"></i>
		                            </a>
		                            <div class="pull-right">
		                                <a href="${ressource.urlAccesRessource}" target="_blank" title="<spring:message code="resource.view" />" class="btn btn-primary launch-res">
		                                    <i class="mdi mdi-launch"></i>
		                                </a>
		                                <a href="javascript:void(0);" title="<spring:message code="resource.view.description" />" class="btn btn-primary show-more">
		                                    <i class="mdi mdi-eye"></i>
		                                </a>
		                            </div>
		                        </div>
		                        <div class="res-block-infos">
		                            <span class="res-title">${ressource.nomRessource}</span>
		                            <span class="res-txt">
		                            	<c:if test="${not empty ressource.typePresentation}" >
		                            		<span class="sautLigne">${ressource.typePresentation.nom}</span>
		                            	</c:if>
		                            	<span class="sautLigne">${ressource.nomEditeur}</span>
		                            </span>
		                            
		                        </div>
		                        <c:choose>
									<c:when test="${(not empty ressource.urlVignette) and (ressource.urlVignette!='')}">			                        
				                        <div class="res-img img-placeholder" style="background-image: url(${ressource.urlVignette});">
				                            <i class="mdi ${ressource.urlVignette=='' ? 'mdi-file-outline' : ''}"></i>
				                        </div>
			                        </c:when>
			                        <c:otherwise>
				                        <div class="res-img img-placeholder no-vignette">
				                            <i class="mdi ${ressource.urlVignette=='' ? 'mdi-file-outline' : ''}"></i>
				                        </div>
			                        </c:otherwise>
		                        </c:choose>
		                    </div>
		                </div>
	            	</c:forEach>
	            </div>
	        </div>
	    </div> <!-- /row -->
	</div> <!-- /container -->

</div>

<%@include file="/WEB-INF/jsp/scripts.jsp"%>