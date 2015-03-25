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
            	rowSelector: "ul > li",
            	itemsPerPage: 20,
            	pullIndicatorClass: 'joy-scroll-indicator',
            	pullDownAllways: true,
                triggerOffset: -50,
            	text: {
        			pullDownToRefresh: "下拉刷新",
        			releaseToRefresh: "释放后刷新",
        			pullUpToLoad: "上拉加载更多",
        			loading: "加载中..."
        		},
        		loadData: function(pageNum){
        			
        		},
        		refreshData: function(){
        			
        		}
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
                        opts = this.options,
                        offset;
                   
                    var $scroller = $wrap.find(".scroller").eq(0);
                	var pullDownEl = $("<div class='pullDown "+opts.pullIndicatorClass+"'><span class='pullDownIcon'>&nbsp;</span><span class='pullDownLabel'>"
                			+opts.text.pullDownToRefresh+"</span></div>");
                	$scroller.prepend(pullDownEl);
                	var pullDownOffset = pullDownEl[0].offsetHeight;
                	
                	var pullUpEl = $("<div class='pullUp "+opts.pullIndicatorClass+"'><span class='pullUpIcon'>&nbsp;</span><span class='pullUpLabel'>"
                			+opts.text.pullUpToLoad+"</span></div>");
                	$scroller.append(pullUpEl);
                	var pullUpOffset = pullUpEl[0].offsetHeight;

                	if ($wrap.find(opts.rowSelector).length < opts.itemsPerPage) {
                		//不满一页
                		if(!opts.pullDownAllways){
                			pullDownEl.hide();
                			offset = 0;
                		}else if (!offset) {
                    		offset = pullUpOffset;
                    	}
                		pullUpEl.hide();
                	} else if (!offset) {
                		offset = pullUpOffset;
                	}

                	var myScroll = instance.scrollCmp = new IScroll('.joy-scroll-list', {
                		probeType : 1,
                		tap : true,
                		click : false,
                		preventDefaultException : {
                			tagName : /.*/
                		},
                		mouseWheel : true,
                		scrollbars : false,
                		fadeScrollbars : true,
                		interactiveScrollbars : false,
                		keyBindings : false,
                		deceleration : 0.0002,
                		startY : (parseInt(offset) * (-1))
                	});
                	myScroll.pullUpOffset = pullUpOffset;
                	
                	var pullActionDetect = {
                		count : 0,
                		limit : 10,
                		check : function(count) {
                			//console.log("pac:"+pullActionDetect.count);
                			//console.log("pac:"+myScroll.y+"--"+myScroll.maxScrollY);
                			if (count) {
                				pullActionDetect.count = 0;
                			}
                			// Detects whether the momentum has stopped, and if it has reached the end - 200px of the scroller - it trigger the pullUpAction
                			setTimeout(function() {
                                console.log(myScroll.y+"--"+myScroll.maxScrollY);
                				if (myScroll.y <= (myScroll.maxScrollY + opts.triggerOffset)
                						&& !pullUpEl.hasClass('loading') 
                						&& pullUpEl.is(":visible")) {
                					pullUpEl.addClass('loading');
                					pullUpEl.find('.pullUpLabel').html(opts.text.loading);
                					
                					if ($wrap.data('page')) {
                						var next_page = parseInt($wrap.data('page'), 10) + 1;
                					} else {
                						var next_page = 2;
                					}
                					opts.loadData.call(instance, next_page);
                					$wrap.data('page', next_page);
                				} else if (pullActionDetect.count < pullActionDetect.limit) {
                					pullActionDetect.check();
                					pullActionDetect.count++;
                				}
                			}, 200);
                		}
                	};
                	
                	/*myScroll.on('scrollStart', function() {
                	});*/
                	myScroll.on('scroll', function() {
                		console.log('scroll');
                		var moreThanOnePage = $wrap.find(opts.rowSelector).length >= opts.itemsPerPage;
                		if(opts.pullDownAllways || moreThanOnePage){
	                		if (this.y >= 5 && pullDownEl
	            					&& !pullDownEl.hasClass('flip')) {
	            				pullDownEl.addClass('flip');
	            				pullDownEl.find('.pullDownLabel').html(opts.text.releaseToRefresh);
	            				this.minScrollY = 0;
	            			} else if (this.y <= 5 && pullDownEl
	            					&& pullDownEl.hasClass('flip')) {
	            				pullDownEl.removeClass('flip');
	            				pullDownEl.find('.pullDownLabel').html(opts.text.pullDownToRefresh);
	            				this.minScrollY = -pullDownOffset;
	            			}
	                		//console.log("y:"+this.y);
                		}
                		
                		if (moreThanOnePage) {
                			pullActionDetect.check(0);
                		}
                	});
                	myScroll.on('scrollEnd', function() {
                		console.log('scrollEnd');
                		var moreThanOnePage = $wrap.find(opts.rowSelector).length >= opts.itemsPerPage;
                		if(opts.pullDownAllways || moreThanOnePage){
                			if (pullDownEl.hasClass('flip')) {
                				pullDownEl.removeClass('flip').addClass('loading');
                				pullDownEl.find('.pullDownLabel').html(opts.text.loading);
                				
                				opts.refreshData.call(instance);
                				$wrap.data('page', 1);

                				// Since "topOffset" is not supported with iscroll-5
                				$wrap.find('.scroller').css({
                					top : 0
                				});
                			}else{
                				//console.log(this.y);
            					if(this.y>parseInt(pullUpOffset)*-1)
            						setTimeout(function(){
            							myScroll.scrollTo(0, parseInt(pullUpOffset) * (-1));
            						}, 200);
                			}
                		}
                		
                		if (moreThanOnePage) {
                			// We let the momentum scroll finish, and if reached the end - loading the next page
                			pullActionDetect.check(0);
                		}
                	});

                	/*document.addEventListener('touchmove', function(e) {
                		e.preventDefault();
                	}, false);*/
                    $(window.document).on('touchmove', function (e) { e.preventDefault(); });
                },

                destroy: function(){
                	this.scrollCmp.destroy();
                	this.scrollCmp = null;
                    $.removeData(this.$el[0], pluginKey);
                },
                
                refresh: function(flag, dataLength) {
                	console.log('refresh');
                	var $wrap = this.$wrap,
                		opts = this.options,
                		myScroll = this.scrollCmp;
                	
            		myScroll.refresh();
            		if(flag!=2)
            			$wrap.data('page', 1);
            		
            		var pullDownEl = $wrap.find(".pullDown");
            		var pullUpEl = $wrap.find(".pullUp");
            		if (flag==1 && pullDownEl.hasClass('loading')) {
            			pullDownEl.removeClass('loading');
            			pullDownEl.find('.pullDownLabel').html(opts.text.pullDownToRefresh);
            			myScroll.scrollTo(0, parseInt(myScroll.pullUpOffset) * (-1), 200);
            		} else if (flag==2 && pullUpEl.hasClass('loading')) {
            			pullUpEl.removeClass('loading');
            			pullUpEl.find('.pullUpLabel').html(opts.text.pullUpToLoad);
            		}
            		
            		if ($wrap.find(opts.rowSelector).length < opts.itemsPerPage) {
                		//不满一页
            			if(!opts.pullDownAllways)
                			pullDownEl.hide();
                		pullUpEl.hide();
                	}else if(flag==2 && dataLength===0){
                		pullUpEl.hide();
                	}else{
                		pullDownEl.show();
                		pullUpEl.show();
                	}
            		
            		if(flag===undefined){
            			if(pullDownEl.is(":visible"))
        					myScroll.scrollTo(0, parseInt(myScroll.pullUpOffset) * (-1));
            			else
            				myScroll.scrollTo(0, 0);
            		}
            	}
            }
        });

        return $.joyscrolllist;
    });
})(window, window.joy, window.jQuery);