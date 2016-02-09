<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />
<p>
	<form method="post" action="add.do">
		<table border="1">
			<tr>
				<td> URL: </td>
				<td><input type="text" name="url" value=""/></td>
			</tr>
			<tr>
				<td> Comment: </td>
				<td><input type="text" name="comment" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Add Favorite"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<jsp:include page="error-list.jsp" />

<p style="font-size: x-large">You have ${ fn:length(favorites) } favorite sites.</p>

		
			<c:forEach var="item" items="${favorites}">
			<table border="0">
           		<tr>
       				<td>
			            <form action="delete.do" method="POST">
                			<input type="hidden" name="id" value="${ item.favorite_id }" />
                			<input type="submit" name="button" value="Delet This Site" />
           				</form>
        			</td>
        		</tr>
        		<tr>
        			<td> URL: </td>
        			<td><a href="userclick.do?fid=${item.favorite_id}"> ${ item.url }</a></td>
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
		

       	Click <a href="logout.do">here</a> to log out.

<jsp:include page="template-bottom.jsp" />
