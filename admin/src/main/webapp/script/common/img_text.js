/**
 * 图文混排 2017-02-22 Juliana
 */
(function(W, U) {
	"use strict";
	W.imgText = {
		/**图片处理**/
        //初始化图片
        initImgNew: function (tagContent) {
        	var content = $(tagContent).find('#img-text-temp').val();
        	var divObj = document.createElement("div");
        	divObj.innerHTML = content;        	
            var imgs = $(divObj).find('img');
            for (var i = 0; i < imgs.length; i++) {
                var img = imgs[i];
                var src = $(img).attr('src');
                if (src.indexOf('http://') != -1 || src.indexOf('https://') != -1) {
                    $(img).attr('data-original', src);
                } else {
                    if (src.indexOf('data:image/jpeg;base64') == -1) {
                        $(img).attr('data-original', window.serverUrl + src);
                    } else {
                        $(img).attr('data-original', src);
                    }
                }
                $(img).addClass('lazy-img');
                $(img).attr('onload', 'imgText.changeWH(this)');
                $(img).attr('onerror', 'imgText.errorImg(this)');
                $(img).attr('name', 'newsPhoto');
                $(img).attr('src', window.serverUrl+'/image/img_bg.png');
                $(img).parent().css('text-align', 'center');
                // $(img).css('width', '100%');
            }
            $(tagContent)[0].appendChild(divObj);
            $(tagContent).find('#img-text-temp').remove();
            $(tagContent).fadeIn();
            
        },
        changeWH: function (img) {
            var width = $(img).closest('.img-text-content')[0].clientWidth;
            img.style.maxWidth = width * 0.9 + "px";
            img.style.maxHeight = (width * 0.9 / img.naturalWidth * img.naturalHeight) + "px";
        },
        errorImg: function(img) {
            img.src = window.serverUrl+'/image/404.png';
        }
	}
}(window,undefined));

$.fn.picLazyLoad = function (settings) {
	var $this = $(this), _winScrollTop = 0, _winHeight = $(window).height();
	settings = $.extend({
		threshold: -100,
		placeholder: window.serverUrl+'/image/img_bg.png',
		parent: $('.img-text-content')
	}, settings);
	lazyLoadPic();
	$(window).on('scroll', function () {
		_winScrollTop = $(window).scrollTop();
		lazyLoadPic();
	});
	function lazyLoadPic() {
		$this.each(function () {
			var $self = $(this);
			if ($self.is('img')) {
				if ($self.attr('data-original')) {
					var _offsetTop = $self.offset().top;
					var rect = settings.parent[0].getBoundingClientRect();
					if (_offsetTop <= rect.height + settings.parent.offset().top) {
						if ((_offsetTop - settings.threshold) <= (_winHeight + _winScrollTop)) {
							var realUrl = $self.attr('data-original');
							$self.attr('src', realUrl);
							$self.removeAttr('data-original');
						}
					}
				}
			} else if ($self.attr('data-original')) {
				if ($self.css('background-image') == 'none') {
					$self.css('background-image', 'url(' + settings.placeholder + ')');
				}
				var _offsetTop = $self.offset().top;
				var rect = settings.parent[0].getBoundingClientRect();
				if (_offsetTop <= rect.height + settings.parent.offset().top) {
					if ((_offsetTop - settings.threshold) <= (_winHeight + _winScrollTop)) {
						var realUrl = $self.attr('data-original');
						$self.css('background-image', 'url(' + realUrl + ')');
						$self.removeAttr('data-original');
					}
				}
			}
		});
	}
}
