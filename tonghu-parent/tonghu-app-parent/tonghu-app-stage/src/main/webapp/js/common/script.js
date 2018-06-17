$(function(){
    try{
        // Tooltips
        $('.hints').tooltip({trigger: 'hover'});
        $('.tips').tooltip({placement: 'left'});
        $('.tip-top').tooltip({placement: 'top'});
        $('.tip-right').tooltip({placement: 'right'});
        $('.tip-bottom').tooltip({placement: 'bottom'});
        // Tour tip
        //$('#joyRideTipContent').joyride({'nextButton': false});
    } catch (e){
        errorMessage(e);
    }

    // Popovers
    try{
        $('.pop-top').popover({trigger:'hover', placement: 'top'});
        $('.pop-right').popover({trigger:'hover', placement: 'right'});
        $('.pop-bottom').popover({trigger:'hover', placement: 'bottom'});
        $('.pop-left').popover({trigger:'hover', placement: 'left'});
    } catch (e){
        errorMessage(e);
    }

    // Modal
    try{
        $('.modal').modal('hide');
    } catch (e){
        errorMessage(e);
    }

    // Slider input js
    try{
        jQuery("#Slider1").slider({ from: 5, to: 50, step: 2.5, round: 1, dimension: '&nbsp;$', skin: "round_plastic" });
        jQuery("#Slider2").slider({ from: 5000, to: 150000, heterogeneity: ['50/50000'], step: 1000, dimension: '&nbsp;$', skin: "round_plastic" });
        jQuery("#Slider3").slider({ from: 1, to: 30, heterogeneity: ['50/5', '75/15'], scale: [1, '|', 3, '|', '5', '|', 15, '|', 30], limits: false, step: 1, dimension: '', skin: "round_plastic" });
        jQuery("#Slider4").slider({ from: 480, to: 1020, step: 15, dimension: '', scale: ['8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00'], limits: false, skin: "round_plastic", calculate: function( value ){
            var hours = Math.floor( value / 60 );
            var mins = ( value - hours*60 );
            return (hours < 10 ? "0"+hours : hours) + ":" + ( mins == 0 ? "00" : mins );
        }});
    } catch (e){
        errorMessage(e);
    }
    // Drop down menu
    $('.breadcrumb li').hover(
        function () {
            //show its submenu
            $('.submenu', this).slideDown(100);

        },
        function () {
            //hide its submenu
            $('.submenu', this).slideUp(100);
        }
    );

    // Elastice text area
    try{
        $('.elastic').elastic();
    } catch (e){
        errorMessage(e);
    }

    // Date picker
    try{
        $('.dpicker').datepicker();
    } catch (e){
        errorMessage(e);
    }

    // Color picker
    try{
        $('.cpicker').colorpicker().on('changeColor', function(ev){
            $('[class*="cpicker"]').css({backgroundColor: ev.color.toHex()});
        });
    } catch (e){

    }

    // Mask input
    //try{
    //    $("#date").mask("99/99/9999");
    //    $("#phone").mask("(999) 999-9999");
    //    $("#phnext").mask("(999) 999-9999? x99999");
    //    $("#tin").mask("99-9999999");
    //    $("#ssn").mask("999-99-9999");
    //    $('#cid').mask("CID: 9999-aaa-9a*");
    //    $('#percentage').mask("99%");
    //} catch (e){
    //    errorMessage(e);
    //}

    // Data table js call
    try{
        $('.dynamicTable_one').dataTable({
            "bPaginate": true,
            "sPaginationType": "two_button",
            "bLengthChange": false,
            "bFilter": false,
            "bSort": true,
            "bInfo": true,
            "bAutoWidth": false
        });
    } catch (e){
        errorMessage(e);
    }
    try{
        $('.dynamicTable_two').dataTable({
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bLengthChange": true,
            "bFilter": true,
            "bSort": true,
            "bInfo": true,
            "bAutoWidth": true
        });
    } catch (e){
        errorMessage(e);
    }

    // ColResize js call
    try{
        $(".resizable").colResizable({
            liveDrag:true,
            gripInnerHtml:"<div class='grip'></div>",
            draggingClass:"dragging"
        });
    } catch (e){
        errorMessage(e);
    }

    // Sortable table js call
    try{
        $('.sortable').tablesorter();
    } catch (e){
        errorMessage(e);
    }

    // Uniform selects
    try{
        $(".u_select").uniform();
    } catch (e){
        errorMessage(e);
    }

    // Chosen selects
    try{
        $(".c_select").chosen();
    } catch (e){
        errorMessage(e);
    }
    // iButton call
    try{
        $(".chkbox1").iButton({className: "on_off", labelOn: "", labelOff: "", easing: "swing"});
    } catch (e){
        errorMessage(e);
    }

    try{
        $(".chkbox2").iButton({className: "enabled_disabled", labelOn: "ENABLE", labelOff: "DISABLE", easing: "swing"});
    } catch (e){
        errorMessage(e);
    }

    try{
        $(".radio1").iButton({className: "on_off", labelOn: "", labelOff: "", easing: "swing"});
    } catch (e){
        errorMessage(e);
    }
    try{
        $(".radio2").iButton({className: "yes_no", labelOn: "ON", labelOff: "OFF", easing: "swing"});
    } catch (e){
        errorMessage(e);
    }

    // Spinner input call
    try{
        $( ".decimal" ).spinner();
    } catch (e){
        errorMessage(e);
    }
    try{
        $( ".spinner_list" ).spinner();
    } catch (e){
        errorMessage(e);
    }
    try{
        $( ".currency" ).spinner({currency:'$'});
    } catch (e){
        errorMessage(e);
    }
    // Form validation
    try{
        $('.p_form').validationEngine({promptPosition: 'topLeft'});
    } catch (e){
        errorMessage(e);
    }
    //$('.j_form').validate();
    try{
        $(".j_form").validate({
            rules: {
                regular: "required",
                pwd: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#pwd"
                },
                minl: {
                    required: true,
                    minlength: 3
                },
                maxl: {
                    required: true,
                    maxlength: 5
                },
                minno: {
                    required: true,
                    min: 3
                },
                maxno: {
                    required: true,
                    max: 5
                },
                digit:{
                    required: true,
                    digits: true
                },
                range: {
                    required: true,
                    range: [10, 25]
                },
                email: {
                    required: true,
                    email: true
                },
                date: {
                    required: true,
                    date: true
                },
                url: {
                    required: true,
                    url: true
                },
                textarea: {
                    required: true,
                    min: 10
                }
            },
            messages: {
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },
                email: "Please enter a valid email address",
                agree: "Please accept our policy"
            }
        });
    } catch (e){
        errorMessage(e);
    }

    // Wizard form
    try{
        $('.wizard').smartWizard();
        $('#wizard').smartWizard({transitionEffect:'slide'});
    } catch (e){
        errorMessage(e);
    }


    // BlockSit Js Call Script Starts

    $(window).load( function() {
        $('#bolt-containers').BlocksIt({
            numOfCol: 5,
            offsetX: 8,
            offsetY: 8
        });
    });

    //window resize
    var currentWidth = 1100;
    $(window).resize(function() {
        var winWidth = $(window).width();
        var conWidth;
        if(winWidth < 660) {
            conWidth = 440;
            col = 2
        } else if(winWidth < 880) {
            conWidth = 660;
            col = 3
        } else if(winWidth < 1100) {
            conWidth = 880;
            col = 4;
        } else {
            conWidth = 1100;
            col = 5;
        }

        if(conWidth != currentWidth) {
            currentWidth = conWidth;
            $('#bolt-containers').width(conWidth);
            $('#bolt-containers').BlocksIt({
                numOfCol: col,
                offsetX: 8,
                offsetY: 8
            });
        }
    });

    // BlockSit Js Call Script Ends



    // Montage  Js Call Script Starts

    montageGallery();

    // Montage  Js Call Script Ends

    // Raphael gallery Js Call Script Starts
    try{
        var li = $('.gallery').find('li');

        li.each(function(i){
            var t = $(this),
                img = t.find('img'),
                src = img.attr('src'),
                width = li.width(),
                height = li.height();

            img.hide().after($('<div />').attr('id', 'holder'+i).addClass('holder'));

            var r = Raphael('holder'+i, width*2, height*2),
                rimg = r.image(src, width/2, height/2, width, height);

            rimg.hover(function(event) {
                this.animate({
                    scale: 2,
                    rotation : 0
                }, 1200, 'elastic');
            }, function (event) {
                this.animate({
                    scale: 1,
                    rotation : 0
                }, 1200, 'elastic');
            });

        });
        li.hover(function(){
            li.css({ 'z-index': 0 });
            $(this).css({ 'z-index': 2 });
        });
    } catch (e){
        errorMessage(e);
    }


    // Raphael gallery Js Call Script Ends

    //ColorBox  Js Call Script Starts

    try{
        $(".bolt-colorbox").colorbox({rel:'bolt-colorbox', slideshow:true});
    } catch (e){
        errorMessage(e);
    }
    try{
        $(".bolt-colorbox2").colorbox({rel:'bolt-colorbox2', slideshow:true});
    } catch (e){
        errorMessage(e);
    }
    try{
        $(".bolt-colorbox3").colorbox({rel:'bolt-colorbox3', slideshow:true});
    } catch (e){
        errorMessage(e);
    }
    try{
        $(".bolt-colorbox4").colorbox({rel:'bolt-colorbox4', slideshow:true});
    } catch (e){
        errorMessage(e);
    }

    //ColorBox  Js Call Script Ends

    // Embedly Js Call Script Starts
    try{
        $(".bolt-embed-video a").embedly({key:'132409jafq09f3', maxWidth:'498', maxHeight:'280' }, function (oembed, dict) {
            if (oembed == null)
                return;
            var output = "<a class='embedly' href='#'></a>";
            output += oembed['code'];
            $(dict["node"]).parent().html(output);
        });
    } catch (e){
        errorMessage(e);
    }

    $('a.embedly').live("click", function (e) {
        e.preventDefault();
        $(this).parents('a').find('.embed').toggle();
    });
