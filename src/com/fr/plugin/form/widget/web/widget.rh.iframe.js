/**
 * @class FR.RHIframe
 * @extends FR.BaseEditor
 */
(function($){

    FR.RHIframe = FR.extend(FR.BaseEditor, {

        _defaultConfig: function () {
            return $.extend(FR.RHIframe.superclass._defaultConfig.apply(), {
                baseName: 'rh.iframe',
                baseClass: 'rh.iframe',
                widgetName: "",
                src: "",
                width: "100%",
                height: "100%",
                showOverFlowX: true,
                showOverFlowY: true
            });
        },
        _init: function () {
            FR.RHIframe.superclass._init.apply(this, arguments);
            this.initData();
            // 控件属性
            var o = this.options;

            this.editComp = this.element.css({
                width: '100%',
                height: '100%'
            });
            o.iframeName = o.widgetName || this.createNoRepeatName();
            this.$iframe = $("<iframe  name=" + o.iframeName + " id="
                + o.iframeName + ">").addClass(o.baseClass).attr({
                width : '100%',
                height : '100%',
                frameborder: 0,
                scrolling: !o.showOverFlowX && !o.showOverFlowY
                    ? 'no'
                    : 'yes'
            }).appendTo(this.editComp);
            // 这里滚动条的设置 跟子页面的设置有关
            // 如果嵌入的是我们的报表或者表单 window的尺寸都是100%那就取了iframe的大小
            // 如果报表或表单内容尺寸超过的话 就会出现滚动条 那么就改下子元素 这时一般不跨域
            this.$iframe[0].onload = function () {
                if (!o.showOverFlowX || !o.showOverFlowY) {
                    try { // 可能跨域 那就不用管了
                        if (this.contentWindow) {
                            var doc = this.contentWindow.document;
                            var cc = $('.content-container', $(doc));

                            if (cc.length > 0) {
                                setOverflow(cc);
                            } else {
                                // 有可能是表单 html和body都要设置
                                setOverflow($(doc.documentElement));
                                setOverflow($(doc.body));
                            }
                            function setOverflow(cc) {
                                if (!o.showOverFlowX) {
                                    cc.css('overflow-x', 'hidden');
                                }
                                if (!o.showOverFlowY) {
                                    cc.css('overflow-y', 'hidden');
                                }
                            }
                        }
                    } catch (e) {
                        console && console.log("error");
                    }
                }
            }

            // IE里面需要设置rowspan为1才能正常显示....否则会跳过n行不显示，chrome应该是bug，一会儿好一会儿不好
            if ($.browser.msie && (this.$iframe.parent()).is("div")
                && (this.$iframe.parent().parent()).is("td")
                && (this.$iframe.parent().parent().attr("widget")) != null
                && (this.$iframe.parent().parent().parent()).is("tr")) {
//            this.$iframe.parent().parent().attr('rowspan', '1');
                // 上面这么搞有问题的，IE7或IE8杂项下，合并单元格中的网页框控件会只显示在第一行的，改成下面
                this.$iframe.parent().height("");
            } else {
                this.$iframe.css('overflow-x', o.showOverFlowX ? 'auto' : 'hidden');
                this.$iframe.css('overflow-y', o.showOverFlowY ? 'auto' : 'hidden');
            }

            if (o.controlAttr) {
                this.setValue(o.controlAttr);
            } else if (o.src) {
                this._loadIframeByGet();
            }
            if (o.disabled) {
                this._addDisableMask();
            }
        },

        createNoRepeatName: function () {
            return this.options.baseName + Math.random();
        },

        // richer:以get的方式获取ifram的参数
        _loadIframeByGet: function () {
            var src = this.options.src, self = this;
            // 获取参数组
            if (this.options.data) {
                //bug:63869这个遍历没有必要下面都有ifelse了
                //for (var i = 0, len = this.options.data.getLength(); i < len; i++) {
                //    var params = this.options.data.getRecord(i).getContent();
                //    src = src.appendQuery(params);
                //}

                var waitForInitComplete = [];

                for (var i = 0, len = this.options.data.getLength(); i < len; i++) {
                    var params = this.options.data.getRecord(i).getContent();
                    if (typeof params == 'object' && params.widgetName && this.options.form) {
                        var w = this.options.form.getWidgetByName(params.widgetName);
                        if (w) {
                            var para = {};
                            para[params.widgetName] = w.getValue();
                            src = src.appendQuery(para);
                        } else {
                            waitForInitComplete.push(params.widgetName);
                        }
                    } else {
                        src = self._appendQuery(src, params);
                    }
                }

                if (waitForInitComplete.length > 1) {
                    var form = this.options.form;
                    form.on(FR.Events.AFTERINIT, function () {
                        for (var c = 0; c < waitForInitComplete.length; c++) {
                            var name = waitForInitComplete[c];
                            var para = {};
                            para[name] = form.getWidgetByName(name).getValue();
                            src = src.appendQuery(para);
                        }
                        self._changeIframe(src);
                    });

                }
            }
            this._changeIframe(src);
        },
        /**
         * 给url加上给定的参数
         * @param {String} src 原地址
         * @param {Object} paras 参数对象，是一个键值对对象
         * @return {String} 添加了给定参数的url
         */
        _appendQuery: function (src, paras) {
            if (!paras) {
                return src;
            }
            // 没有问号说明还没有参数
            if (src.indexOf("?") === -1) {
                src += "?";
            }
            // 如果以问号结尾，说明没有其他参数
            if (src.endWith("?") !== false) {
            } else {
                src += "&";
            }
            // 模板
            if (this.options.sourceType == 'tpl') {
                $.each(paras, function (pName, pValue) {
                    if (FR.isArray(pValue)) {
                        paras[pName] = pValue;
                    } else {
                        paras[pName] = encodeURIComponent(pValue);
                    }
                });
                src += "__parameters__=" + FR.cjkEncode(FR.jsonEncode(paras));
            } else {
                $.each(paras, function (name, value) {
                    if (typeof(name) === 'string') {
                        src += name + "=" + value + "&";
                    }
                });
            }
            return encodeURI(src);
        },

        /**
         * 添加一层遮罩层，用于屏蔽iframe内容的可用性
         * @private
         */
        _addDisableMask: function () {
            this.mask = $('<span/>').css({
                backgroundColor : '#66B9FF',
                height: this.options.height,
                width: this.options.width,
                left: this.element.offset().left
            }).appendTo(this.element);
        },

        /**
         * 设置网页框控件的地址并重新加载页面(保留原参数)
         * @param {String} v 新的地址
         */
        setValue : function(v) {
            this.options.src = v;
            this._loadIframeByGet();
        },

        /**
         * 获取网页框的src地址
         * @returns {String} 地址
         */
        getValue: function () {
            return this.$iframe.attr("src");
        },

        _dealValueWithEvents: function (src) {
            this.options.src = src;
            this._changeIframe(src);
        },

        /**
         * 改变iframe的src指向的地址
         * @param src 新的地址
         * @private
         */
        _changeIframe: function (src) {
            this.$iframe.attr("src", src);
        },

        /**
         * 加载指定的url
         * @param url 网页地址
         */
        loadUrl : function(url) {
            this.$iframe.attr("src", url);
        },

        setEnable: function (enable) {
            FR.RHIframe.superclass.setEnable.apply(this, arguments);
            if (enable) {
                if (this.mask) {
                    this.mask.remove();
                }
            } else {
                if (this.mask) {
                    this.mask.show();
                } else {
                    this._addDisableMask();
                }
            }
        },

        doResize: function (give) {
            FR.RHIframe.superclass.doResize.apply(this, arguments);
            if (this.submitForm) {
                this.submitForm.submit();
            }
        },

        /**
         * 重置网页框控件
         */
        reload: function () {
            this.options.data.clearData();
            this._loadIframeByGet();
        }
    });
    $.shortcut('rh.iframe', FR.RHIframe);
})(jQuery);