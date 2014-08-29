/**
 * Created by liyy on 2014/8/26.
 */
(function (window, document, undefined) {
    var msie, _joy = window.joy,
        joy = window.joy || (window.joy = {}),
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
        return toString.call(value) === '[object Array]';
    }
    //Object.prototype.toString

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
                    // Need to check if hasOwnProperty exists,
                    // as on IE8 the result of querySelectorAll is an object without a hasOwnProperty function
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
            if (config == undefined)
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
        return function module(name, factory) {
            if (factory == undefined)
                return services[name];

            if (!factory || typeof factory != "function")
                return null;

            return ensure(services, name, factory);
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