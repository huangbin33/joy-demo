/**
 * Created by liyy on 2014/9/10.
 * 
 * joy jquery plugin base on jquery.validate.js
 */
(function(window, joy, $, undefined) {
	joy.jqplugin("joyvalidator", ['validate'], function($validate){
       $.extend($.validator, {
            /*defaults: {
                ignore: ' '
            },*/
            messages: {
                required: "该项不能为空",
                remote: "该项输入有误",
                email: "请输入有效的电子邮箱",
                url: "请输入有效的网址",
                date: "请输入有效的日期",
                dateISO: "请输入有效的日期",
                number: "请输入正确的数字",
                digits: "只能输入数字",
                equalTo: "两次输入不一致",
                extension: "请输入有效的后缀",
                maxlength: $.validator.format("长度不能超过{0}"),
                minlength: $.validator.format("长度不能少于{0}"),
                rangelength: $.validator.format("长度只能是{0}到{1}之间"),
                range: $.validator.format("请输入{0}到{1}之间的数值"),
                max: $.validator.format("请输入不大于{0}的数值"),
                min: $.validator.format("请输入不小于{0}的数值")
            }
        });

        $.validator.addMethod("mobile", function(value, element) {
            return this.optional(element) || $validate.isMobile(value);
        }, "请输入有效的手机号码");

        $.validator.addMethod("phone", function(value, element) {
            return this.optional(element) || $validate.isPhone(value);
        }, "请输入有效的电话号码");

        $.validator.addMethod("email", function(value, element) {
            return this.optional(element) || $validate.isEmail(value);
        });

        $.validator.addMethod("mobileOrEmail", function(value, element) {
            return this.optional(element) || $validate.isMobile(value) || $validate.isEmail(value);
        }, "请输入有效的手机号码或电子邮箱");

        $.validator.addMethod("alphaAndDigit", function(value, element) {
            return this.optional(element) || $validate.isMatch(value, "ALPHA_DIGIT");
        }, "只能包含字母和数字");

        $.validator.addMethod("chinese", function(value, element) {
            return this.optional(element) || $validate.isChinese(value);
        }, "只能输入中文");

        $.validator.addMethod("nick", function(value, element) {
            return this.optional(element) || $validate.isNickName(value, true, [4,20]);
        }, "请输入有效的昵称（4-20个字符，只能包含中文、字母、数字和下划线）");
        
        $.validator.addMethod("idcard_strict", function(value, element) {
            return this.optional(element) || $validate.isStrictIDCard(value);
        }, "请输入有效的身份证号");

        $.validator.addMethod("business_license", function(value, element) {
            return this.optional(element) || /^\d{15}$/.test(value);
        }, "请输入有效的营业执照号");

        $.extend($.validator.defaults, {
            showErrors: function(errorMap, errorList){
                this.defaultShowErrors();
            }
        });

        $.fn.joyvalidator = function(options){
            this.each(function(){
                $(this).addClass("joy-validator-wrap");
            });

            return $.fn.validate.call(this, options);
        };

    }, true);
})(window, window.joy, window.jQuery);
