/**
 * Created by liyy on 2014/9/04.
 * 
 * joy jquery plugin base on webuploader
 */
(function(window, joy, $, undefined) {
    joy.jqplugin("joyuploader", function(){
        $.joyuploader = function(element, pluginKey, options){
            this.pluginKey = pluginKey;
            this.$wrap = this.$el = $(element);
            this._create( options );
        };

        $.extend($.joyuploader, {
            defaults: {
                thumbWidth: 110,
                thumbHeight: 110,
                // 文件上传成功后的处理
                onFileUpload: function(file, response){
                },
                // 删除上传成功文件时的处理
                onFileRemove: function(file){
                }
            },
            prototype: {
                _create: function(options){
                    if ( !WebUploader.Uploader.support() ) {
                        alert( '上传组件不支持您的浏览器！如果您使用的是IE浏览器，请尝试升级 flash播放器');
                        throw new Error( 'WebUploader does not support the browser you are using.' );
                    }

                    var opts = $.extend(true, {}, $.joyuploader.defaults, options);
                    this.options = opts;

                    var html = "<div class='queueList'>\
					        <div class='placeholder'>\
					            <div class='pick'>"+(opts.pickLabel||"点击添加")+"</div>\
					            <p>"+(opts.pickTips||"")+"</p>\
					        </div>\
					        <ul class='filelist'></ul>\
					    </div>\
					    <div class='statusBar' style='display:none;'>\
					        <div class='progress'>\
					            <span class='text'>0%</span>\
					            <span class='percentage'></span>\
					        </div><div class='info'></div>\
					        <div class='btns'>\
					            <div class='pick-other'></div><div class='uploadBtn'>"+(opts.uploadLabel||"开始上传")+"</div>\
					        </div>\
					    </div>";
                    this.$wrap.addClass("joy-uploader-wrap").html(html);

                    this._init();
                },

                _init: function(){
                    var instance = this,
                        $wrap = this.$wrap,
                        opts = this.options,
                        wrapWidth = $wrap.width(),
                        wrapHeight = $wrap.height();

                    // 图片容器
                    var $queue = $wrap.find('.filelist'),
                        $statusBar = $wrap.find('.statusBar'),
                    // 文件总体选择信息。
                        $info = $statusBar.find('.info'),
                    // 上传按钮
                        $upload = $wrap.find('.uploadBtn'),
                    // 没选择文件之前的内容。
                        $placeHolder = $wrap.find('.placeholder'),
                    // 总体进度条
                        $progress = $statusBar.find('.progress').css("visibility", "hidden"),//.hide(),
                    // 添加的文件数量
                        fileCount = 0,
                    // 添加的文件总大小
                        fileSize = 0,
                    // 删除的已上传文件数量
                        fileCancelCount = 0,
                        multipleSupport = opts.multiple!==false,
                        autoUploadSupport = opts.auto===true,
                        showStatusBar = opts.showStatusBar!==false,
                    // 优化retina, 在retina下这个值是2
                        ratio = window.devicePixelRatio || 1,
                    // 缩略图大小
                        thumbWidth = opts.thumbWidth * ratio,
                        thumbHeight = opts.thumbHeight * ratio,
                        state = 'pedding',
                    // 所有文件的进度信息，key为file id
                        percentages = {},
                        supportTransition = (function(){
                            var p = document.createElement('p'),
                                s = p.style,
                                r = 'transition' in s ||
                                    'WebkitTransition' in s ||
                                    'MozTransition' in s ||
                                    'msTransition' in s ||
                                    'OTransition' in s;
                            p = s = null;
                            return r;
                        })(),
                    // WebUploader实例
                        uploader;

                    //位置、大小调整
                    $placeHolder.css({
                        paddingTop: wrapHeight/2-25
                    });
                    $queue.parent().css({
                        minHeight: wrapHeight
                    });

                    // 实例化
                    var uploaderOpts = $.extend(true, {
                        pick: {
                            id: $wrap.find('.pick')[0],
                            multiple: multipleSupport
                        },
                        dnd: $wrap.find('.queueList')[0],
                        paste: window.document.body,
                        //runtimeOrder : 'flash',
                        accept: [{
                            title: 'Images',
                            extensions: 'gif,jpg,jpeg,bmp,png',
                            mimeTypes: 'image/*'
                        },{
                            title: 'Text',
                            extensions: 'txt,js,css',
                            mimeTypes: 'text/*'
                        }],
                        thumb: {
                        },
                        // swf文件路径
                        swf: opts.BASE_URL + '/js/webuploader/Uploader.swf',
                        disableGlobalDnd: true,
                        chunked: true,
                        auto: autoUploadSupport,
                        fileNumLimit: multipleSupport?10:1,
                        fileSizeLimit: 10 * 1024 * 1024,
                        fileSingleSizeLimit: 10 * 1024 * 1024
                    }, opts.baseOpts||{});
                    uploader = WebUploader.create(uploaderOpts);

                    // 添加其他“添加文件”的按钮，
                    if(multipleSupport){
                        uploader.addButton({
                            id: $wrap.find('.pick-other')[0],
                            label: opts.pickOtherLabel||"继续添加"
                        });
                    }

                    if(autoUploadSupport)
                        $upload.hide();

                    // 当有文件添加进来时执行，负责view的创建
                    function addFile( file ) {
                        var $li = $( '<li id="' + file.id + '">' +
                                '<p class="imgWrap"></p>'+
                                '<p class="title">' + file.name + '</p>' +
                                '<p class="progress"><span></span></p>' +
                                '</li>' ),

                            $btns = $('<div class="file-panel">' +
                                '<span class="cancel">删除</span>' +
                                '<span class="rotateRight">向右旋转</span>' +
                                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                            $prgress = $li.find('p.progress span'),
                            $wrap = $li.find( 'p.imgWrap' ),
                            $info = $('<p class="error"></p>'),

                            showError = function( code ) {
                                switch( code ) {
                                    case 'exceed_size':
                                        text = '文件大小超出限制';
                                        break;

                                    case 'interrupt':
                                        text = '上传暂停';
                                        break;

                                    default:
                                        text = '上传失败，请重试';
                                        break;
                                }

                                $info.text( text ).appendTo( $li );
                            };

                        if ( file.getStatus() === 'invalid' ) {
                            showError( file.statusText );
                        } else {
                            $wrap.text( '预览中' );
                            uploader.makeThumb( file, function( error, src ) {
                                if ( error ) {
                                    $wrap.text( '不能预览' );
                                    return;
                                }
                                var img = $('<img src="'+src+'">');
                                $wrap.empty().append( img );
                            }, thumbWidth, thumbHeight );

                            percentages[ file.id ] = [ file.size, 0 ];
                            file.rotation = 0;
                        }

                        file.on('statuschange', function( cur, prev ) {
                            if ( prev === 'progress' ) {
                                $prgress.hide().width(0);
                            } else if ( prev === 'queued' ) {
                                //$li.off( 'mouseenter mouseleave' );
                                //$btns.remove();
                            }

                            // 成功
                            if ( cur === 'error' || cur === 'invalid' ) {
                                if(window.console)
                                    console.log( file.statusText );
                                showError( file.statusText );
                                percentages[ file.id ][ 1 ] = 1;
                            } else if ( cur === 'interrupt' ) {
                                showError( 'interrupt' );
                            } else if ( cur === 'queued' ) {
                                percentages[ file.id ][ 1 ] = 0;
                            } else if ( cur === 'progress' ) {
                                $info.remove();
                                $prgress.css('display', 'block');
                            } else if ( cur === 'complete' ) {
                                $li.append( '<span class="success"></span>' );
                            }

                            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
                        });

                        $li.on( 'mouseenter', function() {
                            $btns.stop().animate({height: 30});
                        });

                        $li.on( 'mouseleave', function() {
                            $btns.stop().animate({height: 0});
                        });

                        $btns.on( 'click', 'span', function() {
                            var index = $(this).index(), deg;
                            switch ( index ) {
                                case 0:
                                    if(file.getStatus()=="complete"){
                                        fileCancelCount++;
                                        opts.onFileRemove.call(instance, file);
                                    }
                                    uploader.removeFile( file );
                                    return;
                                case 1:
                                    file.rotation += 90;
                                    break;
                                case 2:
                                    file.rotation -= 90;
                                    break;
                            }

                            if ( supportTransition ) {
                                deg = 'rotate(' + file.rotation + 'deg)';
                                $wrap.css({
                                    '-webkit-transform': deg,
                                    '-mos-transform': deg,
                                    '-o-transform': deg,
                                    'transform': deg
                                });
                            } else {
                                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                            }
                        });
                        $li.css({
                            height: thumbHeight,
                            width: thumbWidth
                        });
                        $wrap.css({
                            height: thumbHeight,
                            width: thumbWidth,
                            lineHeight: thumbHeight+"px"
                        });
                        $li.appendTo( $queue );
                    }

                    // 负责view的销毁
                    function removeFile( file ) {
                        var $li = $('#'+file.id);

                        delete percentages[ file.id ];
                        updateTotalProgress();
                        $li.off().find('.file-panel').off().end().remove();
                    }

                    function updateTotalProgress() {
                        var loaded = 0,
                            total = 0,
                            spans = $progress.children(),
                            percent;

                        $.each( percentages, function( k, v ) {
                            total += v[ 0 ];
                            loaded += v[ 0 ] * v[ 1 ];
                        } );

                        percent = total ? loaded / total : 0;

                        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
                        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
                        updateStatus();
                    }

                    function updateStatus() {
                        var stats = uploader.getStats();
                        var text = '共' + fileCount + '个（' +
                            WebUploader.formatSize( fileSize )  +
                            '），已上传' + (stats.successNum-fileCancelCount) + '个';

                        if ( stats.uploadFailNum ) {
                            text += '，失败' + stats.uploadFailNum + '个(<a class="retry" href="#">重新上传</a>)';
                        }

                        $info.html( text );
                    }

                    function setState( val ) {
                        var file, stats;

                        if ( val === state ) {
                            return;
                        }

                        $upload.removeClass( 'state-' + state );
                        $upload.addClass( 'state-' + val );
                        state = val;

                        switch ( state ) {
                            case 'pedding':
                                $placeHolder.removeClass( 'element-invisible' );
                                $queue.parent().removeClass('filled');
                                $queue.hide();
                                $statusBar.addClass( 'element-invisible' );
                                uploader.refresh();
                                break;

                            case 'ready':
                                $placeHolder.addClass( 'element-invisible' );
                                $wrap.find( '.pick-other' ).removeClass( 'element-invisible');
                                $queue.parent().addClass('filled');
                                $queue.show();
                                if(showStatusBar)
                                    $statusBar.removeClass('element-invisible');
                                uploader.refresh();
                                break;

                            case 'uploading':
                                $wrap.find( '.pick-other' ).addClass( 'element-invisible' );
                                $progress.css("visibility", "");//.show();
                                $upload.text( '暂停上传' );
                                break;

                            case 'paused':
                                $progress.css("visibility", "");//.show();
                                $upload.text( '继续上传' );
                                break;

                            case 'confirm':
                                $progress.css("visibility", "hidden");//.hide();
                                $upload.text( opts.uploadLabel||'开始上传' );//.addClass( 'disabled' );
                                $wrap.find( '.pick-other' ).removeClass( 'element-invisible');

                                stats = uploader.getStats();
                                if ( stats.successNum && !stats.uploadFailNum ) {
                                    setState( 'finish' );
                                    return;
                                }
                                break;
                            case 'finish':
                                stats = uploader.getStats();
                                if ( stats.successNum ) {
                                    //alert( '上传成功' );
                                } else {
                                    // 没有成功的图片，重设
                                    state = 'done';
                                    location.reload();
                                }
                                break;
                        }

                        updateStatus();
                    }

                    uploader.onUploadProgress = function( file, percentage ) {
                        var $li = $('#'+file.id),
                            $percent = $li.find('.progress span');

                        $percent.css( 'width', percentage * 100 + '%' );
                        percentages[ file.id ][ 1 ] = percentage;
                        updateTotalProgress();
                    };

                    uploader.onUploadSuccess = function( file, response ) {
                        opts.onFileUpload.call(instance, file, response);
                    };

                    uploader.onFileQueued = function( file ) {
                        fileCount++;
                        fileSize += file.size;

                        if ( fileCount === 1 ) {
                            $placeHolder.addClass( 'element-invisible' );
                            if(showStatusBar)
                                $statusBar.show();
                        }

                        addFile( file );
                        setState( 'ready' );
                        updateTotalProgress();
                    };

                    uploader.onFileDequeued = function( file ) {
                        fileCount--;
                        fileSize -= file.size;

                        if ( !fileCount ) {
                            setState( 'pedding' );
                        }

                        removeFile( file );
                        updateTotalProgress();

                    };

                    uploader.on( 'all', function( type ) {
                        var stats;
                        switch( type ) {
                            case 'uploadFinished':
                                setState( 'confirm' );
                                break;

                            case 'startUpload':
                                setState( 'uploading' );
                                break;

                            case 'stopUpload':
                                setState( 'paused' );
                                break;



                        }
                    });

                    uploader.onError = function( code ) {
                        switch( code ) {
                            case 'F_DUPLICATE':
                                alert( '不要添加重复的文件：' + arguments[1].name );
                                break;
                            case 'Q_TYPE_DENIED':
                                alert( '不支持的文件类型：' + arguments[1].name );
                                break;
                            case 'Q_EXCEED_NUM_LIMIT':
                                alert( '最多添加'+uploaderOpts.fileNumLimit+'个文件' );
                                break;
                            case 'Q_EXCEED_SIZE_LIMIT':
                                alert( '最多添加总共'+(uploaderOpts.fileSizeLimit/1024/1024)+'M大小的文件' );
                                break;
                            default:
                                alert( 'Error: ' + code );
                                break;
                        }
                    };

                    $upload.on('click', function() {
                        if ( $(this).hasClass( 'disabled' ) ) {
                            return false;
                        }

                        if ( state === 'ready' ) {
                            uploader.upload();
                        } else if ( state === 'paused' ) {
                            uploader.upload();
                        } else if ( state === 'uploading' ) {
                            uploader.stop();
                        }
                    });

                    $info.on( 'click', '.retry', function() {
                        uploader.retry();
                    } );

                    $info.on( 'click', '.ignore', function() {
                        alert( 'todo' );
                    } );

                    $upload.addClass( 'state-' + state );
                    updateTotalProgress();
                },

                destroy: function(){
                    this.$wrap.empty().undelegate();
                    $.removeData(this.$el[0], pluginKey);
                }
            }
        });

        return $.joyuploader;
    });
})(window, window.joy, window.jQuery);