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
<script type="text/javascript"><rs:compressJs>
	/*
	 * Don't overwrite an existing myPortletName variable, just add to it
	 */
	var ${n} = ${n} || {};

	/*
	 * Switch jQuery to extreme noConflict mode, keeping a reference to it in the myPortletName namespace
	 */
	<c:choose>
		<c:when test="${!usePortalJsLibs}">
			${n}.jQuery = jQuery.noConflict(true);
		</c:when>
		<c:otherwise>
		<c:set var="ns"><c:if test="${ not empty portalJsNamespace }">${ portalJsNamespace }.</c:if></c:set>
			${n}.jQuery = ${ ns }jQuery;
		</c:otherwise>
	</c:choose>

	/**
	 * Use an anonymous function to initialize all JavaScript for this portlet.
	 */
	(function($, namespace, portletId) {
		mediacentre.init($, namespace, portletId);
	})(${n}.jQuery, '#mediaCentrePortlet_${n}', '${n}');

</rs:compressJs></script>