/**
 * 通用 2017-02-23 Juliana
 */


(function (W, U) {
        "use strict";
        W.serverUrl = '/admin';
        W.txz = {
            addToTopScroll: function () {
            	var n =  $('#top_back');
                $(window).scroll(function () {
                    $(window).scrollTop() > 700 ? n.css('display','block') : n.css('display','none');
                })
                
                n.click(function () {
                    return $("html,body").animate({scrollTop: 0});
                })
            }
        }
    }
    (window, undefined)
)
;