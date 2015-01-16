/**
 * Created by liyy on 2015/1/16.
 * 
 * joy jquery plugin base on iscroll-lite
 */
(function(window, joy, $, undefined) {
    joy.jqplugin("joyscrolllist", function(){
        $.joyscrolllist = function(element, pluginKey, options){
            this.pluginKey = pluginKey;
            this.$wrap = this.$el = $(element);
            this._create( options );
        };

        $.extend($.joyscrolllist, {
            defaults: {
            },
            prototype: {
                _create: function(options){
                    var opts = $.extend(true, {}, $.joyscrolllist.defaults, options);
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

        return $.joyscrolllist;
    });
})(window, window.joy, window.jQuery);