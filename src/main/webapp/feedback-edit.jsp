<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Feedback</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="result-container">
    <h2>Edit Feedback</h2>

    <form method="post" action="feedback">
        <input type="hidden" name="id" value="${feedback.id}" />

        <label for="name">Name:</label><br/>
        <input type="text" id="name" name="name" value="${feedback.name}" required /><br/><br/>

        <label for="email">Email:</label><br/>
        <input type="email" id="email" name="email" value="${feedback.email}" required /><br/><br/>

        <label for="rating">Rating (1-5):</label><br/>
        <input type="number" id="rating" name="rating" min="1" max="5" value="${feedback.rating}" required /><br/><br/>

        <label for="comment">Comment:</label><br/>
        <textarea id="comment" name="comment" rows="4" required>${feedback.comment}</textarea><br/><br/>

        <label>
            <input type="checkbox" name="allowContact" <c:if test="${feedback.allowContact}">checked</c:if> /> Allow contact
        </label><br/><br/>

        <button type="submit">Update Feedback</button>
    </form>

    <br/>
    <a href="feedback?action=view&id=${feedback.id}">← Back to Details</a>
</div>
</body>
</html>