/***********
    try{
        // Embedly Js Call Script Ends

        //Video Flowplayer Js Call Script Starts

        flowplayer("player", "swf/flowplayer-3.2.14.swf", {

            clip:{
                autoPlay: false,
                baseUrl:'http://stream.flowplayer.org'
            }
        }).playlist("#myplaylist", {loop:true});

        // Video Flowplayer Js Call Script Ends
    }catch(e){
        errorMessage(e);
    }
****/
    /********************************** File upload js start **********************/
    try{
        var dropbox = $('#dropbox'),
            message = $('.message', dropbox);

        dropbox.filedrop({
            // The name of the $_FILES entry:
            paramname:'pic',

            maxfiles: 5,
            maxfilesize: 2,
            url: 'post_file.php',

            uploadFinished:function(i,file,response){
                $.data(file).addClass('done');
                // response is the JSON object that post_file.php returns
            },

            error: function(err, file) {
                switch(err) {
                    case 'BrowserNotSupported':
                        showMessage('Your browser does not support HTML5 file uploads!');
                        break;
                    case 'TooManyFiles':
                        alert('Too many files! Please select 5 at most! (configurable)');
                        break;
                    case 'FileTooLarge':
                        alert(file.name+' is too large! Please upload files up to 2mb (configurable).');
                        break;
                    default:
                        break;
                }
            },

            // Called before each upload is started
            beforeEach: function(file){
                if(!file.type.match(/^image\//)){
                    alert('Only images are allowed!');

                    // Returning false will cause the
                    // file to be rejected
                    return false;
                }
            },

            uploadStarted:function(i, file, len){
                createImage(file);
            },

            progressUpdated: function(i, file, progress) {
                $.data(file).find('.progress').width(progress);
            }

        });
    } catch (e){
        console.log(e);
    }

    var template = '<div class="preview">'+
        '<span class="imageHolder">'+
        '<img />'+
        '<span class="uploaded"></span>'+
        '</span>'+
        '<div class="progressHolder progress_striped">'+
        '<div class="progress"></div>'+
        '</div>'+
        '</div>';


    function createImage(file){

        var preview = $(template),
            image = $('img', preview);

        var reader = new FileReader();

        image.width = 100;
        image.height = 100;

        reader.onload = function(e){

            // e.target.result holds the DataURL which
            // can be used as a source of the image:

            image.attr('src',e.target.result);
        };

        // Reading the file as a DataURL. When finished,
        // this will trigger the onload function above:
        reader.readAsDataURL(file);

        message.hide();
        preview.appendTo(dropbox);

        // Associating a preview container
        // with the file, using jQuery's $.data():

        $.data(file,preview);
    }

    function showMessage(msg){
        message.html(msg);
    }

    /********************************** File upload js end **********************/

    // Little tabs js
    try{
        $('.leftTabs').easytabs();
    } catch (e){
        errorMessage(e);
    }
    try{
        $('.rightTabs').easytabs();
    } catch (e){
        errorMessage(e);
    }


    // Event calendar js

    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    try{
        $('#utopia-fullcalendar-2').fullCalendar({
            header:{
                left:'prev,next today',
                center:'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable:false,
            height:540,
            events:[
                {
                    title:'All Day Event',
                    start:new Date(y, m, 1)
                },
                {
                    title:'Long Event',
                    start:new Date(y, m, d - 5),
                    end:new Date(y, m, d - 2)
                },
                {
                    id:999,
                    title:'Repeating Event',
                    start:new Date(y, m, d - 3, 16, 0),
                    allDay:false
                },
                {
                    id:999,
                    title:'Repeating Event',
                    start:new Date(y, m, d + 4, 16, 0),
                    allDay:false
                },
                {
                    title:'Meeting',
                    start:new Date(y, m, d, 10, 30),
                    allDay:false
                },
                {
                    title:'Lunch',
                    start:new Date(y, m, d, 12, 0),
                    end:new Date(y, m, d, 14, 0),
                    allDay:false
                },
                {
                    title:'Birthday Party',
                    start:new Date(y, m, d + 1, 19, 0),
                    end:new Date(y, m, d + 1, 22, 30),
                    allDay:false
                },
                {
                    title:'Click for Google',
                    start:new Date(y, m, 28),
                    end:new Date(y, m, 29),
                    url:'http://google.com/'
                }
            ]
        });
    } catch (e){
        errorMessage(e);
    }

    // ClEditor call
    try{
        $(".textEditor").cleditor({width:"100%", height:"100%"});
    } catch (e){
        errorMessage(e);
    }


    // Responsiveness

    responsiveWindow();

    // Isotope Js Call Script Starts

    try{
        var $container = $('#bolt-container');

        $container.isotope({
            itemSelector:'.bolt-img-element',
            layoutMode : 'fitRows',
            itemSelector:'.bolt-img-element'
        });

        $container.infinitescroll({
                navSelector:'#page_nav', // selector for the paged navigation
                nextSelector:'#page_nav a', // selector for the NEXT link (to page 2)
                itemSelector:'.bolt-img-element', // selector for all items you'll retrieve
                loading:{
                    finishedMsg:'No more pages to load.'
                }
            },
            function (newElements) {
                $container.isotope('appended', $(newElements));
                $(".bolt-colorbox2").colorbox({rel:'bolt-colorbox2', slideshow:true});
            }
        );
    } catch (e){
        errorMessage(e);
    }

    function errorMessage(e){
        //console.log(e);
    }

    // Isotope Js Call Script Ends

    if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
        ie8();
    }
});

