<!-- MODAL gestion des affectations ressources-->
<div class="modal affectations">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title"><spring:message code="site.affectation.titre" /><i class="mdi mdi-close withripple"></i></h4>
        </div>
        <div class="modal-body">
           	<c:forEach var="gestionAffectation" items="${affectation}" >
	            <div class="website">
	                <a href="<spring:message code="${gestionAffectation.lien}" />" title="<spring:message code="site.affectation.titre" />" target="_blank">
	                    <div class="website-name"><spring:message code="${gestionAffectation.nom}" /><i class="mdi mdi-24px mdi-launch"></i></div>
	                    <div class="website-description"><spring:message code="${gestionAffectation.description}" /></div>
	                </a>
	            </div>
			</c:forEach>
            
        </div>
        <div class="modal-footer">
            <a class="btn btn-primary"><spring:message code="site.affectation.fermer" /></a>
        </div>
    </div>
</div>
<div class="overlay"></div>
