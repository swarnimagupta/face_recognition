<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<json:object>
	<json:property name="upload_ajax_fragment" escapeXml="false">
		<c:if test="${not empty message}">
			<h5>${message}</h5>
		</c:if>
		<h3>File Upload:</h3>
		<form id="form2" method="post" action="/uploadImage" enctype="multipart/form-data">
		  <!-- File input -->    
		  <input name="file2" id="file2" type="file" /><br/>
		</form>
	 
		<button value="Submit" onclick="uploadFormData()" >Upload</button>
	</json:property>
</json:object>
