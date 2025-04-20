<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Details</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="result-container">
    <h2>Feedback Details</h2>

    <c:if test="${not empty feedback}">
        <table border="1" cellpadding="8" cellspacing="0">
            <tr><th>ID</th><td>${feedback.id}</td></tr>
            <tr><th>Name</th><td>${feedback.name}</td></tr>
            <tr><th>Email</th><td>${feedback.email}</td></tr>
            <tr><th>Rating</th><td>${feedback.rating}</td></tr>
            <tr><th>Comment</th><td>${feedback.comment}</td></tr>
            <tr><th>Allow Contact</th>
                <td>
                    <c:choose>
                        <c:when test="${feedback.allowContact}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr><th>Submitted At</th><td>${feedback.submittedAt}</td></tr>
        </table>

        <br/>
        <a href="feedback?action=edit&id=${feedback.id}">Edit</a>
        <a href="feedback?action=delete&id=${feedback.id}" onclick="return confirm('Delete this feedback?');">Delete</a>
    </c:if>

    <br/>
    <a href="feedback?action=list">← Back to List</a>
</div>
</body>
</html>

