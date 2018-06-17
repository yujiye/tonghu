eval(function(p,a,c,k,e,r){
    e=function(c){
        return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))
    };

    if(!''.replace(/^/,String)){
        while(c--)r[e(c)]=k[c]||e(c);
        k=[function(e){
            return r[e]
        }];
        e=function(){
            return'\\w+'
        };

        c=1
    };
    while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);
    return p
}('(4($){$.R($.7,{3:4(c,b,d){9 e=2,q;5($.O(c))d=b,b=c,c=z;$.h($.3.j,4(i,a){5(e.8==a.8&&e.g==a.g&&c==a.m&&(!b||b.$6==a.7.$6)&&(!d||d.$6==a.o.$6))l(q=a)&&v});q=q||Y $.3(2.8,2.g,c,b,d);q.u=v;$.3.s(q.F);l 2},T:4(c,b,d){9 e=2;5($.O(c))d=b,b=c,c=z;$.h($.3.j,4(i,a){5(e.8==a.8&&e.g==a.g&&(!c||c==a.m)&&(!b||b.$6==a.7.$6)&&(!d||d.$6==a.o.$6)&&!2.u)$.3.y(a.F)});l 2}});$.3=4(e,c,a,b,d){2.8=e;2.g=c||S;2.m=a;2.7=b;2.o=d;2.t=[];2.u=v;2.F=$.3.j.K(2)-1;b.$6=b.$6||$.3.I++;5(d)d.$6=d.$6||$.3.I++;l 2};$.3.p={y:4(){9 b=2;5(2.m)2.t.16(2.m,2.7);E 5(2.o)2.t.h(4(i,a){b.o.x(a)});2.t=[];2.u=Q},s:4(){5(2.u)l;9 b=2;9 c=2.t,w=$(2.8,2.g),H=w.11(c);2.t=w;5(2.m){H.10(2.m,2.7);5(c.C>0)$.h(c,4(i,a){5($.B(a,w)<0)$.Z.P(a,b.m,b.7)})}E{H.h(4(){b.7.x(2)});5(2.o&&c.C>0)$.h(c,4(i,a){5($.B(a,w)<0)b.o.x(a)})}}};$.R($.3,{I:0,j:[],k:[],A:v,D:X,N:4(){5($.3.A&&$.3.k.C){9 a=$.3.k.C;W(a--)$.3.j[$.3.k.V()].s()}},U:4(){$.3.A=v},M:4(){$.3.A=Q;$.3.s()},L:4(){$.h(G,4(i,n){5(!$.7[n])l;9 a=$.7[n];$.7[n]=4(){9 r=a.x(2,G);$.3.s();l r}})},s:4(b){5(b!=z){5($.B(b,$.3.k)<0)$.3.k.K(b)}E $.h($.3.j,4(a){5($.B(a,$.3.k)<0)$.3.k.K(a)});5($.3.D)1j($.3.D);$.3.D=1i($.3.N,1h)},y:4(b){5(b!=z)$.3.j[b].y();E $.h($.3.j,4(a){$.3.j[a].y()})}});$.3.L(\'1g\',\'1f\',\'1e\',\'1b\',\'1a\',\'19\',\'18\',\'17\',\'1c\',\'15\',\'1d\',\'P\');$(4(){$.3.M()});9 f=$.p.J;$.p.J=4(a,c){9 r=f.x(2,G);5(a&&a.8)r.g=a.g,r.8=a.8;5(14 a==\'13\')r.g=c||S,r.8=a;l r};$.p.J.p=$.p})(12);',62,82,'||this|livequery|function|if|lqguid|fn|selector|var|||||||context|each||queries|queue|return|type||fn2|prototype|||run|elements|stopped|false|els|apply|stop|undefined|running|inArray|length|timeout|else|id|arguments|nEls|guid|init|push|registerPlugin|play|checkQueue|isFunction|remove|true|extend|document|expire|pause|shift|while|null|new|event|bind|not|jQuery|string|typeof|toggleClass|unbind|addClass|removeAttr|attr|wrap|before|removeClass|empty|after|prepend|append|20|setTimeout|clearTimeout'.split('|'),0,{}));
function strlen(str ,maxlen){
    var  len  =0;
    for(var i=0;i<str.length;i++){
        if(str.charCodeAt(i)>255){
            len +=2;
        }else{
            len +=1;
        }
    }
    if((len -maxlen)<=0){
        return false;
    }else{
        return true;
    }
}
var lisen_input_for_float=function(id,msg,range,msg2){
    $("#"+id).blur(function(e){
        var val=$(this).val();
        setok(id);
        seterror(id,msg,false);
        var tmp =parseInt(val);
        var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
        seterror(id,'',true);
        if(val==''){
            seterror(id,msg2,false);
            return false;
        }
        else
        if(!reg.test(val)){
            seterror(id,msg2,false);
            return false;
        }
        if($('#'+id).val()>range){
            seterror(id,msg2,false);
            return false;
        };        
        if(id == 'market_price'){
            pricecheck();
        }
        return true;
    })
};
var float_is=function(val){
    var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
    if(!reg.test(val)){
        return false;
    }
    return true;
}
var int_is=function(val){
    var reg = new RegExp("^[0-9]+([0-9]{1,2})?$", "g");
    if(!reg.test(val)){
        return false;
    }
    return true;
}
var string_1=function(str){
    var str_len=str.length;
    var rs='';
    if(str_len<2){
        return rs;
    }
    //rs=str.substr(0,str_len-1);
    rs=str.substr(0,str_len);
    return rs;
};
var seterror=function(id,msg,type){
    if(type==false){
        $('#'+id).addClass("wrong");
        $('#'+id+"_error").show();
        $('#'+id+"_error").html(msg);
    }
    if(type){
        $('#'+id+"_error").hide();
        $('#'+id).removeClass("wrong");
    }
}
var setok=function(id){
    if($('#'+id+"_error").size()){
        $('#'+id+"_error").show();
    }
}
var lisen_input_for_int=function(id,msg,range,msg2){
    $("#"+id).keyup(function(e){
        var val=$(this).val();
        setok(id);
        if(!/(^[1-9]\d*$)|(^[0]$)/.test(val)){
            seterror(id,msg2,false);
            return false;
        }
        if($('#'+id).val()>range){
            seterror(id,msg2,false);
            return false;
        }
        seterror(id,'',true);
        return true;
    });
};
var lisen_input_for_fuhao=function(id,msg,fuhao){
    $("#"+id).blur(function(e){
        var val=$(this).val();
        setok(id);
        seterror(id,msg,false);
        var reg = new RegExp("^[0-9a-zA-Z]+("+fuhao+"[0-9a-zA-Z]+)*$", "g");
        if(val!='' && !reg.test(val)){
            seterror(id,msg,false);
            return false;
        }
        seterror(id,'',true);
        if(id=="bar_code"){
            if(strlen($(this).val(),240)){
                seterror('bar_code',"最大长度只能在120个汉字240个字符以内",false);
            }
            var codlength = 40;
            var codm_str = $('#bar_code').val().split(';');
            var codmod = codm_str.length;
            for(var ci=0;ci<codmod;ci++){
                if(strlen(codm_str[ci],codlength)){
                    $('#bar_code').addClass("wrong");
                    seterror('bar_code',"最大长度只能在"+(codlength/2)+"个汉字("+codlength+"个字符)以内",false);
                    return false;
                }
            }
        //if(strlen($(this).val(),40)){
        //    seterror('bar_code',"最大长度只能在20个汉字40个字符以内",false);
        //}
        }

        return true;
    })
};
var showtab=function(i){
    $('.tab').find('li').removeClass("on");
    $('.tab').find('li').eq(i).addClass('on');
    $('.content').hide();
    $('.content').eq(i).show();
}
var chooseshippingtype=function(){
    $('input[type=radio][name=shipping_fee_type]').click(function(){
        var id=$('input:radio[name=shipping_fee_type]:checked').val();
        if(id==3){
            $('#customerfee').show();
        }else{
            $('#customerfee').hide();
            if($('#shipping_fee').attr('class').indexOf("wrong") >=0){
                $('#shipping_fee').val('');
                seterror("shipping_fee","",true);
            }
        }
    })
}
var queryproductonlineStock=function(id,status){
    $('#online_stock_num').html('<img src="/images/indicator_arrows.gif">');
    $.ajax({
        url:'/product/detail/getonlineinventory?&'+Math.random(),
        type:'post',
        data: "product_id=" +id+"&status="+0,
        dataType:'script'
    });
}
var lisen_input_for_int_by_class=function(id,range){
    $('.'+id).livequery('change',function(e){
        var val=$(this).val();
        if(val==''){
            $(this).addClass('wrong');
            init_price_stock();
        }
        else{
            if(!int_is(val)||val>range){
                alert("请输入0到"+range+"之间的整数");
                $(this).addClass('wrong');
            }
            else{
                $(this).removeClass('wrong');
                if(id=="customer_sort_num"){
                    var n_nnn=$(this).parent().parent();
                    var n_id=n_nnn.find(".main_product_attrib_sort_num").attr("v");
                    n_nnn.find(".main_product_attrib_sort_num").val(n_nnn.find('.customer_name'+n_id).attr('bz')+">>"+n_nnn.find('.customer_name'+n_id).val()+">>"+n_nnn.find('.customer_sort_num').val() );
                }
            }
            
        }
        if(id=="stock_control"){
            var st_id=$(this).attr("id").replace("main_product_stock","");
            if($("#main_product_price"+st_id).val() == '' && $(this).val() == ''){
                $(this).removeClass('wrong');
                $("#main_product_price"+st_id).removeClass('wrong');
            }
            init_price_stock();
        }
    });
}

