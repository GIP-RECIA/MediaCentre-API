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
    $.material.init();

    // add-to-fav
    $('.add-to-fav').on('click',function (e) {
        if($(this).hasClass('added')){
            $(this).removeClass('added');
        }else{
            $(this).addClass('added');
        }
        // prevent block info to show up when starring a res
        e.stopPropagation();
    });
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