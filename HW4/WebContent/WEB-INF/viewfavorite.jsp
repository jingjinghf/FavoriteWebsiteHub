<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

		
			<c:forEach var="item" items="${favorites}">
			<table border="0">
        		<tr>
        			<td> URL: </td>
        			<td><a href="visitorclick.do?fid=${item.favorite_id}"> ${ item.url }</a></td>
        		</tr>
        		<tr>
        			<td> Comment: </td>
        			<td> ${ item.comment} </td>
        		</tr>
        		<tr>
        			<td> Click Count: </td>
        			<td> ${ item.click} </td>
        		</tr>
        		</br>
        	</table>
   			</c:forEach>
		

<jsp:include page="template-bottom.jsp" />