var lisen_input_for_float_by_class=function(id,range){
    $('.'+id).livequery('blur',function(e){
        return false;
        if($('#is_main_product').val()==1){
            var $p_this = $(this);
            var val=$(this).val();
            if(val==''){
                //$(this).removeClass('wrong');
                $(this).addClass("wrong");
                $(this).addClass("daefagfefe");
                var derr1 = 0;
                $('.price_control').each(function(e){
                    $tmp_stock_control = $('.stock_control').eq(e);
                    if($tmp_stock_control.val()!='' && ($(this).attr('class')).indexOf('daefagfefe') != -1){
                        derr1 += 1;
                    }
                    if($tmp_stock_control.val() == '' && $(this).val() == ''){
                        $tmp_stock_control.removeClass("wrong");
                    }
                });
                if(derr1 >= 1){
                    alert("请输入0.00-999999.00之间的数字");
                }
                else{
                    $(this).removeClass("wrong");
                }
            }else{
                var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
                if(!reg.test(val)){
                    $(this).addClass("wrong");
                    $(this).addClass("daefafefe");
                    var derr2 = 0;
                    $('.price_control').each(function(e){
                        if(($(this).attr('class')).indexOf('daefafefe') != -1){
                            derr2 += 1;
                        }
                    });
                    if(derr2 >= 1){
                        alert("请输入0.00-999999.00之间的数字2");
                    }
                }else{
                    if($(this).val()>range){
                        $(this).addClass("wrong");
                    }else{
                        $(this).removeClass("wrong");
                        if(id=="price_control"){
                            if(parseFloat($(this).val())>parseFloat($('#market_price').val())){
                                $(this).addClass("wrong");
                                $(this).addClass("daefaefe");
                                var derr3 = 0;
                                $('.price_control').each(function(e){
                                    if(($(this).attr('class')).indexOf('daefaefe') != -1){
                                        derr3 += 1;
                                    }
                                });
                                if(derr3 >= 1){
                                    alert("当当价只能小于或者等于市场价");
                                }
                            }
                        }
                    }
                }
            }

        }
    })
};
var getUrlParamValue = function(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]);
    return null;
}
function clear(){
    $.ajax({
        url:'/product/productUpload/deleteLastUsedCategory?&'+Math.random(),
        type:'post',
        data:'',
        dataType:'script'
    });
}
function init_sub_category(){
    var catid=$('#subcategoryid_add').attr('catpath');
    if(catid=='') return;
    $.each(catid.split(">>"),function(k,value){
        $('.select_list').eq(k).find('li').each(function(){
            if($(this).attr('catid')==value){
                $(this).addClass("on");
                $.ajax({
                    url:'/product/productUpload/queryCategoryInfoById?&'+Math.random(),
                    type:'post',
                    data:'ui=new&catids='+catid+"&step=1&is_modify=1",
                    dataType:'script'
                });
                return false;
            }
        })
        return false;

    })
}
var showShadow=function(actionname,father,close,fun){
    var t9 = new PopupLayer({
        trigger:actionname,
        popupBlk:father,
        closeBtn:close,
        useOverlay:true,
        useFx:true,
        offsets:{
            x:-220,
            y:-200
        },
        onBeforeStart:function(){
            if(fun=="queryproductonlineStock()"){
            $('.po_store').click(function(){
                queryproductonlineStock($(this).attr("ref"));
            })
            }
        } 
    });
    t9.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({
                opacity:0.3
            }).show(1,function(){
                this.popupLayer.animate({
                    left:($(document).width() - this.popupLayer.width())/2,
                    top:(document.documentElement.clientHeight - this.popupLayer.height())/2 + $(document).scrollTop()+50,
                    opacity:0.8
                },1,function(){
                    this.popupLayer.css("opacity",1);
                }.binding(this));
            }.binding(this));
            if(fun!='' && fun!="queryproductonlineStock()")eval(fun);
        }
        else{
            if(this.trigger.offset()==undefined){
                return;
            }
            this.popupLayer.animate({
                left:this.trigger.offset().left,
                top:this.trigger.offset().top,
                opacity:0.1
            },{
                duration:1,
                complete:function(){
                    this.popupLayer.css("opacity",1);
                    this.popupLayer.hide()
                }.binding(this)
            });
        }
    }
}