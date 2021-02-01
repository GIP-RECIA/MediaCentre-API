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
var mediacentre_plugin = mediacentre_plugin || {};

mediacentre_plugin.init = function($, namespace, refCount) {
// Avoid `console` errors in browsers that lack a console.
    (function () {
        var method;
        var noop = function () {
        };
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

    (function ($, namespace, refCount) {
        // init material design
        //$.material.init();
        $(document).ready(function () {
            // Prevent block info to glitching up when launching a res
            $(namespace + ' .launch-res').on('click', function (e) {
                e.stopPropagation();
            });

            // Since the line-clamp only work in -webkit- based browsers
            // & dotdotdot allow to dynamically ellipsis div w/ dynamic width

            // Ellipsis block info title
            $(namespace + " .res-title").dotdotdot({
                watch: "window",
                height: 48
            });
            // Ellipsis block info text
            $(namespace + " .res-txt").dotdotdot({
                watch: "window",
                height: 96
            });
            // Ellipsis block info footer
            $(namespace + " .res-footer").dotdotdot({
                watch: "window",
                height: 48
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
                modalAffect.css('moz-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('ms-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('-webkit-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('transform', 'translateY(' + modalAffectationsHeight + 'px)');
            };

            modalAffect.css('moz-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('ms-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('-webkit-transform', 'translateY(' + modalAffectationsHeight + 'px)').css('transform', 'translateY(' + modalAffectationsHeight + 'px)');
            $(namespace + ' .fab-affectations').on('click', openModalAffect);
            $('.modal .mdi-close, .overlay, .modal-footer .btn-primary').on('click', closeModalAffect);

            var isMobile = {
                Android: function () {
                    return navigator.userAgent.match(/Android/i);
                },
                BlackBerry: function () {
                    return navigator.userAgent.match(/BlackBerry/i);
                },
                iOS: function () {
                    return navigator.userAgent.match(/iPhone|iPad|iPod/i);
                },
                Opera: function () {
                    return navigator.userAgent.match(/Opera Mini/i);
                },
                Windows: function () {
                    return navigator.userAgent.match(/IEMobile/i);
                },
                any: function () {
                    return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
                }
            };

            if (isMobile.any()) {

                $(namespace + ' .res-card', $(this)).click(function () {

                    var $resblockinfo = $(this).find('.res-block-infos');

                    $(namespace + ' .res-card > .res-block-infos').removeClass('opened');

                    if ($resblockinfo.hasClass('opened')) {
                        $resblockinfo.removeClass('opened');
                    } else {
                        $resblockinfo.addClass('opened');
                    }
                });

            } else {
                // Open block infos on hover
                $(namespace + ' .res-card').on({
                    mouseenter: function () {
                        $(this).find('.res-block-infos').addClass('opened');
                    },
                    mouseleave: function () {
                        $(this).find('.res-block-infos').removeClass('opened');
                    }
                });
            }
        });

    }($,namespace, refCount));

// Bootstrap Mediaquerie are here !
    (function ($, namespace, refCount) {

        function scrollFab($, namespace) {
            var content = $(namespace);
            var bottom = $(window).height() - content.position().top - content.height() + $('html, body').scrollTop();

            if (bottom < 24) {
                $(namespace + ' .fab-affectations').css('bottom', '24px').removeClass('is-absolute');
            } else {
                $(namespace + ' .fab-affectations').css('bottom', '24px').addClass('is-absolute');
            }
        }

        function viewPortChanged($, namespace) {
            // Executes in MD+ breakpoints
            if (isDetectedDivs('md') || isDetectedDivs('lg')) {
                // close filters by default
                $(namespace + ' .filters-list.collapse').addClass('in');

            } else {
                // Executes in MD- breakpoints
                // open filters header by default
                $(namespace + ' .filters-list.collapse').removeClass('in');
            }
            //this function runs every time you are scrolling
            // set the position of filters and FAB to absolute when footer enter the viewport
            $(window).scroll(function () {
                scrollFab($, namespace);
            });
        }

        var detectionDivs = {
            'xs': $('<div class="device-xs visible-xs visible-xs-block"></div>'),
            'sm': $('<div class="device-sm visible-sm visible-sm-block"></div>'),
            'md': $('<div class="device-md visible-md visible-md-block"></div>'),
            'lg': $('<div class="device-lg visible-lg visible-lg-block"></div>')
        };

        function applyDetectionDivs($, namespace) {
            $('<div class="responsive-bootstrap-toolkit"></div>').appendTo('body ' + namespace);
            $.each(detectionDivs, function(alias){
                detectionDivs[alias].appendTo(namespace + ' .responsive-bootstrap-toolkit');
            });
        }

        function isDetectedDivs(str) {
            return detectionDivs[ str ] && detectionDivs[ str ].is(':visible');
        }

        // Execute only after document has fully loaded
        $(document).ready(function () {
            applyDetectionDivs($, namespace);
            setTimeout(function() {
                scrollFab($, namespace);
                viewPortChanged($, namespace);
                $(namespace + " .filter-options.collapse").on('shown.bs.collapse', function () {
                    scrollFab($, namespace);
                }).on('hidden.bs.collapse', function () {
                    scrollFab($, namespace);
                });
            }, 300);
        });

        // Execute code each time window is resized
        $(window).resize(function () {
                viewPortChanged($, namespace);
            }
        );

    }($, namespace, refCount));
};