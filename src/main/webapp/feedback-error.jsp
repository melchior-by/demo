<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="result-container">
    <h2>Oops! Something went wrong.</h2>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;"><strong>Error:</strong> ${errorMessage}</p>
    </c:if>

    <br/>
    <a href="feedback">← Back to form</a>
</div>
</body>
</html>

