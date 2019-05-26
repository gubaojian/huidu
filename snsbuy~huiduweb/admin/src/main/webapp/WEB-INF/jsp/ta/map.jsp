<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${lang  eq 'zh'}">
        <c:set value="TA在哪" var="title"/>
        <c:set value="设备不支持GPS定位" var="device_not_support"/>
        <c:set value="TA拒绝远程定位请求" var="deny_gps_location"/>
        <c:set value="远程定位失败" var="location_failed"/>
        <c:set value="远程定位超时" var="location_timeout"/>
        <c:set value="远程定位成功，TA成功获知了您的位置。" var="location_success_tips"/>
        <c:set value="远程定位请求" var="gps_location_title"/>
        <c:set value="TA向您发送了一条“远程定位”的请求信息。若您希望TA获知您的位置，请在使用位置的对号框中点击好；否则，请点击不允许。" var="gps_location_desc"/>
        <c:set value="下载APP" var="download_app"/>
    </c:when>
    <c:otherwise>
        <c:set value="Where U" var="title"/>
        <c:set value="Device Not Support GPS Location" var="device_not_support"/>
        <c:set value="GPS Location Denied" var="deny_gps_location"/>
        <c:set value="GPS Location Failed" var="location_failed"/>
        <c:set value="GPS Location Timeout" var="location_timeout"/>
        <c:set value="GPS locating success, your location has delivered to your friend (family)." var="location_success_tips"/>
        <c:set value="Where U" var="gps_location_title"/>
        <c:set value="You see this page because you have a friend (family) that sends you a request for GPS location. click Ok on the dialog if you allow him (her) to get your location; Otherwise, click Don't Allow." var="gps_location_desc"/>
        <c:set value="Download APP" var="download_app"/>
     </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<link rel="icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<link rel="shortcut icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
	margin: 0 auto;
	padding: 0 auto;
	background-color:#4CAF50;
    background:#4CAF50;
    color: #FFFFFF;
}

body {
	background-color:#4CAF50;
        background:#4CAF50;
	max-width: 600px;
	min-height: 300px;
}

.content-font {
	font-size: 20px;
	margin-top: 20px;
}

.content-info-div-zh-cn {
	margin-top: 6px;
	margin-left: 6px;
	margin-right: 6px;
	text-indent: 0em;
	line-height: 120%;
	font-size: 18px;
	text-align: justify;
	text-justify: inter-ideograph;
}

</style>
<script type="text/javascript">            
            if (document.addEventListener ){
               document.addEventListener( "DOMContentLoaded", 
                 function(){
                   handleCollectGeoLocationRequest();
                },
                false);
            } else if (document.attachEvent) {
                document.attachEvent("onreadystatechange", function(){
                     if(document.readyState != 'complete'){
                       return;
                     }
                     handleCollectGeoLocationRequest();
               });
           }
            
            function goHome() {
				window.location.replace("http://ta.lanxijun.com/d.html?lang=${lang}");
			}

           function  handleCollectGeoLocationRequest(){
               
                locationData= {
                    "status" : null,
                    "poi": null,
                    "latitude"  : null,
                    "longitude" : null,
                    "token"  :"${token}",
                    "id"  :"${id}"  
                };
                
                
                
                if(navigator.geolocation){
                    navigator.geolocation.getCurrentPosition(getPosition, getPositionError, {enableHighAccuracy:true, timeout:8000, maximumAge:1000});
                }else{
                    locationData.status = -2;
                    //locationData.poi = "${device_not_support}";
                    ajaxGeoLocation(locationData);
                }
                
           
                /**获取位置*/
                function getPosition(position){
                    locationData.latitude = position.coords.latitude;
                    locationData.longitude = position.coords.longitude; 
                    locationData.status = 2;
                    ajaxGeoLocation(locationData);
                }

                function getPositionError(error){
                        switch(error.code) 
                        {
                            case error.PERMISSION_DENIED:
                            	    locationData.status = -3;
                                //locationData.poi = "${deny_gps_location}"; 
                                break;
                            case error.POSITION_UNAVAILABLE:
                            	   locationData.status = -5;
                                //locationData.poi = "${location_failed}";
                                break;
                            case error.TIMEOUT:
                                locationData.status = -4;
                                //locationData.poi = "${location_timeout}";
                                break;
                            case error.UNKNOWN_ERROR:
                            	    locationData.status = -4;
                            	    //locationData.poi = "${location_timeout}";
                            break;
                        }
                        ajaxGeoLocation(locationData);
                }
                
               

                function ajaxGeoLocation(data){
                	    //你的请求已过期
                   	var tips = locationData.poi;
                    if(!tips){
                       tips ="${location_success_tips}"
                    }
                    
                    
                    //你的请求
                    var  request;
                    if(window.XMLHttpRequest){
                       request =  new XMLHttpRequest();
                    }else{
                       request = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    request.open("POST", "ajaxLocation.htm", true);  // asynchronously 
                    request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                    
                    request.onreadystatechange =  function(){
                    	  if(request.readyState == 4){
                    			var contentDiv = document.getElementById("content-info-div");
                                if(contentDiv){
                                   if(contentDiv.innerHTML){
                                       contentDiv.innerHTML   = tips ;
                                   }else if(contentDiv.innerText){
                                       contentDiv.innerText = tips;
                                   }else if(contentDiv.textContent){
                                       contentDiv.textContent = tips;  
                                   }
                   				   setTimeout(function(){
                   					   goHome();
                   				   }, 1200);
                                }
                    	  }
                    };
                    
                    
                    var  query = "";
                    for(var key in data){
                        if(data[key]){
                            query +=(key + "=" + encodeURI(data[key]) + "&");
                        } 
                    }
                    
                    request.send(query);  
                            
                    
                }
           }
         </script>
</head>
<body>
	<div id="content" class="content-font">
		<h4 align="center">${gps_location_title}</h4>
		<div id="content-info-div" class="content-info-div-zh-cn">
		       ${gps_location_desc}
		</div>
		<br/>
		<br/>
		<h5 align="center"><a href="http://ta.lanxijun.com/d.html?lang=${lang}&from=title">${download_app}</a></h5>
		<br/>
	</div>
</body>
</html>