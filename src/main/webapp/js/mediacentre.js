/*
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
var mediacentre = mediacentre || {};


mediacentre.init = function($, namespace, portletId) {


    (function initContainer($, namespace, portletId) {
        $(window).bind('load', function () {
               alert($,namespace,portletId);
               checkAllChild();
               checkParent();
               collapseAuto();
        });

    })($, namespace, portletId);

    function alert(jqueryinstance, cssNamespace, idPortlet) {
        jqueryinstance(cssNamespace + ' #mydiv_' + idPortlet).on('click', function (e) {
            alert("click on " + cssNamespace + ' #mydiv_' + idPortlet)
        });
    }
    
	function checkAllChild(){
		$('.caseSelectAll').click(function(e){
			var nameGroup = this.name;
			var index = nameGroup.indexOf('.', 0);
			var firstElement = nameGroup.substring(0,index);
	        var caseFilterACocher = $('input:checkbox[name^="' + firstElement + '"].caseAutreFiltre');
			if(this.checked){
				caseFilterACocher.prop('checked', true); 
			}else{
				caseFilterACocher.prop('checked', false);
			}
			submitForm();
	    });
	};

	function checkParent(){
		// en cochant une case enfant on coche ou décoche la Checkbox parent
		$('.caseAutreFiltre').click(function(e) {
			var nameGroup = this.name;
			var index = nameGroup.indexOf('.', 0);
			var firstElement = nameGroup.substring(0,index);
			
			// si la case parent est cochée mais que celle que nous venons de cocher ne l'est pas (plus) : on décoche la case parent
			if( ($('input:checkbox[name^="' + firstElement + '"].caseSelectAll').is(':checked') == true ) && ($(this).is(':checked') == false) ){
				$('input:checkbox[name^="' + firstElement + '"].caseSelectAll').prop('checked', false);
			}
			// l'attribut checked est true si on vient de cliquer sur une case à cocher enfant
			if (this.checked == true){// on définit notre valeur à true
			      var val = true;
			      // on boucle pour vérifier si une case à cocher n'est pas décochée : checked renvoie false
			      $('input:checkbox[name^="' + firstElement + '"].caseAutreFiltre').each(
			    	function(){ // si renvoie false on attribue notre nouvelle valeur à false
			    		if (this.checked == false)
			    			val = false;
			    	 }
			      );
			      // on assigne au checkbox parent la bonne valeur : true si toutes sont cochées, false si au moins une checkbox est décochée
			      $('input:checkbox[name^="' + firstElement + '"].caseSelectAll').prop('checked', val);
		      }
			submitForm();
		});
	};
	
	function submitForm(){
		$("#categorieFiltreModel").submit();
	};
	
	function collapseAuto(){
		$('.filter-title').click(function(e) {
			var nameGroup = this.name;
			var index = nameGroup.indexOf('-', 0);
			var nameCategorie = nameGroup.substring(index + 1);
			var caseHiddenCheckbox = $('input:checkbox[name="' + nameCategorie + '.categorieExpended"]');
			if($('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').is(':checked') == true ){
				$('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').prop('checked', false);
			}else{
				$('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').prop('checked', true);
			}
		});
	};

};

jQuery(document).ready(function($)
{
	$(function(){
		$('.caseSelectAll').click(function(e){
			var nameGroup = this.name;
			var index = nameGroup.indexOf('.', 0);
			var firstElement = nameGroup.substring(0,index);
	        //var caseFilterACocher = $('input:checkbox[name="' + nameGroup + '"].caseAutreFiltre');
			var caseFilterACocher = $('input:checkbox[name^="' + firstElement + '"].caseAutreFiltre');
			if(this.checked){
				caseFilterACocher.prop('checked', true); 
			}else{
				caseFilterACocher.prop('checked', false);
			}
			submitForm();
	    });
	});

	$(function(){
		// en cochant une case enfant on coche ou décoche la Checkbox parent
		$('.caseAutreFiltre').click(function(e) {
			var nameGroup = this.name;
			var index = nameGroup.indexOf('.', 0);
			var firstElement = nameGroup.substring(0,index);
			
			// si la case parent est cochée mais que celle que nous venons de cocher ne l'est pas (plus) : on décoche la case parent
			if( ($('input:checkbox[name^="' + firstElement + '"].caseSelectAll').is(':checked') == true ) && ($(this).is(':checked') == false) ){
				$('input:checkbox[name^="' + firstElement + '"].caseSelectAll').prop('checked', false);
			}
			// l'attribut checked est true si on vient de cliquer sur une case à cocher enfant
			if (this.checked == true){// on définit notre valeur à true
			      var val = true;
			      // on boucle pour vérifier si une case à cocher n'est pas décochée : checked renvoie false
			      $('input:checkbox[name^="' + firstElement + '"].caseAutreFiltre').each(
			    	function(){ // si renvoie false on attribue notre nouvelle valeur à false
			    		if (this.checked == false)
			    			val = false;
			    	 }
			      );
			      // on assigne au checkbox parent la bonne valeur : true si toutes sont cochées, false si au moins une checkbox est décochée
			      $('input:checkbox[name^="' + firstElement + '"].caseSelectAll').prop('checked', val);
		      }
			submitForm();
		});
	});
	
	function submitForm(){
		$("#categorieFiltreModel").submit();
	};
	
	
	$(function(){
		$('.filter-title').click(function(e) {
			var nameGroup = this.name;
			var index = nameGroup.indexOf('-', 0);
			var nameCategorie = nameGroup.substring(index + 1);
			var caseHiddenCheckbox = $('input:checkbox[name="' + nameCategorie + '.categorieExpended"]');
			if($('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').is(':checked') == true ){
				$('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').prop('checked', false);
			}else{
				$('input:checkbox[name="' + nameCategorie + '.categorieExpended"]').prop('checked', true);
			}
		});
	});
	
	
});

