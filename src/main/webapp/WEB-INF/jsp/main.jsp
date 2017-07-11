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

<!-- i18n messages -->
<spring:message code="resource.action.favorite" var="msgResourceActionFavorite" />
<spring:message code="resource.action.view" var="msgResourceActionView" />
<spring:message code="resource.action.describe" var="msgResourceActionDescribe" />
<span class="mediacentrePortletMessages">
    <span class="resourceActionFavorite">${msgResourceActionFavorite}</span>
    <span class="resourceActionView">${msgResourceActionView}</span>
    <span class="resourceActionDescribe">${msgResourceActionDescribe}</span>
</span>
	


<%-- <div id="mediacentre_${n}" class="mediacentre"> --%>

<%-- 	<portlet:renderURL var="viewUrl" portletMode="view"/> --%>

<%-- 	 <c:if test="not empty msg" ><c:out value="msg"/></c:if> --%>
<%-- <portlet:defineObjects />
 
<portlet:renderURL var="renderURL">
    <portlet:param name="action" value="showForm"/>
    <portlet:param name="hello" value="world"/>
</portlet:renderURL>
 
<h3> Spring Form Submission Demo</h3>
 
<br/><br/>
 
<a href="<%=renderURL.toString()%>">Go to Form Page</a> --%>

			
		
<div class="container-fluid">
    <!-- copy from here -->
    <div class="row">
        <div class="col-xs-12 col-md-3 col-lg-2 filters">
            <div class="filters-group">
                <div class="filters-heading">
                    <a data-toggle="collapse" href="#collapse-full">
                        <span>Filtres</span>
                        <i class="mdi mdi-24px mdi-menu-down pull-right"></i>
                        <i class="mdi mdi-close pull-right"></i>
                    </a>
                </div>
                <div id="collapse-full" class="filters-list collapse">
                
					<c:forEach var="categorie" items="${categoriesFiltre}" >
	                    <ul class="form-group filter ${categorie.id}">
	                        <a data-toggle="collapse" href="#collapse-${categorie.id}" class="filter-title">
	                        <span>${categorie.libelle} </span>
	                        <i class="mdi mdi-24px mdi-menu-down pull-right"></i></a>
	                        <div id="collapse-${categorie.id}" class="filter-options collapse">
	                        	
	                        	<c:forEach var="filtre" items="${categorie.filtres}" >
	                        	
	                        		<c:if test="${not categorie.valeursMultiples}">
			                            <li class="radio withripple">
			                                <label><input type="radio" name="options_${categorie.id}" id="${filtre.id}" value="${filtre.id}" ${filtre.actif ? 'checked' : ''}>${filtre.libelle}</label>
			                            </li>
		                            </c:if>
		                            
		                            <c:if test="${categorie.valeursMultiples}">
										<li class="checkbox withripple">
	                                		<label><input type="checkbox" id="${filtre.id}" value="${filtre.id}" ${filtre.actif ? 'checked' : ''}>${filtre.libelle}</label>
	                                	</li>		      
                                	</c:if>                      
	                            </c:forEach>
	                        </div>
	                    </ul>
		
					</c:forEach>

                </div>
            </div>
        </div>

        <div class="col-xs-12 col-md-offset-3 col-md-9 col-lg-offset-2 col-lg-10 grid">
            <div class="container-fluid">
               	<c:forEach var="ressource" items="${ressources}" >
	                <div class="col-xs-12 col-sm-6 col-lg-4">
	                    <div class="res-card">
	                        <div class="action-zone">
	                            <a href="javascript:void(0);" title="<spring:message code="resource.add.favorite" />" class="btn btn-primary add-to-fav">
	                                <i class="mdi mdi-star-outline"></i>
	                                <i class="mdi mdi-star"></i>
	                            </a>
	                            <div class="pull-right">
	                                <a href="${ressource.urlAccesRessource}" target="_blank" title="<spring:message code="resource.view" />" class="btn btn-primary launch-res">
	                                    <i class="mdi mdi-launch"></i>
	                                </a>
	                                <a href="javascript:void(0);" title="<spring:message code="resource.view.derscription" />" class="btn btn-primary show-more">
	                                    <i class="mdi mdi-eye"></i>
	                                </a>
	                            </div>
	                        </div>
	                        <div class="res-block-infos">
	                            <span class="res-title">${ressource.nomRessource}</span>
	                            <span class="res-txt">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut efficitur ligula fermentum arcu sollicitudin, sed porta nulla condimentum. Praesent scelerisque, nisi non maximus viverra, magna velit tempor dolor, non tincidunt leo ligula ac ex. Quisque in magna id lectus accumsan imperdiet. Fusce at laoreet sem. Pellentesque convallis vestibulum metus.</span>
	                            <div class="res-tags">
	                                <span class="label label-danger">curiosité</span>
	                                <span class="label label-success">ressource collectivité</span>
	                                <span class="label label-fav">favoris</span>
	                            </div>
	                        </div>
	                        <div class="res-img img-placeholder">
	                            <i class="mdi ${ressource.urlVignette=='' ? 'mdi-file-outline' : ''}"><c:if test="${ressource.urlVignette!=''}"><img src="${ressource.urlVignette}"></c:if></i>
	                        </div>
	                    </div>
	                </div>
            	</c:forEach>
            </div>
        </div>
    </div> <!-- /row -->
</div> <!-- /container -->



<%@include file="/WEB-INF/jsp/scripts.jsp"%>