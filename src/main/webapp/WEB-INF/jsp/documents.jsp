<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
</head>
<body>

<h2>Document Handler</h2>


<form:form action="save" modelAttribute="document" enctype="multipart/form-data">
	<form:errors path="*" cssClass="error"/>
	<table>
	<tr>
		<td><form:label path="name">Name</form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	<tr>
		<td><form:label path="description">Description</form:label></td>
		<td><form:textarea path="description" /></td>
	</tr>
	<tr>
		<td><form:label path="content">Document</form:label></td>
		<td><input type="file" name="file" id="file"></input></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="Upload"/>
		</td>
	</tr>
</table>	
</form:form>

<br/>
<h3>Document List</h3>
<c:if  test="${!empty documentList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Description</th>
	<th>view</th>
	<th>delete</th>
</tr>
<c:forEach items="${documentList}" var="document">
	<tr>
		<td width="100px">${document.name}</td>
		<td width="250px">${document.description}</td>
		<td width="20px">
			<a href="download/${document.id}">
			<img src="images/save_icon.gif" border="0" title="Download this document" width="50" height="50"/></a> 
		</td>
		<td>
			<a href="remove/${document.id}"
				onclick="return confirm('Are you sure you want to delete this document?')">
				<img src="images/delete_icon.gif" border="0" title="Delete this document" width="170" height="80"/></a> 
		</td>
	</tr>
</c:forEach>
</table>
</c:if>
</body>
</html>