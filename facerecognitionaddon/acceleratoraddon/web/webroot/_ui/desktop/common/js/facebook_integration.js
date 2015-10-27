/**
 * 
 */
		window.fbAsyncInit = function() {
	        FB.init({
	          appId      : '696335297165134',
	          xfbml      : true,
	          version    : 'v2.4'
	        });
	      };
      

      (function(d, s, id){
         var js, fjs = d.getElementsByTagName(s)[0];
         if (d.getElementById(id)) {return;}
         js = d.createElement(s); js.id = id;
         js.src = "//connect.facebook.net/en_US/sdk.js";
         fjs.parentNode.insertBefore(js, fjs);
       }(document, 'script', 'facebook-jssdk'));
	
      
      	// This method and its respective methods logs you into the fb account,
        // gets the picture url and logs out.

		 function checkLoginState() {
			
			    FB.getLoginStatus(function(response) {
			    	/*alert("In checkLoginState. Response is "+response.status);*/
			      statusChangeCallback(response);
			    });
			  }
		
		 function statusChangeCallback(response) {
			 if (response.status === 'connected') {
			      // Logged into your app and Facebook.
			      getUserProfile();
			    }
		 }
		 
		 
		 function getUserProfile() {
		 
			FB.api(
					  '/me/picture',
					  'GET',
					  {"type":"large"},
					 	function(response) {
						
						  document.getElementById('url').innerHTML = 'The Picture url is , ' + response.data.url;
						  $('#hiddenUrl').val(response.data.url);
						  sendImageURL();
					  }
					);
			
			myFacebookLogout();
			    
			}
		 
		 function myFacebookLogout() {
			 
			 FB.logout(function(response) {
		        // Person is now logged out
		    	});
		 }
		 