// Search box toggle
$('.user_search').on('click', function(){
    $('.search').toggle();
    $('.search > .search_form').toggleClass('visible');
});

$('.menuDrop').on('click', function(){
    if($('.search_form').hasClass('visible')){
        $('.search_form').removeClass('visible');
        $('.search').css({display: 'none'});
    }
});

// Show all js
$(".list_expand a").on('click', function(e){
    e.preventDefault();
    e.stopPropagation();
    var elem = $(this).parents('li');
    elem.find('.list .list_expand').html('<img src="~/Content/themes/base/img/loading.gif" alt="Loading...">');
    setTimeout(function() {
        elem.find('.list .list_expand').parent().hide();
        elem.find('.list_hidden').removeClass('list_hidden');
    }, 500);
});

// Form drop menu js fix
$(document).mouseup(function(e) {
    if(jQuery(e.target).parents().index() < 4 || jQuery(e.target).parents().index() > 5){
        $('.search').hide();
    }
});


// Text area counter
$('.counter').keyup(function(){
    var max = $(this).data('limit'),
        len = $(this).val().length;

    if (len >= max) {
        $(this).val($(this).val().substring(0, max));
        $('.counter_info').text('You have reached the limit');
    }else {
        var str = max - len;
        $('.counter_info').text( str + ' characters left');
    }
});

