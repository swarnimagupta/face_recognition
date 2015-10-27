<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<script  type="text/javascript" src="${commonResourcePath}/../../addons/qrcodeaddon/desktop/common/bnc_js/new/jquery-1.11.0.min.js"></script>
</head>
<script type="text/javascript">

function imageUpload()
{
	var uploadedImage = document.getElementById("uploadedImage").value;
	$.ajax({
		type : 'POST',
		url : "${contextPath}/uploadImage",
		data : "uploadedImage=" + uploadedImage,
		dataType : 'json',
		success : function(response) 
		{
			$("#uploadingImageId").html(response.upload_ajax_fragment);
		},
		error : function(e) 
		{
			alert("Please select proper file with Image format!");
		}
	});
}

function uploadFormData()
{
	var oMyForm = new FormData();
	oMyForm.append("file", file2.files[0]);
	$.ajax({
	    url: '${contextPath}/electronics/en/my-account/profile/uploadImage',
	    data: oMyForm,
	    processData: false,
	    contentType: 'application/json',
        dataType: 'json',
	    type: 'POST',
	    success: function(data){
	    	$("#uploadingImageId").html(response.upload_ajax_fragment);
	    },
	    error : function(e) 
		{
			alert("Please select proper file with Image format!");
		}
	  });
}

function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie != '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) == (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}
$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
            xhr.setRequestHeader("X-CSRFToken", getCookie('csrftoken'));
        }
    }
});
</script>
<div id="uploadingImageId">
	<h3>File Upload:</h3>
	<form id="form2" method="post" action="/uploadImage" enctype="multipart/form-data">
	  <!-- File input -->    
	  <input name="file2" id="file2" type="file" /><br/>
	</form>
 
	<button value="Submit" onclick="uploadFormData()" >Upload</button>
	
	<!-- facebook code starts -->	
	<script type="text/javascript" src="${commonResourcePath}/../../addons/facerecognitionaddon/desktop/common/js/facebook_integration.js"></script>	
	<fb:login-button scope="public_profile,email" onlogin="checkLoginState();"></fb:login-button>
	<div id="status"></div>
	<div id="url"></div>
	<!--  facebook code ends* -->
</div>