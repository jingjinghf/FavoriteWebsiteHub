<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<c:forEach var="error" items="${errors}">
			<p style="font-size:medium; color:red"> ${error} </>
		</c:forEach>
