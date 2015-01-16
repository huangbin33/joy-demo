/**
 * Created by liyy on 2015/1/16.
 * 
 * joy jquery plugin base on iscroll-infinite
 */
(function(window, joy, $, undefined) {
    joy.jqplugin("joyinfinitelist", function(){
        $.joyinfinitelist = function(element, pluginKey, options){
            this.pluginKey = pluginKey;
            this.$wrap = this.$el = $(element);
            this._create( options );
        };

        $.extend($.joyinfinitelist, {
            defaults: {
            	mouseWheel: true,
        		infiniteElements: '#scroller .row',
        		//infiniteLimit: 2000,
        		dataset: joy.noop,
        		dataFiller: joy.noop,
        		cacheSize: 1000
            },
            prototype: {
                _create: function(options){
                    var opts = $.extend(true, {}, $.joyinfinitelist.defaults, options);
                    this.options = opts;

                    this._init();
                },

                _init: function(){
                    var instance = this,
                        $wrap = this.$wrap,
                        opts = this.options;
                   
                    instance.scrollCmp = new IScroll('#'+$wrap.attr("id"), opts);
                    
                    $(window.document).on('touchmove', function (e) { e.preventDefault(); });
                },

                destroy: function(){
                    $.removeData(this.$el[0], pluginKey);
                }
            }
        });

        return $.joyinfinitelist;
    });
})(window, window.joy, window.jQuery);