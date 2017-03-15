/**
Core script to handle the entire layout and base functions
**/
var App = function () {

    // IE mode
    var isRTL = false;
    var isIE8 = false;
    var isIE9 = false;
    var isIE10 = false;

    var sidebarWidth = 225;
    var sidebarCollapsedWidth = 35;

    var responsiveHandlers = [];
    
    var $dialog = null,$dialog_header = null,$dialog_content = null;
    var closeModel = function(){
    	$dialog.modal('hide');
    }

    // theme layout color set
    var layoutColorCodes = {
        'blue': '#4b8df8',
        'red': '#e02222',
        'green': '#35aa47',
        'purple': '#852b99',
        'grey': '#555555',
        'light-grey': '#fafafa',
        'yellow': '#ffb848'
    };

    var handleInit = function() {

        if ($('body').css('direction') === 'rtl') {
            isRTL = true;
        }

        isIE8 = !! navigator.userAgent.match(/MSIE 8.0/);
        isIE9 = !! navigator.userAgent.match(/MSIE 9.0/);
        isIE10 = !! navigator.userAgent.match(/MSIE 10/);
        
        if (isIE10) {
            jQuery('html').addClass('ie10'); // detect IE10 version
        }
    }

    var handleDesktopTabletContents = function () {
        // loops all page elements with "responsive" class and applies classes for tablet mode
        // For metornic  1280px or less set as tablet mode to display the content properly
        if ($(window).width() <= 1280 || $('body').hasClass('page-boxed')) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forDesktop);
                    $(this).addClass(forTablet);
                }
            });
        }

        // loops all page elements with "responsive" class and applied classes for desktop mode
        // For metornic  higher 1280px set as desktop mode to display the content properly
        if ($(window).width() > 1280 && $('body').hasClass('page-boxed') === false) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forTablet);
                    $(this).addClass(forDesktop);
                }
            });
        }
    }

    var handleSidebarState = function () {
        // remove sidebar toggler if window width smaller than 900(for table and phone mode)
        if ($(window).width() < 980) {
            $('body').removeClass("page-sidebar-closed");
        }
    }

    var runResponsiveHandlers = function () {
        // reinitialize other subscribed elements
        for (var i in responsiveHandlers) {
            var each = responsiveHandlers[i];
            each.call();
        }
    }

    var handleResponsive = function () {
        handleTooltips();
        handleSidebarState();
        handleDesktopTabletContents();
        handleSidebarAndContentHeight();
        handleChoosenSelect();
        handleFixedSidebar();
        runResponsiveHandlers();
    }

    var handleResponsiveOnInit = function () {
        handleSidebarState();
        handleDesktopTabletContents();
        handleSidebarAndContentHeight();
    }

    var handleResponsiveOnResize = function () {
        var resize;
        if (isIE8) {
            var currheight;
            $(window).resize(function() {
                if(currheight == document.documentElement.clientHeight) {
                    return; //quite event since only body resized not window.
                }                
                if (resize) {
                    clearTimeout(resize);
                }   
                resize = setTimeout(function() {
                    handleResponsive();    
                }, 50); // wait 50ms until window resize finishes.                
                currheight = document.documentElement.clientHeight; // store last body client height
            });
        } else {
            $(window).resize(function() {
                if (resize) {
                    clearTimeout(resize);
                }   
                resize = setTimeout(function() {
                    handleResponsive();    
                }, 50); // wait 50ms until window resize finishes.
            });
        }   
    }

    //* BEGIN:CORE HANDLERS *//
    // this function handles responsive layout on screen size resize or mobile device rotate.
  
    var handleSidebarAndContentHeight = function () {
        var content = $('.page-content');
        var sidebar = $('.page-sidebar');
        var body = $('body');
        var height;

        if (body.hasClass("page-footer-fixed") === true && body.hasClass("page-sidebar-fixed") === false) {
            var available_height = $(window).height() - $('.footer').height();
            if (content.height() <  available_height) {
                content.attr('style', 'min-height:' + available_height + 'px !important');
            }
        } else {
            if (body.hasClass('page-sidebar-fixed')) {
                height = _calculateFixedSidebarViewportHeight();
            } else {
                height = sidebar.height() + 20;
            }
            if (height >= content.height()) {
                content.attr('style', 'min-height:' + height + 'px !important');
            } 
        }          
    }

    var handleSidebarMenu = function () {
        jQuery('.page-sidebar').on('click', 'li > a', function (e) {
                if ($(this).next().hasClass('sub-menu') == false) {
                    if ($('.btn-navbar').hasClass('collapsed') == false) {
                        $('.btn-navbar').click();
                    }
                    return;
                }

                var parent = $(this).parent().parent();

                parent.children('li.open').children('a').children('.arrow').removeClass('open');
                parent.children('li.open').children('.sub-menu').slideUp(200);
                parent.children('li.open').removeClass('open');

                var sub = jQuery(this).next();
                if (sub.is(":visible")) {
                    jQuery('.arrow', jQuery(this)).removeClass("open");
                    jQuery(this).parent().removeClass("open");
                    sub.slideUp(200, function () {
                            handleSidebarAndContentHeight();
                        });
                } else {
                    jQuery('.arrow', jQuery(this)).addClass("open");
                    jQuery(this).parent().addClass("open");
                    sub.slideDown(200, function () {
                            handleSidebarAndContentHeight();
                        });
                }

                e.preventDefault();
            });

        // handle ajax links
        jQuery('.page-sidebar').on('click', ' li > a.ajaxify', function (e) {
                e.preventDefault();
                App.scrollTop();

                var url = $(this).attr("href");
                var menuContainer = jQuery('.page-sidebar ul');
                var pageContent = $('.page-content');
                var pageContentBody = $('.page-content .page-content-body');

                menuContainer.children('li.active').removeClass('active');
                menuContainer.children('arrow.open').removeClass('open');

                $(this).parents('li').each(function () {
                        $(this).addClass('active');
                        $(this).children('a > span.arrow').addClass('open');
                    });
                $(this).parents('li').addClass('active');

                App.blockUI(pageContent, false);

                $.post(url, {}, function (res) {
                        App.unblockUI(pageContent);
                        pageContentBody.html(res);
                        App.fixContentHeight(); // fix content height
                        App.initUniform(); // initialize uniform elements
                    });
            });
    }
    
    var handleNavigateMenu = function() {
    	$('#nav').children('[data-type="nav"]').click(function() {
    		var _this = $(this);
    		if (_this.data('loading') == 'true')
    			return;

    		var url = '/silder/' + _this.attr('navigate');
    		var sidebar = $('#sidebar');
    		$.ajax({
    			url : url,
    			type : 'post',
    			timeout : 10000,
    			cache : false,
    			data : {'_ajax':'true'},
    			beforeSend : function() {
    				_this.data('loading', 'true');
    				App.blockUI(sidebar, false);
    			},
    			success : function(data) {
    				sidebar.html(data);
    			},
    			error : function(jqXHR, textStatus, errorThrown) {
    				alert('Internal server error occured. Request aborted');
    			},
    			complete : function() {
    				_this.data('loading', 'false');
    				App.unblockUI(sidebar);
    			}
    		});
    	});
    }

    var _calculateFixedSidebarViewportHeight = function () {
        var sidebarHeight = $(window).height() - $('.header').height() + 1;
        if ($('body').hasClass("page-footer-fixed")) {
            sidebarHeight = sidebarHeight - $('.footer').height();
        }

        return sidebarHeight; 
    }

    var handleFixedSidebar = function () {
        var menu = $('.page-sidebar-menu');

        if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before updating the height
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
            $('.page-sidebar').removeAttr('style');            
        }

        if ($('.page-sidebar-fixed').size() === 0) {
            handleSidebarAndContentHeight();
            return;
        }

        if ($(window).width() >= 980) {
            var sidebarHeight = _calculateFixedSidebarViewportHeight();

            menu.slimScroll({
                size: '7px',
                color: '#a1b2bd',
                opacity: .3,
                position: isRTL ? 'left' : ($('.page-sidebar-on-right').size() === 1 ? 'left' : 'right'),
                height: sidebarHeight,
                allowPageScroll: false,
                disableFadeOut: false
            });
            handleSidebarAndContentHeight();
        }
    }

    var handleFixedSidebarHoverable = function () {
        if ($('body').hasClass('page-sidebar-fixed') === false) {
            return;
        }

        $('.page-sidebar').off('mouseenter').on('mouseenter', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-closed') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            body.removeClass('page-sidebar-closed').addClass('page-sidebar-hover-on');
            $(this).addClass('page-sidebar-hovering');                
            $(this).animate({
                width: sidebarWidth
            }, 400, '', function () {
                $(this).removeClass('page-sidebar-hovering');
            });
        });

        $('.page-sidebar').off('mouseleave').on('mouseleave', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-hover-on') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            $(this).addClass('page-sidebar-hovering');
            $(this).animate({
                width: sidebarCollapsedWidth
            }, 400, '', function () {
                $('body').addClass('page-sidebar-closed').removeClass('page-sidebar-hover-on');
                $(this).removeClass('page-sidebar-hovering');
            });
        });
    }

    var handleSidebarToggler = function () {
        // handle sidebar show/hide
        $('.page-sidebar').on('click', '.sidebar-toggler', function (e) {            
            var body = $('body');
            var sidebar = $('.page-sidebar');

            if ((body.hasClass("page-sidebar-hover-on") && body.hasClass('page-sidebar-fixed')) || sidebar.hasClass('page-sidebar-hovering')) {
                body.removeClass('page-sidebar-hover-on');
                sidebar.css('width', '').hide().show();
                e.stopPropagation();
                runResponsiveHandlers();
                return;
            }

            $(".sidebar-search", sidebar).removeClass("open");
            
            var t = 0;
            if (body.hasClass("page-sidebar-closed")) {
                body.removeClass("page-sidebar-closed");
                if (body.hasClass('page-sidebar-fixed')) {
                    sidebar.css('width', '');
                }
            } else {
                body.addClass("page-sidebar-closed");
                t = 1;
            }
            
            $.post('/extra_profile/sidebar_closed',{'t':t},function(json){
            	
            },'json');
            
            runResponsiveHandlers();
        });

        // handle the search bar close
        $('.page-sidebar').on('click', '.sidebar-search .remove', function (e) {
            e.preventDefault();
            $('.sidebar-search').removeClass("open");
        });

        // handle the search query submit on enter press
        $('.page-sidebar').on('keypress', '.sidebar-search input', function (e) {
            if (e.which == 13) {
                window.location.href = "extra_search.html";
                return false; //<---- Add this line
            }
        });

        // handle the search submit
        $('.sidebar-search .submit').on('click', function (e) {
            e.preventDefault();
          
                if ($('body').hasClass("page-sidebar-closed")) {
                    if ($('.sidebar-search').hasClass('open') == false) {
                        if ($('.page-sidebar-fixed').size() === 1) {
                            $('.page-sidebar .sidebar-toggler').click(); //trigger sidebar toggle button
                        }
                        $('.sidebar-search').addClass("open");
                    } else {
                        window.location.href = "extra_search.html";
                    }
                } else {
                    window.location.href = "extra_search.html";
                }
        });
    }

    var handleHorizontalMenu = function () {
        //handle hor menu search form toggler click
        $('.header').on('click', '.hor-menu .hor-menu-search-form-toggler', function (e) {
                if ($(this).hasClass('hide')) {
                    $(this).removeClass('hide');
                    $('.header .hor-menu .search-form').hide();
                } else {
                    $(this).addClass('hide');
                    $('.header .hor-menu .search-form').show();
                }
                e.preventDefault();
            });

        //handle hor menu search button click
        $('.header').on('click', '.hor-menu .search-form .btn', function (e) {
                window.location.href = "extra_search.html";
                e.preventDefault();
            });

        //handle hor menu search form on enter press
        $('.header').on('keypress', '.hor-menu .search-form input', function (e) {
                if (e.which == 13) {
                    window.location.href = "extra_search.html";
                    return false;
                }
            });
    }

    var handleGoTop = function () {
        /* set variables locally for increased performance */
        jQuery('.footer').on('click', '.go-top', function (e) {
                App.scrollTo();
                e.preventDefault();
            });
    }

    var handlePortletTools = function () {
        jQuery('body').on('click', '.portlet .tools a.remove', function (e) {
            e.preventDefault();
                var removable = jQuery(this).parents(".portlet");
                if (removable.next().hasClass('portlet') || removable.prev().hasClass('portlet')) {
                    jQuery(this).parents(".portlet").remove();
                } else {
                    jQuery(this).parents(".portlet").parent().remove();
                }
        });

       /* jQuery('body').on('click', '.portlet .tools a.reload', function (e) {
            e.preventDefault();
                var el = jQuery(this).parents(".portlet");
                App.blockUI(el);
                window.setTimeout(function () {
                        App.unblockUI(el);
                    }, 1000);
        });*/

        jQuery('body').on('click', '.portlet .tools .collapse, .portlet .tools .expand', function (e) {
            e.preventDefault();
                var el = jQuery(this).closest(".portlet").children(".portlet-body");
                if (jQuery(this).hasClass("collapse")) {
                    jQuery(this).removeClass("collapse").addClass("expand");
                    el.slideUp(200);
                } else {
                    jQuery(this).removeClass("expand").addClass("collapse");
                    el.slideDown(200);
                }
        });
    }

    var handleUniform = function () {
        if (!jQuery().uniform) {
            return;
        }
        var test = $("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
        if (test.size() > 0) {
            test.each(function () {
                    if ($(this).parents(".checker").size() == 0) {
                        $(this).show();
                        $(this).uniform();
                    }
                });
        }
    }

    var handleAccordions = function () {
        $(".accordion").collapse().height('auto');

        var lastClicked;

        //add scrollable class name if you need scrollable panes
        jQuery('body').on('click', '.accordion.scrollable .accordion-toggle', function () {
            lastClicked = jQuery(this);
        }); //move to faq section

        jQuery('body').on('shown', '.accordion.scrollable', function () {
            jQuery('html,body').animate({
                scrollTop: lastClicked.offset().top - 150
            }, 'slow');
        });
    }

    var handleTabs = function () {

        // function to fix left/right tab contents
        var fixTabHeight = function(tab) {
            $(tab).each(function() {
                var content = $($($(this).attr("href")));
                var tab = $(this).parent().parent();
                if (tab.height() > content.height()) {
                    content.css('min-height', tab.height());    
                } 
            });            
        }

        // fix tab content on tab shown
        $('body').on('shown', '.nav.nav-tabs.tabs-left a[data-toggle="tab"], .nav.nav-tabs.tabs-right a[data-toggle="tab"]', function(){
            fixTabHeight($(this)); 
        });

        $('body').on('shown', '.nav.nav-tabs', function(){
            handleSidebarAndContentHeight();
        });

        //fix tab contents for left/right tabs
        fixTabHeight('.nav.nav-tabs.tabs-left > li.active > a[data-toggle="tab"], .nav.nav-tabs.tabs-right > li.active > a[data-toggle="tab"]');

        //activate tab if tab id provided in the URL
        if(location.hash) {
            var tabid = location.hash.substr(1);
            $('a[href="#'+tabid+'"]').click();
        }
    }

    var handleScrollers = function () {
        $('.scroller').each(function () {
                $(this).slimScroll({
                        size: '7px',
                        color: '#a1b2bd',
                        position: isRTL ? 'left' : 'right',
                        height: $(this).attr("data-height"),
                        alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                        railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
                        disableFadeOut: true
                    });
            });
    }

    var handleTooltips = function () {
        if (App.isTouchDevice()) { // if touch device, some tooltips can be skipped in order to not conflict with click events
            jQuery('.tooltips:not(.no-tooltip-on-touch-device)').tooltip();
        } else {
            jQuery('.tooltips').tooltip();
        }
    }

    var handleDropdowns = function () {
        $('body').on('click', '.dropdown-menu.hold-on-click', function(e){
            e.stopPropagation();
        })
    }

    var handlePopovers = function () {
        jQuery('.popovers').popover();
    }

    var handleChoosenSelect = function () {
        if (!jQuery().chosen) {
            return;
        }

        $(".chosen").each(function () {
        	if($(this).attr('data-disable') == 'true') return true;
            $(this).chosen({
                allow_single_deselect: $(this).attr("data-with-diselect") === "1" ? true : false
            });
        });
    }

    var handleFancybox = function () {
        if (!jQuery.fancybox) {
            return;
        }

        if (jQuery(".fancybox-button").size() > 0) {
            jQuery(".fancybox-button").fancybox({
                groupAttr: 'data-rel',
                prevEffect: 'none',
                nextEffect: 'none',
                closeBtn: true,
                helpers: {
                    title: {
                        type: 'inside'
                    }
                }
            });
        }
    }

    var handleTheme = function () {

        var panel = $('.color-panel');

        if ($('body').hasClass('page-boxed') == false) {
            $('.layout-option', panel).val("fluid");
        }
        
        //$('.sidebar-option', panel).val("default");
        //$('.header-option', panel).val("fixed");
        //$('.footer-option', panel).val("fixed"); 

        //handle theme layout
        var resetLayout = function () {
            $("body").
                removeClass("page-boxed").
                removeClass("page-footer-fixed").
                removeClass("page-sidebar-fixed").
                removeClass("page-header-fixed");

            $('.header > .navbar-inner > .container').removeClass("container").addClass("container-fluid");

            if ($('.page-container').parent(".container").size() === 1) {
                $('.page-container').insertAfter('.header');
            } 

            if ($('.footer > .container').size() === 1) {                        
                $('.footer').html($('.footer > .container').html());                        
            } else if ($('.footer').parent(".container").size() === 1) {                        
                $('.footer').insertAfter('.page-container');
            }

            $('body > .container').remove(); 
        }

        var lastSelectedLayout = '';

        var setLayout = function () {
            var layoutOption = $('.layout-option', panel).val();
            var sidebarOption = $('.sidebar-option', panel).val();
            var headerOption = $('.header-option', panel).val();
            var footerOption = $('.footer-option', panel).val(); 

            if (sidebarOption == "fixed" && headerOption == "default") {
                alert('不支持固定头部和浮动边栏选择，重置为固定头部和固定边栏');
                $('.sidebar-option', panel).val("default");
                sidebarOption = 'default';
            }

            resetLayout(); // reset layout to default state

            if (layoutOption === "boxed") {
                $("body").addClass("page-boxed");

                // set header
                $('.header > .navbar-inner > .container-fluid').removeClass("container-fluid").addClass("container");
                var cont = $('.header').after('<div class="container"></div>');

                // set content
                $('.page-container').appendTo('body > .container');

                // set footer
                if (footerOption === 'fixed' || sidebarOption === 'default') {
                    $('.footer').html('<div class="container">'+$('.footer').html()+'</div>');
                } else {
                    $('.footer').appendTo('body > .container');
                }            
            }

            if (lastSelectedLayout != layoutOption) {
                //layout changed, run responsive handler:
                runResponsiveHandlers();
            }
            lastSelectedLayout = layoutOption;

            //header
            if (headerOption === 'fixed') {
                $("body").addClass("page-header-fixed");
                $(".header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("page-header-fixed");
                $(".header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
            }

            //sidebar
            if (sidebarOption === 'fixed') {
                $("body").addClass("page-sidebar-fixed");
            } else {
                $("body").removeClass("page-sidebar-fixed");
            }

            //footer 
            if (footerOption === 'fixed') {
                $("body").addClass("page-footer-fixed");
            } else {
                $("body").removeClass("page-footer-fixed");
            }

            handleSidebarAndContentHeight(); // fix content height            
            handleFixedSidebar(); // reinitialize fixed sidebar
            handleFixedSidebarHoverable(); // reinitialize fixed sidebar hover effect
            
            $.post('/extra_profile/themeLayout',{'layout':layoutOption,'header':headerOption,'sidebar':sidebarOption,'footer':footerOption},function(json){
            	
            },'json');
        }

        // handle theme colors
        var setColor = function (color) {
            $('#style_color').attr("href", "/res/css/" + color + ".css");
            $.post('/extra_profile/themeColor',{'color':color},function(json){
            	
            },'json');
        }

        $('.icon-color', panel).click(function () {
            $('.color-mode').show();
            $('.icon-color-close').show();
        });

        $('.icon-color-close', panel).click(function () {
            $('.color-mode').hide();
            $('.icon-color-close').hide();
        });

        $('li', panel).click(function () {
            var color = $(this).attr("data-style");
            setColor(color);
            $('.inline li', panel).removeClass("current");
            $(this).addClass("current");
        });

        $('.layout-option, .header-option, .sidebar-option, .footer-option', panel).change(setLayout);
    }

    var handleFixInputPlaceholderForIE = function () {
        //fix html5 placeholder attribute for ie7 & ie8
        if (isIE8 || isIE9) { // ie7&ie8
            // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
            jQuery('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function () {

                var input = jQuery(this);

                if(input.val()=='' && input.attr("placeholder") != '') {
                    input.addClass("placeholder").val(input.attr('placeholder'));
                }

                input.focus(function () {
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });

                input.blur(function () {                         
                    if (input.val() == '' || input.val() == input.attr('placeholder')) {
                        input.val(input.attr('placeholder'));
                    }
                });
            });
        }
    }
    
    var handleMenuInit = function() {
		var path = $(".page-sidebar #path").val();
		var subMenu = $(".page-sidebar #" + path);
		subMenu.addClass("active");
		var menu = subMenu.parent().parent();
		if (menu.hasClass("last")) {
			menu.addClass("active");
		}else{
			menu.addClass("open");
			subMenu.parent().css("display",'block');
			menu.parent().parent().addClass("active");
		}
	}
    
    var handleDailog = function(){
    	$.fn.modalmanager.defaults.resize = true;
		$.fn.modalmanager.defaults.spinner = '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><img src="../media/image/ajax-modal-loading.gif" align="middle">&nbsp;<span style="font-weight:300; color: #eee; font-size: 18px; font-family:Open Sans;">&nbsp;Loading...</div>';
		
		if($("#dialog").length==0) $("body").append('<div id="dialog" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false"><div class="modal-header"></div><div class="model-content"></div></div>');
		$dialog=$("#dialog");
		$dialog.on("hidden", function() {
		    $(this).removeData("modal").off('show').off('hide');
		});
		$dialog_header = $dialog.find('.modal-header');
		$dialog_content=$(".model-content");
    }
    
    var Core = {
    	ajax: jQuery.ajax
    };
    //CUSTOM AJAX
    function doAjaxSuccess(d, s, r, v) {
		if(r){
			var x = r.getResponseHeader("BLUE-AJAX");
			if(x=='0'){
				App.Modal.alert(d.length==0?'您当前的登录信息已过期，请重新登录！':d,{okfn : function(){
					top.location.href='/login';
				}});
			}else if(x=='1'){
				App.Modal.alert(d);
			}else if(x=="2"){
				App.Modal.close();
				try{
					new Function(d)();
				}catch(e2){
					if(s.error)s.error.call(this, r, "error", e2);
				}
			}else if(x=="3"){
				window.location.href=d;
			}else if(x=="4"){
				window.location.reload();
			}else if(s.success){
				s.success.call(this, d, v, r);
			}
		}else{
			if(s.success)s.success.call(this, d, v, r);
		}
	}
    
    jQuery.ajax = function (s) {
    	if (!s){return Core.ajax();}
    	var r = null;
    	var n_s = $.extend({}, s);
    	if (n_s.context) throw new Error("不支持context选项。");
    	var _scs = function (d, v, r2) {doAjaxSuccess.call(this, d, s, r2 || r, v)};
    	n_s.success = _scs;
    	r = Core.ajax(n_s);
    	return r;
    };
    

    //* END:CORE HANDLERS *//

    return {

        //main function to initiate template pages
        init: function () {

            //IMPORTANT!!!: Do not modify the core handlers call order.

            //core handlers
            handleInit();
            handleResponsiveOnResize(); // set and handle responsive    
            handleUniform();        
            handleScrollers(); // handles slim scrolling contents 
            handleResponsiveOnInit(); // handler responsive elements on page load

            //layout handlers
            handleNavigateMenu();
            handleFixedSidebar(); // handles fixed sidebar menu
            handleFixedSidebarHoverable(); // handles fixed sidebar on hover effect 
            handleSidebarMenu(); // handles main menu
            handleHorizontalMenu(); // handles horizontal menu
            handleSidebarToggler(); // handles sidebar hide/show            
            handleFixInputPlaceholderForIE(); // fixes/enables html5 placeholder attribute for IE9, IE8
            handleGoTop(); //handles scroll to top functionality in the footer
            handleTheme(); // handles style customer tool
            handleDailog(); //dialog

            //ui component handlers
            handlePortletTools(); // handles portlet action bar functionality(refresh, configure, toggle, remove)
            handleDropdowns(); // handle dropdowns
            handleTabs(); // handle tabs
            handleTooltips(); // handle bootstrap tooltips
            handlePopovers(); // handles bootstrap popovers
            handleAccordions(); //handles accordions
            handleChoosenSelect(); // handles bootstrap chosen dropdowns     

            App.addResponsiveHandler(handleChoosenSelect); // reinitiate chosen dropdown on main content resize. disable this line if you don't really use chosen dropdowns.
            
            $('.group-checkable').change(function () {
                var checked = $(this).is(":checked");
                $('.checkboxes').not(":disabled").each(function () {
        	       	if (checked) {
        	       		 $(this).attr("checked", true).parent().addClass('checked');
        	        } else {
        	             $(this).attr("checked", false).parent().removeClass('checked');
        	        }
            	});
        	});
            handleMenuInit();
        },

        fixContentHeight: function () {
            handleSidebarAndContentHeight();
        },

        addResponsiveHandler: function (func) {
            responsiveHandlers.push(func);
        },

        // useful function to make equal height for contacts stand side by side
        setEqualHeight: function (els) {
            var tallestEl = 0;
            els = jQuery(els);
            els.each(function () {
                    var currentHeight = $(this).height();
                    if (currentHeight > tallestEl) {
                        tallestColumn = currentHeight;
                    }
                });
            els.height(tallestEl);
        },

        // wrapper function to scroll to an element
        scrollTo: function (el, offeset) {
            pos = el ? el.offset().top : 0;
            jQuery('html,body').animate({
                    scrollTop: pos + (offeset ? offeset : 0)
                }, 'slow');
        },

        scrollTop: function () {
            App.scrollTo();
        },

        // wrapper function to  block element(indicate loading)
        blockUI: function (el, centerY) {
            var el = jQuery(el); 
            el.block({
                    message: '<img src="/res/image/loading.gif" align="">',
                    centerY: centerY != undefined ? centerY : true,
                    css: {
                        top: '10%',
                        border: 'none',
                        padding: '2px',
                        backgroundColor: 'none'
                    },
                    overlayCSS: {
                        backgroundColor: '#000',
                        opacity: 0.05,
                        cursor: 'wait'
                    }
                });
        },

        // wrapper function to  un-block element(finish loadiwng)
        unblockUI: function (el) {
            jQuery(el).unblock({
                    onUnblock: function () {
                        jQuery(el).removeAttr("style");
                    }
                });
        },

        // initializes uniform elements
        initUniform: function (els) {

            if (els) {
                jQuery(els).each(function () {
                        if ($(this).parents(".checker").size() == 0) {
                            $(this).show();
                            $(this).uniform();
                        }
                    });
            } else {
                handleUniform();
            }

        },

        // initializes choosen dropdowns
        initChosenSelect: function (els) {
            $(els).chosen({
                    allow_single_deselect: true
                });
        },

        initFancybox: function () {
            handleFancybox();
        },

        getActualVal: function (el) {
            var el = jQuery(el);
            if (el.val() === el.attr("placeholder")) {
                return "";
            }

            return el.val();
        },

        getURLParameter: function (paramName) {
            var searchString = window.location.search.substring(1),
                i, val, params = searchString.split("&");

            for (i = 0; i < params.length; i++) {
                val = params[i].split("=");
                if (val[0] == paramName) {
                    return unescape(val[1]);
                }
            }
            return null;
        },

        // check for device touch support
        isTouchDevice: function () {
            try {
                document.createEvent("TouchEvent");
                return true;
            } catch (e) {
                return false;
            }
        },

        isIE8: function () {
            return isIE8;
        },

        isRTL: function () {
            return isRTL;
        },

        getLayoutColorCode: function (name) {
            if (layoutColorCodes[name]) {
                return layoutColorCodes[name];
            } else {
                return '';
            }
        },
        
        Modal : {
        	alertDefault : {ok : '确定',title : '提示',okfn : closeModel,openfn : null,hideTitle : false,hideClose : false,hidefn : null , width:560},
        	alert : function(msg , options){
        		var opt = $.extend({},App.Modal.alertDefault, options);
        		
        		$dialog_header.html('');
        		$dialog_content.html('');
        		
        		$dialog_content.html('<div class="modal-body">'+msg+'</div><div class="modal-footer"><button id="dialog_ok" type="button" class="btn green">'+opt.ok+'</button></div>');
        		$('#dialog_ok').click(function(){opt.okfn.call(this);});
        		if(opt.hideTitle) $dialog_header.hide();
        		else{
            		if(!opt.hideClose) $dialog_header.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>');
            		$dialog_header.append('<h3>'+opt.title+'</h3>');
        			$dialog_header.show();
        		}
        		
        		if(opt.openfn != null) $dialog.off('show').on('show',opt.openfn);
        		if(opt.hidefn != null) $dialog.off('hide').on('hide',opt.hidefn);
        		$dialog.attr({'data-keyboard':'false','data-width':opt.width}).css({'width':opt.width+'px'}).modal({keyboard:false,width:opt.width});
        	},
        	confirmDefault : {ok : '确定',cal : '取消',title : '提示',okfn : closeModel,calfn : closeModel,openfn:null,hideTitle : false,hideClose : false,hidefn : null , width:560},
        	confirm : function(msg , options){
        		var opt = $.extend({},App.Modal.confirmDefault, options);
        		
        		$dialog_header.html('');
        		$dialog_content.html('');
        		
        		$dialog_content.html('<div class="modal-body">'+msg+'</div><div class="modal-footer"><button id="dialog_ok" type="button" class="btn green">'+opt.ok+'</button><button id="dialog_cal" type="button" class="btn">'+opt.cal+'</button></div>');
        		$('#dialog_ok').click(function(){opt.okfn.call(this);});
        		$('#dialog_cal').click(function(){opt.calfn.call(this);});
        		
        		if(opt.hideTitle) $dialog_header.hide();
        		else{
            		if(!opt.hideClose) $dialog_header.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>');
            		$dialog_header.append('<h3>'+opt.title+'</h3>');
        			$dialog_header.show();
        		}
        		
        		if(opt.openfn != null) $dialog.off('show').on('show',opt.openfn);
        		if(opt.hidefn != null) $dialog.off('hide').on('hide',opt.hidefn);
        		$dialog.attr({'data-keyboard':'false','data-width':opt.width}).css({'width':opt.width+'px'}).modal({keyboard:false,width:opt.width});
        	},
        	htmlDefault : {title : '提示',openfn:null,hideTitle : false,hideClose : false,hidefn : null , width:560},
        	html : function(modalBody , options , modalFooter){
        		var opt = $.extend({},App.Modal.htmlDefault, options);
        		
        		$dialog_header.html('');
        		$dialog_content.html('');
        		
        		$dialog_content.html('<div class="modal-body">'+modalBody+'</div>');
        		if(modalFooter) $dialog_content.append('<div class="modal-footer">'+modalFooter+'</div>');
        		if(opt.hideTitle) $dialog_header.hide();
        		else{
            		if(!opt.hideClose) $dialog_header.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>');
            		$dialog_header.append('<h3>'+opt.title+'</h3>');
        			$dialog_header.show();
        		}
        		
        		if(opt.openfn != null) $dialog.off('show').on('show',opt.openfn);
        		if(opt.hidefn != null) $dialog.off('hide').on('hide',opt.hidefn);
        		$dialog.attr({'data-keyboard':'true','data-width':opt.width}).css({'width':opt.width+'px'}).modal({keyboard:true,width:opt.width});
        	},
        	loadDefault : {title : '提示',openfn:null,hideTitle : false,hideClose : false,hidefn : null , width:560},
        	load : function(url,params,options){
        		$dialog_header.html('');
        		$dialog_content.html('');
        		if(!params || typeof params != "object") params = {};
        		params['_ajax']='true';
        		var elements = $('body');
        		
        		$.ajax({
    				"url":  url,
    				"data": params,
    				"dataType": "html",
    				"cache": false,
    				"type": 'post',
    				"beforeSend" : function(){
    					elements.modalmanager('loading');
    				},
    				"success": function(responseText){
    					$dialog_content.html(responseText);

    					var opt = $.extend({},App.Modal.loadDefault,options?options:{});
    					//查看里面是否设置了width
                		var modal_body = $dialog_content.children('.modal-body');
                		var dataWidth = modal_body.attr('data-width');
                		if(dataWidth && App.isMath(dataWidth)){
                			opt.width = parseInt(dataWidth);
                		}
                		var dataTitle = modal_body.attr('data-title');
                		if(dataTitle && dataTitle != ''){
                			opt.title = dataTitle;
                		}
                		
                		if(opt.hideTitle) $dialog_header.hide();
                		else{
                    		if(!opt.hideClose) $dialog_header.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>');
                    		$dialog_header.append('<h3>'+opt.title+'</h3>');
                			$dialog_header.show();
                		}
                		
                		if(opt.openfn != null) $dialog.off('show').on('show',opt.openfn);
                		if(opt.hidefn != null) $dialog.off('hide').on('hide',opt.hidefn);
                		
                		$dialog.attr({'data-keyboard':'true','data-width':opt.width}).css({'width':opt.width+'px'}).modal({keyboard:true,width:opt.width});
    				},
    				"complete":function(xhr,status){
    					
              		},
    				"error": function (xhr, error, thrown) {
    					elements.modalmanager().data('modalmanager').removeLoading();
    					App.Tips.error(xhr.responseText);
    				}
    			});
        	},
        	close : closeModel
        },
        validateDefault : {
        	rules : {},
        	messages : {},
        	errorPlacement : function(error, element){error.insertAfter(element);},
        	dialog : true
        },
        validate : function(element,options){
        	options = $.extend({},App.validateDefault,options);
        	
        	var tipsClass = options.dialog?'help-inline':'help-block';
        	
        	$('#'+element).validate({
        		errorElement: 'span', //default input error message container
                errorClass: tipsClass, // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: ".ignore",
        		rules : options.rules,
        		messages : options.messages,
        		errorPlacement : options.errorPlacement,
        		highlight : function(element){
        			$(element).closest('.'+tipsClass).removeClass('ok'); // display OK icon
        	    	$(element).closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
        		},
        		unhighlight : function(element){
        			$(element).closest('.control-group').removeClass('error'); // set error class to the control group
        		},
        		success : function(label){
        			 // mark the current input as valid and display OK icon
        			 // set success class to the control group
        			var elementName = label.attr('for');
        			var element = label.prev();
        			if(typeof options.rules[elementName] != 'undefined'){
        				if(options.rules[elementName].required || element.val().length > 0){
        					label.addClass('valid').addClass(tipsClass+' ok').closest('.control-group').removeClass('error').addClass('success');
        				}else{
        					element.closest('.'+tipsClass).removeClass('ok'); // display OK icon
        					element.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
        				}
        			}
        		}
        	});
        },
        Ajax : {
        	defaults : {fn : null , errfn : null,completefn : null},
            submit : function(formid,options){
            	var opt = $.extend({},App.Ajax.defaults, options);
            	
            	var form = $('#'+formid);
            	if(form.length == 0 || form.data('send')=='true') return;
            	form.data('send','true');
            	
            	var url = form.attr('action');
            	var data = form.serializeArray();
            	if(!data || typeof data != 'object') data = {};
            	data['_ajax']='true';
            	
            	var options = {
        			"url":  url,
        			"data": data,
        			"dataType": "json",
        			"cache": false,
        			"type": 'post',
        			"beforeSend" : function(){
        	           	App.Tips.info('数据正在处理......',{autoHide:false,hideClose:true});
        			},
        			"success": function(json){
        				App.Ajax.resolve(json,opt.fn,opt.errfn);
        			},
        			"complete":function(xhr,status){
        				form.data('send','false');
        				if(opt.completefn && opt.completefn instanceof Function) opt.completefn();
                  	},
        			"error": function (xhr, error, thrown) {
        				App.Tips.error('数据提交发生错误......');
        			}
        		}
            	if(form.attr('enctype') == 'multipart/form-data'){
            		form.ajaxSubmit(options);
            	}else{
            		$.ajax(options);
            	}
            },
            get : function(url, options){
            	url = url.replace(new RegExp("_ajax=([^\&]*)[\&]*","g"),'');
            	url = url.replace(new RegExp("[\&|\?]+$"),'');
            	if(url.indexOf('_ajax') == -1){
            		if(url.indexOf('?') != -1)  url += '&_ajax=true';
            		else url += '?_ajax=true'
            	}
            	
            	var opt = $.extend({},App.Ajax.defaults, options);
            	$.ajax({
    				"url":  url,
    				"dataType": "json",
    				"cache": false,
    				"type": 'get',
    				"beforeSend" : function(){
    	            	App.Tips.info('数据正在处理......',{autoHide:false,hideClose:true});
    				},
    				"success": function(json){
    					App.Ajax.resolve(json,opt.fn,opt.errfn);
    				},
    				"complete":function(xhr,status){
    					if(opt.completefn && opt.completefn instanceof Function) opt.completefn();
              		},
    				"error": function (xhr, error, thrown) {
    					App.Tips.error('数据提交发生错误......');
    				}
    			});
            },
            post : function(url, data,options){
            	if(!data || typeof data != 'object') data = {};
            	data['_ajax']='true';
            	
            	var opt = $.extend({},App.Ajax.defaults, options);
            	
            	$.ajax({
    				"url":  url,
    				"data":data,
    				"dataType": "json",
    				"cache": false,
    				"type": 'post',
    				"beforeSend" : function(){
    	            	App.Tips.info('数据正在处理......',{autoHide:false,hideClose:true});
    				},
    				"success": function(json){
    					App.Ajax.resolve(json,opt.fn,opt.errfn);
    				},
    				"complete":function(xhr,status){
    					if(opt.completefn && opt.completefn instanceof Function) opt.completefn();
              		},
    				"error": function (xhr, error, thrown) {
    					App.Tips.error('数据提交发生错误......');
    				}
    			});
            },
            asyncPost : function(url, data,options){
            	if(!data || typeof data != 'object') data = {};
            	data['_ajax']='true';
            	
            	var opt = $.extend({},App.Ajax.defaults, options);
            	
            	$.ajax({
    				"url":  url,
    				"data":data,
    				"dataType": "json",
    				"cache": false,
    				"type": 'post',
    				"async": false,
    				"beforeSend" : function(){
    	            	App.Tips.info('数据正在处理......',{autoHide:false,hideClose:true});
    				},
    				"success": function(json){
    					App.Ajax.resolve(json,opt.fn,opt.errfn);
    				},
    				"complete":function(xhr,status){
    					if(opt.completefn && opt.completefn instanceof Function) opt.completefn();
              		},
    				"error": function (xhr, error, thrown) {
    					App.Tips.error('数据提交发生错误......');
    				}
    			});
            },
            resolve : function(json,fn,errfn){
            	if(json.success){
            		App.Tips.success(json.msg);
            		
            		if(json.resultType){
            			switch(json.resultType){
        					case 'selfpage'://刷新本页面
        						window.location.reload();
        						break;
            				case 'function'://执行函数
            					if(fn && fn instanceof Function){
            						fn(json);
                    			}
            					break;
            				case 'close_box_selfpage'://关闭窗口并且刷新页面
            					App.Modal.close();
            					window.location.reload();
            					break;
            				case 'close_box_function'://关闭窗口并且执行函数
            					if(fn && fn instanceof Function){
            						fn(json);
                    			}
            					App.Modal.close();
            					break;
            				case 'close_box'://关闭窗口
            					App.Modal.close();
            					break;
            				case 'url'://跳转到指定页面
            					window.location = json.data;
            					break;
            				case 'np_op'://什么都不做
            					break;
            				case 'close_selfpage_reload_opener'://关闭本窗口并且刷新父窗口
            					 window.opener.location.reload();
            	                 window.close();
            					break;
            				case 'close_selfpage'://关闭本页面
            					window.close();
            					break;
            			}
            		}
            	}else{
            		App.Tips.error(json.msg);
            		if(json.resultType == 'function'){
            			if(errfn && errfn instanceof Function){
            				errfn(json);
            			}
            		}
            	}
            }
        },
        Tips : {
        	timer : null,
        	defaults : {autoHide : true,hideTime:3000,closefn:null,addClass:'',hideClose:false},
        	clearTimer : function(){
        		if(App.Tips.timer != null){
        			clearTimeout(App.Tips.timer);
            		App.Tips.timer = null;
        		}
        	},
        	show : function(msg,options){
        		App.Tips.clearTimer();
        		var opt = $.extend({},App.Tips.defaults,options);
        		
        		var tips = $('#app_tips');
        		if (tips.length != 0){
        			tips.stop().css({'opacity':1}).show().html('<div class="alert'+(opt.addClass!=''?' '+opt.addClass:'')+'"><strong>'+msg+'</strong></div>');
                }else{
                	tips = $('<div id="app_tips"><div class="alert'+(opt.addClass!=''?(' '+opt.addClass):'')+'"><strong>'+msg+'</strong></div></div>');
                    $('body').append(tips);
                }
        		
        		if(!opt.hideClose){
        			tips.find('.alert').append('<button class="close"></button>').find('.close').click(function(){
        				App.Tips.clearTimer();
        				tips.remove();
        			});
        		}

        		if(opt.autoHide){
        			App.Tips.timer = setTimeout(function(){
        				App.Tips.clearTimer();
        				tips.fadeOut('fast',function(){
        					tips.remove();
        				})
        				
        				if(opt.closefn != null) opt.closefn();
        			},opt.hideTime);
        		}
        	},
        	warning : function(msg,opt){
        		if(!opt || typeof opt != 'object') opt = {};
        		App.Tips.show(msg,opt);
        	},
        	info : function(msg,opt){
        		if(!opt || typeof opt != 'object') opt = {};
        		opt['addClass'] = 'alert-info';
        		App.Tips.show(msg,opt);
        	},
        	success : function(msg,opt){
        		if(!opt || typeof opt != 'object') opt = {};
        		opt['addClass'] = 'alert-success';
        		App.Tips.show(msg,opt);
        	},
        	error : function(msg,opt){
        		if(!opt || typeof opt != 'object') opt = {};
        		opt['addClass'] = 'alert-error';
        		App.Tips.show(msg,opt);
        	}
        },
        Tables : {
        	get : function(key){
        		return $.data(document.body, key+'_Tables');
        	},
        	set : function(key,value){
        		$.data(document.body, key+'_Tables' , value);
        	},
        	refresh : function(key,bo){
        		$('#'+key+'_Tables').DataTable().ajax.reload();// 修改弹出模态窗关闭不刷新列表bug
        		//App.Tables.get(key).fnDraw(bo === false ? false : true);
        	},
        	destory : function(key,bo){
        		App.Tables.get(key).fnClearTable(bo === false ? false : true);
        		App.Tables.get(key).fnDestroy(bo === false ? false : true);
        	},
        	getManager : function(key){
        		return $.data(document.body, key+'_Manager');
        	},
        	setManager : function(key,value){
        		$.data(document.body, key+'_Manager' , value);
        	},
        	updateDate : function(key,aoData){
        		App.Tables.getManager(key).updateData(aoData);
        	},
        	resetData : function(key,aoData){
        		App.Tables.getManager(key).resetData(aoData);
        	},
        	getData : function(key,dataKey){
        		var d = App.Tables.getManager(key);
        		return d.getData(dataKey);
        	},
        	setData : function(key,dataKey,value){
        		var d = App.Tables.getManager(key);
        		d.setData(dataKey,value);
        	}
        },
        Radio : {
        	uniform : function(element){
        		if (!jQuery().uniform) {
    	            return;
    	        }
    	        var test = $(element).find("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
    	        if (test.size() > 0) {
    	            test.each(function () {
    	                    if ($(this).parents(".checker").size() == 0) {
    	                        $(this).show();
    	                        $(this).uniform();
    	                    }
    	             });
    	        }
        	}
        },
        Tools : {
        	trimText : function(form) {
        		var sfind = "";
        		if(typeof form == 'undefined'){
        			sfind = "input[type='text']";
        		}else{
        			sfind = "#"+form+" input[type='text']";
        		}
        		
        		$(sfind).each(function() {
        			var ss = $(this).attr("value");
        			jQuery(this).attr("value", jQuery.trim(ss));
        		});
        	},
        	href : function(url , param){
        		if(url.indexOf('?') > -1){
        			url = url + "&";
        		}else{
        			url = url + "?";
        		}
        		
        		$.each(param , function(k,v){
        			url += k + "="+v+"&";
        		});
        		
        		top.location.href=url.substring(0,url.length-1);
        	},
        	open : function(url , param){
        		if(url.indexOf('?') > -1){
        			url = url + "&";
        		}else{
        			url = url + "?";
        		}
        		
        		$.each(param , function(k,v){
        			url += k + "="+v+"&";
        		});
        		
        		window.open(url.substring(0,url.length-1));
        	}
        },
        isMath : function(s){
        	if(s==""){return false;}
        	if(/^\d+$/.test(s)){return true;}else{return false;}
        },
        isMobile : function(a){
        	var b=new RegExp("^1[0-9]{10}$");return b.test(a);
        },
        isMoney : function(v){
        	var patrn=/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
        	if(patrn.exec(v)) return true;
        	return false;
        },
        isEmail : function(v){
        	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?[-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        	return reg.test(v);
        },
        resizeImage : function(ImgD,maxWidth,maxHeight){
        	var image=new Image();
        	var iwidth = maxWidth;
        	var iheight = maxHeight;
        	image.src=ImgD.src;
        	
        	var p = $(ImgD);
        	if(image.width>0 && image.height>0 && (image.width>maxWidth || image.height>maxHeight)){
        		if(image.width/image.height>= iwidth/iheight){
        			p.css('width',iwidth+'px');
        			p.css('height',(image.height*iwidth)/image.width+'px');
        			
        			ImgD.width=iwidth;
        			ImgD.height=(image.height*iwidth)/image.width;
        		}else{
        			p.css('height',iheight+'px');
        			p.css('width',(image.width*iheight)/image.height+'px');
        			
        			ImgD.height=iheight;
        			ImgD.width=(image.width*iheight)/image.height;
        		}
        	}
        }
    };

}();/*

jQuery(document).ready(function() {     
	  App.init();
});*/