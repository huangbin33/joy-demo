/**
 * Created by liyy on 2014/9/9.
 * 
 */
(function(window, joy, $, undefined) {
	joy.jqplugin("joyloading", function(){

        $.joyloading = function(element, pluginKey, options){
            this.pluginKey = pluginKey;
            this.$el = $(element);
            this._create( options );
        };

        $.extend($.joyloading, {
            defaults: {
                tips: '处理中，请稍候...',
            	type: 'mask',
            	timeout: 0
            },
            prototype: {
                _create: function(options){
                    var opts = $.extend(true, {}, $.joyloading.defaults, options);
                    this.options = opts;
                    
                    var html = "<div class='loading-mask'><div class='loading-tips'></div></div>";
                    this.$el.append(html).addClass("joy-loading-wrap");
                    this.$mask = this.$el.find('.loading-mask');

                    this._init();
                },

                _init: function(){
                    var $wrap = this.$el,
                        opts = this.options,
                        w = $wrap.width(),
                        h = $wrap.height();
                    
                    this.$mask.css({
                        width: w,
                        height: h,
                        left: 0,
                        top: 0
                    });

                    var $tips =  this.$el.find('.loading-tips'),
                        tw = $tips.width(),
                        th = $tips.height();

                    $tips.css({
                        marginTop: (h/2-th/2)+"px"
                    }).html(opts.tips);
                    this.show();

                },
                
                show: function(){
                    this.$mask.show();
                },
                
                hide: function(){
                	this.$mask.hide();
                },
                
                close: function(){
                	this._destroy();
                },

                _destroy: function(){
                    this.$mask.remove();
                    $.removeData(this.$el[0], this.pluginKey);
                }
            }
        });

        return $.joyloading;
    });
})(window, window.joy, window.jQuery);


