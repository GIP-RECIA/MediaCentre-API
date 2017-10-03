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
 
// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }

}());

$(function() {
    // init material design
    //$.material.init();

    // Prevent block info to glitching up when launching a res
    $('.launch-res').on('click',function (e) {
        e.stopPropagation();
    });

    // Since the line-clamp only work in -webkit- based browsers
    // & dotdotdot allow to dynamically ellipsis div w/ dynamic width

        // Ellipsis block info title
        $(".res-title").dotdotdot({
            watch: "window",
            height: 48
        });
        // Ellipsis block info text
        $(".res-txt").dotdotdot({
            watch: "window",
            height: 96
        });

    // Open block infos on click on ressource img
    // $('.res-card').each(function(){
    //     $('.res-img', $(this)).click(function(e) {
    //         $('.res-card > .res-block-infos').removeClass('opened');
    //         var $resblockinfo = $(this).prev('.res-block-infos');
    //         if($resblockinfo.hasClass('opened')){
    //             $resblockinfo.removeClass('opened');
    //         }else{
    //             $resblockinfo.addClass('opened');
    //         }
    //     });
    // });

    // show modal on fab click
    var modalAffect = $('.modal.affectations');
    var modalAffectationsHeight = modalAffect.height();
    var openModalAffect = function () {
        modalAffect.addClass('opened');
        $('.overlay').addClass('is-visible');
        modalAffect.css('moz-transform', 'translateY(0)').css('ms-transform', 'translateY(0)').css('-webkit-transform', 'translateY(0)').css('transform', 'translateY(0)');
    };
    var closeModalAffect = function () {
        modalAffect.removeClass('opened');
        $('.overlay').removeClass('is-visible');
        modalAffect.css('moz-transform', 'translateY('+ modalAffectationsHeight +'px)').css('ms-transform', 'translateY('+ modalAffectationsHeight +'px)').css('-webkit-transform', 'translateY('+ modalAffectationsHeight +'px)').css('transform', 'translateY('+ modalAffectationsHeight +'px)');
    };

    modalAffect.css('moz-transform', 'translateY('+ modalAffectationsHeight +'px)').css('ms-transform', 'translateY('+ modalAffectationsHeight +'px)').css('-webkit-transform', 'translateY('+ modalAffectationsHeight +'px)').css('transform', 'translateY('+ modalAffectationsHeight +'px)');
    $('.fab-affectations').on('click', openModalAffect);
    $('.modal .mdi-close, .overlay, .modal-footer .btn-primary').on('click', closeModalAffect);

    var isMobile = {
        Android: function() {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function() {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function() {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i);
        },
        Opera: function() {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function() {
            return navigator.userAgent.match(/IEMobile/i);
        },
        any: function() {
            return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
        }
    };

    if(isMobile.any()) {

        $('.res-card', $(this)).click(function() {

            var $resblockinfo = $(this).find('.res-block-infos');

            $('.res-card > .res-block-infos').removeClass('opened');

            if($resblockinfo.hasClass('opened')){
                $resblockinfo.removeClass('opened');
            }else{
                $resblockinfo.addClass('opened');
            }
        });

    }else{
        // Open block infos on hover
        $('.res-card').on({
            mouseenter: function () {
                $(this).find('.res-block-infos').addClass('opened');
            },
            mouseleave: function () {
                $(this).find('.res-block-infos').removeClass('opened');
            }
        });
    }

});

// Bootstrap Mediaquerie are here !
(function($, viewport){

    // Execute only after document has fully loaded
    $(document).ready(function() {
        // Executes in MD+ breakpoints
        if( viewport.is('>=md') ) {
            $('.filters-list.collapse').addClass('in');
        }
        // Executes in MD- breakpoints
        if( viewport.is('<md') ) {
            $('.filters-list.collapse').removeClass('in');
        }
    });

    // Execute code each time window is resized
    $(window).resize(
        viewport.changed(function() {
            // Executes in MD+ breakpoints
            if( viewport.is('>=md') ) {
                $('.filters-list.collapse').addClass('in');
            }
            // Executes in MD- breakpoints
            if( viewport.is('<md') ) {
                $('.filters-list.collapse').removeClass('in');
            }
        })
    );

})(jQuery, ResponsiveBootstrapToolkit);