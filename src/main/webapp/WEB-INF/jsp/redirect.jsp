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
<%--
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
--%>

<div id="mediacentre_${n}" class="mediacentre">
	<div class="container-fluid">
	    <div class="row">
 	          <div class="col-xs-12 col-md-12 col-lg-12 grid">
	            <div class="container-fluid">
                    <c:if test="${not empty redirectTo}">
                        <div id="msgAucuneRessource"><spring:message code="redirectTo.success.doIt" /><a href="${redirectTo}" target="_self" title="<spring:message code="resource.view" />"><i class="mdi mdi-24px mdi-launch"></i></a></div>
                        <script type="text/javascript">
                           window.location.replace("${redirectTo}");
                        </script>
                        </c:if>
                        <c:if test="${empty redirectTo}">
                            <div id="msgAucuneRessource"><spring:message code="redirectTo.error.empty" /></div>
                        </c:if>
	            </div>
	        </div>
	    </div> <!-- /row -->
	</div> <!-- /container -->

</div>

<%--@include file="/WEB-INF/jsp/scripts.jsp"--%>