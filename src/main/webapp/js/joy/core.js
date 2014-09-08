/**
 * Created by liyy on 2014/8/26.
 */
(function (root, doc, undefined) {
    var msie, 
    	_joy = root.joy,
        joy = root.joy || (root.joy = {}),
        joyModule;

    msie = parseInt((/msie (\d+)/.exec(navigator.userAgent.toLowerCase()) || [])[1]);
    if (isNaN(msie)) {
        msie = parseInt((/trident\/.*; rv:(\d+)/.exec(navigator.userAgent
            .toLowerCase()) || [])[1]);
    }

    function noop() {
    }

    var version = {
        full: '1.0.0',
        codeName: 'joy-js'
    };

    function ensure(obj, name, factory) {
        return obj[name] || (obj[name] = factory());
    }

    function isFunction(value) {
        return typeof value === 'function';
    }

    function isString(value) {
        return typeof value === 'string';
    }

    function isArray(value) {
        return Object.prototype.toString.call(value) === '[object Array]';
    }

    function isWindow(obj) {
        return obj && obj.document && obj.location && obj.alert && obj.setInterval;
    }

    function isArrayLike(obj) {
        if (obj == null || isWindow(obj)) {
            return false;
        }

        var length = obj.length;

        if (obj.nodeType === 1 && length) {
            return true;
        }

        return isString(obj) || isArray(obj) || length === 0 ||
            typeof length === 'number' && length > 0 && (length - 1) in obj;
    }

    function forEach(obj, iterator, context) {
        var key;
        if (obj) {
            if (isFunction(obj)) {
                for (key in obj) {
                    if (key != 'prototype' && key != 'length' && key != 'name' && (!obj.hasOwnProperty || obj.hasOwnProperty(key))) {
                        iterator.call(context, obj[key], key);
                    }
                }
            } else if (obj.forEach && obj.forEach !== forEach) {
                obj.forEach(iterator, context);
            } else if (isArrayLike(obj)) {
                for (key = 0; key < obj.length; key++)
                    iterator.call(context, obj[key], key);
            } else {
                for (key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        iterator.call(context, obj[key], key);
                    }
                }
            }
        }
        return obj;
    }

    function extend(dst) {
        forEach(arguments, function (obj) {
            if (obj !== dst) {
                forEach(obj, function (value, key) {
                    dst[key] = value;
                });
            }
        });
        return dst;
    }

    ensure(joy, 'module', function () {
        var modules = {};
        return function module(name, config) {
            if (config === undefined)
                return modules[name];

            var config = config || {};
            return ensure(modules, name, function () {
                var moduleInstance = {
                    name: name
                };
                for (c in config) {
                    if (config.hasOwnProperty(c))
                        moduleInstance[c] = config[c];
                }
                return moduleInstance;
            });
        };
    });

    ensure(joy, 'service', function () {
        var services = {};
        return function service(name, factory) {
            if (factory == undefined)
                return services[name]|| root[name];

            if (!factory || typeof factory != "function")
                return null;

            return ensure(services, name, factory);
        };
    });

    ensure(joy, 'jqplugin', function () {
        var services = {};
        return function jqplugin(name, deps, factory) {
            if ( arguments.length === 2 ) {
                factory = deps;
                deps = null;
            }

            if ( typeof factory === 'function' ) {
                var args = [], len;
                if(deps){
                    len = deps.length || (deps = [deps]);
                    for( var i = 0; i < len; i++ ) {
                        args.push( joy.service( deps[ i ] ) );
                    }
                }

                var pluginConstructor = factory.apply(null, args);
                if(!pluginConstructor)
                    throw new Error('need provoid plugin constructor!');

                (function( factory ) {
                    if ( typeof define === "function" && define.amd ) {
                        define( ["jquery"], factory );
                    } else {
                        factory( root.jQuery );
                    }
                }(function( $ ) {
                    if(!$)
                        throw new Error('jQuery not found!');
                    var pluginKey = "joy-jqp-"+name;
                    $.fn[name] = function(options){
                        if (isString(options)) {
                            var args = Array.prototype.slice.call(arguments, 1);
                            var res;
                            this.each(function() {
                                var plugin = $.data(this, pluginKey);
                                //if options=="options" then set options... TODO
                                if (plugin && $.isFunction(plugin[options])) {
                                    var r = plugin[options].apply(plugin, args);
                                    if (res === undefined)
                                        res = r;
                                }
                            });
                            return res!==undefined?res:this;
                        }else if(options===undefined && this.length==1){
                            var plugin = $.data(this, pluginKey);
                            if(plugin)
                                return plugin;
                        }

                        this.each(function() {
                            var plugin = $.data(this, pluginKey);
                            if(!plugin)
                                plugin = $.data(this, pluginKey, new pluginConstructor(this, pluginKey, options));
                        });

                        return this;
                    };

                }));
            }
        };
    });

    function bootstrap() {

    }
    
    //publish external API
    joy.noop = noop;
    joy.version = version;
    joy.bootstrap = bootstrap;
    extend(joy, {
        'version': version,
        'bootstrap': bootstrap,
        'extend': extend,
        'forEach': forEach,
        'noop':noop,
        'isString': isString,
        'isFunction': isFunction,
        'isArray': isArray
    });

})(window, document);