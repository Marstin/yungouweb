$.fn.StartTimeOut=function(t,I){var u=$(this);var L=new Date();L.setSeconds(L.getSeconds()+I);var A=0;var x=0;var y=0;var B=function(){var b=new Date();if(L>b){var a=parseInt((L.getTime()-b.getTime())/1000);var c=a%60;A=parseInt(a/60);x=parseInt(c);if(c>=x){y=parseInt((c-x)*10)}else{y=0}setTimeout(B,3000)}};var K=u.find("span.minute");var C=u.find("span.second");var H=u.find("i.millisecond");var v=u.find("i.last");var F=9;var z=function(){F--;if(F<0){F=9}v.html(F);setTimeout(z,10)};var J=function(){v.html("0");u.find("#jiexiaoTime").html('<div class="pour-are">\u6b63\u5728\u8ba1\u7b97...</div>');var a=null;var b=function(){$.ajax({url:"/lottery/lotteryUtil.html?id="+t,type:"GET",success:function(c){if(c=="true"){$.ajax({url:"/lottery/LatestlotteryByProductId.html?id="+t,type:"GET",success:function(d){if(d!="false"){var e="";e+='<s class="lottery-tips"></s><a class="fl goodsimg" href="/lotteryDetail/'+t+'.html" target="_blank" title="(\u7b2c'+d.productPeriod+"\u671f)"+d.productName+'">';e+='<img src="'+$img+d.productImg+'" alt="" /></a>';e+='<div class="publishC-Member gray02">';e+='<a class="fl headimg" href="/u/'+d.userId+'.html" target="_blank" title="'+d.userName+'">';e+='<img src="'+d.userFace+'" alt="" /></a><p>';e+='\u83b7\u5f97\u8005\uff1a<a class="blue Fb" href="/u/'+d.userId+'.html" target="_blank" title="'+d.userName+'">'+d.userName+"</a></p>";e+="<p>\u6765\u81ea\uff1a"+d.location+"</p><p>"+$shortName+'\uff1a<em class="orange Fb">'+d.buyNumberCount+"</em>\u4eba\u6b21</p>";e+="</div>";e+='<div class="publishC-tit">';e+='<h3><a href="/lotteryDetail/'+t+'.html" target="_blank" class="gray01">(\u7b2c'+d.productPeriod+"\u671f)"+d.productName+"</a></h3>";e+='<p class="money">\u5546\u54c1\u4ef7\u503c\uff1a<span class="rmb">'+d.productPrice+"</span></p>";e+='<p class="Announced-time gray02">\u63ed\u6653\u65f6\u95f4\uff1a'+d.announcedTime+"</p></div>";e+='<div class="details"><p class="fl details-Code">\u5e78\u8fd0'+$shortName+'\u7801\uff1a<em class="orange Fb">'+d.randomNumber+"</em></p>";e+='<a class="fl details-A" href="/lotteryDetail/'+t+'.html" rel="nofollow" target="_blank">\u67e5\u770b\u8be6\u60c5</a>';u.removeClass("lottery").html(e);u.hover(function(){u.addClass("Cursor")},function(){u.removeClass("Cursor")});if(a!=null){clearInterval(a);a=null}}}})}}})};a=setInterval(b,2000)};var D=function(){y--;if(y<1){if(x<1){if(A<1){J();return}else{A--}x=59}else{x--}y=9}setTimeout(D,100)};var G=0,w=0;var E=function(){H.html(y);if(G!=x){if(x<10){C.html("0"+x)}else{C.html(x)}G=x}if(w!=A){if(A<10){K.html("0"+A)}else{K.html(A)}w=A}setTimeout(E,100)};B();D();z();E()};