// Check or un-check all checkboxes
$('.selectAll').on('click', function() {
    var $this = $(this);
    //console.log($(this).parents('.widget_header').next('.widget_content').find('input:checkbox').attr('checked', $(this).is(':checked')));
    $this.parents('.widget_header').next('.widget_content').find('input:checkbox').attr('checked', $this.is(':checked'));
    if($this.is(':checked')){
        $this.parents('.widget_header').next('.widget_content').find('input:checkbox').attr('checked', $this.is(':checked')).parent().addClass('checked');
    }else{
        $this.parents('.widget_header').next('.widget_content').find('input:checkbox').attr('checked', $this.is(':checked')).parent().removeClass('checked');
    }
});

/* Widget hover event */
// show arrow image in the right side of the title upon hover
jQuery('.collapsible').hover(function(){
    var w = $(this).children('h3').outerWidth(true)+5;
    jQuery(this).prepend('<span class="collapse-widget" style="left: '+w+'px">&nbsp;&nbsp;</span>');
}, function(){
    jQuery(this).children('.collapse-widget').remove()
});


//show/hide widget content when widget title is clicked
jQuery('.collapsible').click(function(){
    if(jQuery(this).next().is(':visible')) {
        jQuery(this).next().slideUp('fast');
    } else {
        jQuery(this).next().slideDown('fast');
    }
});


