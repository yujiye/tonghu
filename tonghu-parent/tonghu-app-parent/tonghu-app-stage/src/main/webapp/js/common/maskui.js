/*
	author: montage  date: 2012/08/01
	method:
		$.maskUI.open(elem,options,follow,close);
		@elem 弹出的对象，可以传id、DOM对象或jquery对象.如果不填则弹出loading
		@options 半透明层和弹出层的css
		@follow 如果为true则设置弹出层为absolute，默认为fixed
		@close 如果设置为true则点击半透明层可关闭蒙窗
		
		$.unmaskUI(time) 关闭弹出层
		@time 淡出的时间 默认为200ms
*/
;(function($, window){
	var doc = window.document,
			defaultIndex = 888, 
			defaults = {
				message: 'Loading...',
				css: {
					position: 'fixed',
					zIndex: defaultIndex + 1
				},
				overlayCss: {
					backgroundColor: '#000',
					opacity: 0.6,
					position: 'fixed',
					left: 0,
					top: 0,
					zIndex: defaultIndex,
					width: '100%',
					height: '100%'
				},
				loadingCss: {
					width: '100px',
					padding: '10px',
					backgroundColor: 'transparent',
					color: '#fff'
				}
			},
			queue = [],
			loading = '<div id="maskLoading" style="display:none">' +
								'<img src="images/maskloading.gif" width="16" height="16" style="float:left;margin-right:10px;" />' +
								'Loading...</div>';
	
	var  viewport = function(){
					var viewPortWidth = window.innerWidth || doc.documentElement.clientWidth || doc.body.clientWidth,
							viewPortHeight = window.innerHeight || doc.documentElement.clientHeight || doc.body.clientHeight;
					return {
						viewW: viewPortWidth,
						viewH: viewPortHeight
					}
				},
				queueClear = function(){
					for(var i = 0, len = queue.length; i<len; i++){
						queue[i].hide();
					}
					
					var overlay = $('#maskOverlay');
					if(overlay.length > 0){
						overlay.unbind('click');
					}
				},
				resizeHandler = function(){
					var v = viewport(),
							w = v.viewW,
							h = v.viewH;
					for(var i = 0, len = queue.length; i<len; i++){
						var target = queue[i];
						//如果已经隐藏 则不需要处理window.resize事件
						if(!target.is(':hidden')){
							target.css({left: Math.floor((w - target.outerWidth())/2) + 'px', top: Math.floor((h - target.outerHeight())/2) + 'px'});
						}
					}
				};
		
	
	$.maskUI =  (function(){
		var maskui = function(){};
		
		maskui.prototype = {
			open: function(el, o, follow, cls){
				var _ui,
						_opts = o || {},
						_overlayCss = $.extend({}, defaults.overlayCss, (_opts.overlayCss || {})),
						_css = $.extend({}, defaults.css, (_opts.css || {}))
						_pos = follow || false,
						_cls = cls || false;
				
				if(typeof el === 'undefined'){
					if($('#maskLoading').length == 0){
						$(doc).find('body').append(loading);
					}
					_ui = $('#maskLoading');
				}else if(typeof el === 'string'){
					_ui = $('#' + el);
				}else if(typeof el === 'object' && el.nodeType && el.nodeType == 1){
					_ui = $(el);
				}else if(typeof el === 'object' && el.jquery && el.length > 0){
					_ui = el;
				}else{
					return false;
				}

				this.ui = _ui;
				this.css = _css;
				this.overlayCss = _overlayCss;
				this.view = viewport();

				this.create();
				this.content(_pos, _cls);
			},
			create: function(bool){
				var overlay = $('#maskOverlay');
				if(overlay.length > 0){
					overlay.show();
				}else{
					var self = this,
							overlay = $('<div/>');
					overlay.attr('id', 'maskOverlay').css(self.overlayCss);
					$(doc).find('body').append(overlay);
				}
			},
			content: function(bool, bool2){
				var self = this,
						ui = this.ui,
						w = ui.outerWidth(),
						h = ui.outerHeight(),
						_css = {
							left: Math.floor((self.view.viewW - w)/2) + 'px',
							top: Math.floor((self.view.viewH - h)/2) + 'px',
							position: bool ? 'absolute' : 'fixed'
						};
				ui.css($.extend({}, this.css, _css));
				queueClear();
				ui.show();
				queue.push(ui);
				
				if(bool2){
					$('#maskOverlay').bind('click',function(){
						$.unmaskUI();
					});
				}
				
				$(window).bind('resize',function(){
					resizeHandler();
				});
			}
		}
		return new maskui();
	})();
	
	$.unmaskUI = function(t){
		var _t = t || 200;
		$('#maskOverlay').fadeOut(_t);
		queueClear();
		$(window).unbind('resize',function(){
			resizeHandler();
		});
	};
	
	$(function(){
		$('.maskuiclose').click(function(){
			$.unmaskUI();
		});
	});
})(jQuery, window);