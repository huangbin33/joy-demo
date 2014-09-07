/**
 * Created by liyy on 2014/9/04.
 * 
 * joy jquery plugin base on webuploader
 */
(function(window, joy, $, undefined) {
	joy.jqplugin("joyvalidator", ['validate'], function($validate){
        console.log($validate);

        $.joyvalidator = function(element, pluginKey, options){
            this.pluginKey = pluginKey;
            this.$el = $(element);
            this._create( options );
        };

        $.extend($.joyvalidator, {
            defaults: {

            },
            messages: {

            },
            prototype: {
                _create: function(options){
                    var opts = $.extend(true, {}, $.joyvalidator.defaults, options);
                    this.options = opts;

                    this._init();
                },

                _init: function(){
                    var instance = this,
                        opts = this.options;

                },

                destroy: function(){
                    $.removeData(this.$el[0], this.pluginKey);
                }
            }
        });

        return $.joyvalidator;
    });
})(window, window.joy, window.jQuery);