// Responsive js
jQuery(window).load(function(){
    responsiveWindow();
    montageGallery();
});

//screen resize
jQuery(window).resize(function(){
    responsiveWindow();
    montageGallery();
});

function responsiveWindow(){
    if(jQuery(this).width() < 600 || jQuery(this).width() <= 800) {
        jQuery('.sidebar_navigation ul li a > span').css({visibility: 'hidden'});
        jQuery('.sidebar_navigation ul li').css({height: '55px'});
    }else if(jQuery(this).width() > 1000){
        jQuery('.sidebar_navigation ul li a > span').css({visibility: 'visible'});
        jQuery('.sidebar_navigation ul li').css({height: 'auto'});
    }
}


function montageGallery(){
    try{
        var $container 	= $('#am-container'),
            $imgs		= $container.find('img').hide(),
            totalImgs	= $imgs.length,
            cnt			= 0;

        $imgs.each(function(i) {
            var $img	= $(this);
            $('<img/>').load(function() {
                ++cnt;
                if( cnt === totalImgs ) {
                    $imgs.show();
                    $container.montage({
                        fillLastRow				: true,
                        alternateHeight			: true,
                        margin 	: 2,
                        liquid: true,
                        alternateHeightRange	: {
                            min	: 100,
                            max	: 250
                        }
                    });
                }
            }).attr('src',$img.attr('src'));
        });
    } catch (e){
        errorMessage(e);
    }
}

function ie8(){
    $(".header_wrapper .user_nav > li:first-child").css({borderLeft: 'none'});
    $(".header_wrapper .user_nav > li:first-child, .header_wrapper .user_nav > li:last-child").css({height: '38px', padding: 0});
    $(".header_wrapper .user_nav > li:last-child").css({borderRight:"none"});
}