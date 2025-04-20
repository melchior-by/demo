<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>All Feedback</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="result-container">
    <h2>All Feedback</h2>
    <c:if test="${empty feedbackList}">
        <p>No feedback yet.</p>
    </c:if>
    <c:if test="${not empty feedbackList}">
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Rating</th>
                <th>Comment</th>
                <th>Allow Contact</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="f" items="${feedbackList}">
                <tr>
                    <td>${f.id}</td>
                    <td>${f.name}</td>
                    <td>${f.email}</td>
                    <td>${f.rating}</td>
                    <td>${f.comment}</td>
                    <td>
                    <c:choose>
                        <c:when test="${f.allowContact}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                    </td>
                    <td>
                        <a href="feedback?action=view&id=${f.id}">View</a>
                        <a href="feedback?action=edit&id=${f.id}">Edit</a>
                        <a href="feedback?action=delete&id=${f.id}" onclick="return confirm('Delete this feedback?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <br/>
    <a href="form.jsp">Add New Feedback</a>
    <a href="index.jsp">← Back to main</a>
</div>
</body>
</html>