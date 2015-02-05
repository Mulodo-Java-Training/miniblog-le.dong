<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Account Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  </script>
</head>
<body>
<h1>
	Add a Account
</h1>

<c:url var="add" value="/account/add" ></c:url>

<form:form action="${add}" commandName="acc">
<table>
	<c:if test="${!empty acc.username}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"  disabled="true" />
			<form:hidden path="id" />
		</td> 
	</tr>
	</c:if>
	<tr>
		<td>
			<form:label path="username">
				<spring:message text="Username"/>
			</form:label>
		</td>
		<td>
			<form:input path="username" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="password">
				<spring:message text="Password"/>
			</form:label>
		</td>
		<td>
			<form:password path="password" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="firstname">
				<spring:message text="First Name"/>
			</form:label>
		</td>
		<td>
			<form:input path="firstname" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="lastname">
				<spring:message text="Last Name"/>
			</form:label>
		</td>
		<td>
			<form:input path="lastname" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="email">
				<spring:message text="Email"/>
			</form:label>
		</td>
		<td>
			<form:input path="email" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="create_at">
				<spring:message text="Create Date"/>
			</form:label>
		</td>
		<td>
			<form:input path="create_at" id="datepicker"/>
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="modified_at">
				<spring:message text="Modified Date"/>
			</form:label>
		</td>
		<td>
			<form:input path="modified_at" id="datepicker"/>
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="status">
				<spring:message text="Status"/>
			</form:label>
		</td>
		<td>
			<form:radiobutton value="0" label="Active" path="status"/>
		</td> 
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty acc.username}">
				<input type="submit"
					value="<spring:message text="Edit"/>" />
			</c:if>
			<c:if test="${empty acc.username}">
				<input type="submit"
					value="<spring:message text="Add"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>Account List</h3>
<c:if test="${!empty listAccounts}">
	<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="120">Name</th>
		<th width="120">Email</th>
		<th width="60">Edit</th>
		<th width="60">Delete</th>
	</tr>
	<c:forEach items="${listAccounts}" var="acc">
		<tr>
			<td>${acc.id}</td>
			<td>${acc.username}</td>
			<td>${acc.email}</td>
			<td><a href="<c:url value='/edit/${acc.id}' />" >Edit</a></td>
			<td><a href="<c:url value='/delete/${acc.id}' />" >Delete</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
