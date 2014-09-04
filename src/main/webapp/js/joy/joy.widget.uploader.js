/**
 * Created by liyy on 2014/9/04.
 * 
 * depend on jquery and webuploader
 */
(function($, undefined) {
	var pluginKey = "joy-uploader";

	$.fn.joyuploader = function(options) {
		if (typeof options == 'string') {
			var args = Array.prototype.slice.call(arguments, 1);
			var res;
			this.each(function() {
				var plugin = $.data(this, pluginKey);
				if (plugin && $.isFunction(plugin[options])) {
					var r = plugin[options].apply(plugin, args);
					if (res === undefined) {
						res = r;
					}					
				}
			});
			if (res !== undefined) {
				return res;
			}
			return this;
		}
		
		this.each(function() {
			var plugin = $.data(this, pluginKey);
			if(!plugin)
				plugin = $.data(this, pluginKey, new pluginConstructor(this, options));
			else
				plugin.update(options);
		});
		
		return this;
	};

	$.joyuploader = pluginConstructor = function(element, options){
		this.$wrap = this.el = $(element);
		this._create( options );
	}

	$.joyuploader.prototype = {
		_create: function(options){
			var opts = $.extend(true, {}, $.joyuploader.defaults, options);
            this.options = opts;

			var instance = this;
			
			this._init();
		},

		_init: function(){
			var opts =  this.options;
			
		},

		destroy: function(){	
			this.$wrap.empty().undelegate();		
			$.removeData(this.el[0], pluginKey);
		},

		update: function(key) {
            if ($.isPlainObject(key)) {
                this.options = $.extend({},this.options,key);
                this._init();
            }
        }
	};


})(window.jQuery);


$.joyuploader.defaults = {    
}; 
