<%@ page import="com.acc.util.InstagramClient" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	<title>facerecognition page</title>
	<!-- Edit Below -->
	<link rel="stylesheet" type="text/css" href="${commonResourcePath}/../../addons/facerecognitionaddon/desktop/common/css/style_browse_button.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<br>
<br>
<br>
<center>
	<div style="border:1px solid black; width:500px;padding:20px;">

<h3>Facerecognition Page</h3>
<br><br>
<div class="container">
	<div class="main">
       	<div class="customfile-container">
       		<form method="post" enctype="multipart/form-data"  action="facerecognitionpage">  
			   <table>  
				    <tr>  
					     <td>Upload File: </td>  
					     <td><input type="file" name="file" />  
					     </td>  
					     <td style="color: red; font-style: italic;">
					     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
					     </td>  
				    </tr>  
				    <tr>  
					     <td> </td>  
					     <td><input type="submit" value="Upload" />  
					     </td>  
					     <td> </td>  
				    </tr>  
			   </table>  
		  </form>  
		</div>
     </div>
</div><!-- Container -->
	
	<script type="text/javascript">
		;(function( $ ) {

		  // Browser supports HTML5 multiple file?
		  var multipleSupport = typeof $('<input/>')[0].multiple !== 'undefined',
		      isIE = /msie/i.test( navigator.userAgent );

		  $.fn.customFile = function() {

		    return this.each(function() {

		      var $file = $(this).addClass('customfile'), // the original file input
		          $wrap = $('<div class="customfile-wrap">'),
		          $input = $('<input type="text" class="customfile-filename" />'),
		          // Button that will be used in non-IE browsers
		          $button = $('<button type="button" class="customfile-upload">Open</button>'),
		          // Hack for IE
		          $label = $('<label class="customfile-upload" for="'+ $file[0].id +'">Open</label>');

		      // Hide by shifting to the left so we
		      // can still trigger events
		      $file.css({
		        position: 'absolute',
		        left: '-9999px'
		      });

		      $wrap.insertAfter( $file )
		        .append( $file, $input, ( isIE ? $label : $button ) );

		      // Prevent focus
		      $file.attr('tabIndex', -1);
		      $button.attr('tabIndex', -1);

		      $button.click(function () {
		        $file.focus().click(); // Open dialog
		      });

		      $file.change(function() {

		        var files = [], fileArr, filename;

		        // If multiple is supported then extract
		        // all filenames from the file array
		        if ( multipleSupport ) {
		          fileArr = $file[0].files;
		          for ( var i = 0, len = fileArr.length; i < len; i++ ) {
		            files.push( fileArr[i].name );
		          }
		          filename = files.join(', ');

		        // If not supported then just take the value
		        // and remove the path to just show the filename
		        } else {
		          filename = $file.val().split('\\').pop();
		        }

		        $input.val( filename ) // Set the value
		          .attr('title', filename) // Show filename in title tootlip
		          .focus(); // Regain focus

		      });

		      $input.on({
		        blur: function() { $file.trigger('blur'); },
		        keydown: function( e ) {
		          if ( e.which === 13 ) { // Enter
		            if ( !isIE ) { $file.trigger('click'); }
		          } else if ( e.which === 8 || e.which === 46 ) { // Backspace & Del
		            // On some browsers the value is read-only
		            // with this trick we remove the old input and add
		            // a clean clone with all the original events attached
		            $file.replaceWith( $file = $file.clone( true ) );
		            $file.trigger('change');
		            $input.val('');
		          } else if ( e.which === 9 ){ // TAB
		            return;
		          } else { // All other keys
		            return false;
		          }
		        }
		      });

		    });

		  };

		  // Old browser fallback
		  if ( !multipleSupport ) {
		    $( document ).on('change', 'input.customfile', function() {

		      var $this = $(this),
		          // Create a unique ID so we
		          // can attach the label to the input
		          uniqId = 'customfile_'+ (new Date()).getTime(),
		          $wrap = $this.parent(),

		          // Filter empty input
		          $inputs = $wrap.siblings().find('.customfile-filename')
		            .filter(function(){ return !this.value }),

		          $file = $('<input type="file" id="'+ uniqId +'" name="'+ $this.attr('name') +'"/>');

		      // 1ms timeout so it runs after all other events
		      // that modify the value have triggered
		      setTimeout(function() {
		        // Add a new input
		        if ( $this.val() ) {
		          // Check for empty fields to prevent
		          // creating new inputs when changing files
		          if ( !$inputs.length ) {
		            $wrap.after( $file );
		            $file.customFile();
		          }
		        // Remove and reorganize inputs
		        } else {
		          $inputs.parent().remove();
		          // Move the input so it's always last on the list
		          $wrap.appendTo( $wrap.parent() );
		          $wrap.find('input').focus();
		        }
		      }, 1);

		    });
		  }

		}( jQuery ));

		$('input[type=file]').customFile();
		
		
		function sendImageURL()
		{
			var url = $("#hiddenUrl").val();
			getBase64FromImageUrl(url);
		}
		
		function getBase64FromImageUrl(url) 
		{
			var xmlHTTP = new XMLHttpRequest();
		    xmlHTTP.open('GET',url,true);
		    xmlHTTP.responseType = 'arraybuffer';
		    var dataURL= "";
		    xmlHTTP.onload = function(e)
		    {

		        var arr = new Uint8Array(this.response);
		        var raw = String.fromCharCode.apply(null,arr);
		        var b64=btoa(raw);
		        var dataURL="data:image/jpeg;base64,"+b64;
		        alert("dataURL->"+dataURL);
		        document.getElementById("image").src = dataURL;
		        url = url.replace(/\./g,'DOT');
		        url = url.replace(/\//g,'SLASH');
		        url = url.replace(/&/g,'AMPERSAND');
		        $.ajax({
					type : 'GET',
					url : "${contextPath}/facerecognitionpage/submitImageURL",
					data : "url="+url,
					async : true,
					dataType : "text",
					crossDomain : true,
					success : function(response) 
					{
						$("#message").html("Image Uploaded successfully!!");
					},
					error : function(e) {
						$("#message").html("Please use a different image!!");
					}
				});
		    };

		    xmlHTTP.send();
		}
	</script>
	<br>
	<!-- facebook code starts -->	
	<script type="text/javascript" src="${commonResourcePath}/../../_ui/addons/facerecognitionaddon/desktop/common/js/facebook_integration.js"></script>	
	<fb:login-button scope="public_profile,email" onlogin="checkLoginState();"></fb:login-button>
	<div id="status"></div>
	<div id="url"></div>
	<br>
	<% 
		String code = request.getParameter("code"); 
		String profilePic = null;	
	
		if(code == null){
	%>
		<a href="https://api.instagram.com/oauth/authorize/?client_id=e580d04d0687403189f86d49545b69a4&redirect_uri=http://electronics.local:9001${contextPath}/electronics/en/facerecognitionpage&response_type=code">Upload Profile Picture</a> via Instagram 
 	<%
		} else {
			
			System.out.println("Code is "+code);
			InstagramClient instagramClient = new InstagramClient();
			profilePic = instagramClient.getProfilePicOfUser(code);
			
		%>User's Profile Pic is <%= profilePic %><%
		}
	%>
	<c:set var="imageUrl" value="<%= profilePic %>"></c:set>
	<input type="hidden" id="hiddenUrl" value="${imageUrl}"/>
	<c:if test="${not empty imageUrl}">
		<script type="text/javascript">
			document.getElementById("hiddenUrl").value='${imageUrl}';
			sendImageURL();
		</script>
	</c:if>
	<img id="image" alt="" />
	<!--  facebook code ends* -->
	<div id="message"></div>
	
	<br><br>
	<a href="http://electronics.local:9001${contextPath}">&lt;&lt;BACK</a>
	</div>
</center>
</body>

</html>