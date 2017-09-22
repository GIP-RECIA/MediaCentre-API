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

//----------------------------------
// Initialisation des callbacks
//----------------------------------
var categorieFavoris = null;
$(function(){
	
	// En cochant la case "select All", on coche ou decoche les checkbox enfants
	$('.caseSelectAll').click(function(e){
		var caseFilterACocher = $('input:checkbox.' + this.name + '.caseAutreFiltre');
		if(this.checked){
			caseFilterACocher.prop('checked', true); 
		}else{
			caseFilterACocher.prop('checked', false);
		}
		afficherRessources();
	});
	

	// En cochant une case enfant on coche ou décoche la Checkbox parent
	$('.caseAutreFiltre').click(function(e) {
		var categorie = $(this).closest('ul')[0].id;
		var valeur = $('input:checkbox.' + categorie + '.caseAutreFiltre:checked').length == $('input:checkbox.' + categorie + '.caseAutreFiltre').length
		$('input:checkbox.' + categorie + '.caseSelectAll').prop('checked', valeur);
		afficherRessources()
	});

	// Initialisation de la variable paramFavoris
	for (var i in ressourcesParFiltre) {
		if (ressourcesParFiltre[i].favoris) {
			categorieFavoris = ressourcesParFiltre[i];
			break;
		}
	}

	$('.add-to-fav').click(changeFavoris);
	
	
	$('input:radio.filtreMediacentre').click(afficherRessources);
	afficherRessources();
});


//---------------------------------------------------
// fonction calculant l'intersection de tableaux
//---------------------------------------------------
function intersection() {
	  return Array.from(arguments).reduce(function(previous, current){
		return previous.filter(function(element){
		  return current.indexOf(element) > -1;
		});
	  });
	};

//---------------------------------------------------
// fonction calculant l'union de tableaux
//---------------------------------------------------
function union() {
	let arr = Array.concat.apply(null, arguments);
	return arr.filter(function (val, index) {
	  return arr.indexOf(val) === index;
	});
}	

//---------------------------------------------------
// fonction renvoyant l'id des ressources 
// correspondant aux filtres
//---------------------------------------------------
function filtrer() {
	let ressourcesCandidates = [];
	for ( let categorie in ressourcesParFiltre) {
		let ressourcesParCategories = [];	
		// Recherche de toutes les ressources candidates pour une catégorie
		for (let filtre in ressourcesParFiltre[categorie]) {
			for (let i in arguments) {
				if (filtre == arguments[i]) {
					ressourcesParCategories = union (ressourcesParCategories, ressourcesParFiltre[categorie][filtre]);
				}
			}
		}
		ressourcesCandidates.push(ressourcesParCategories);
	}
	
	return intersection.apply(null, ressourcesCandidates);
}

//---------------------------------------------------
// fonction rafraichissant l'affichage des ressources 
// qui correspondent aux filtres
//---------------------------------------------------
function afficherRessources() {
	var tab = $(".filtreMediacentre:checked").toArray();
	var filtres = tab.reduce(function(arr, elt) { arr.push(elt.id); return arr; }, []);
	var ressourcesFiltrees = filtrer.apply(null, filtres);

	$(".ressource").each(function(){
		if (ressourcesFiltrees.includes(parseInt(this.id))) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}

//---------------------------------------------------
// fonction appelée lors d'une modification d'un 
// favori
//---------------------------------------------------
function changeFavoris(event) {
	event.stopPropagation();

	var id = parseInt($(this).attr("id"));
	var idRessource = $(this).parent(".action-zone").attr('id');
	
	if ($(this).hasClass('added')) {
		$(this).removeClass('added');
		supprimerFavori(idRessource);
		if (categorieFavoris != null) {
			categorieFavoris.favoris = categorieFavoris.favoris.filter(function(item) { return item != id});
			if ($('input:radio#favoris').prop('checked')) {
				afficherRessources();
			}
		}
	} else {
		$(this).addClass('added');
		ajouterFavori(idRessource);
		if (categorieFavoris != null) {
			categorieFavoris.favoris.push(id);
		}
	}
}

//---------------------------------------------------
// fonction appelée pour ajouter un favori 
//---------------------------------------------------
function ajouterFavori(id){
	$.ajax({
        type: "POST",
        url: urlAjouterFavori, 
        dataType: 'json',
         data: { 
            "id": id
        }
    });
}

//---------------------------------------------------
// fonction appelée pour supprimer un favori 
//---------------------------------------------------
function supprimerFavori(id){
	$.ajax({
     type: "POST",
     url: urlRetirerFavori, 
     dataType: 'json',
      data: { 
         "id": id
     }
 });
}
