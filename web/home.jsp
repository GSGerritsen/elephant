<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Elephant</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <div class="sidebar-container">
        <div class="compose-section">
            <div class="button-compose">Compose</div>
        </div>
        <div class="inbox-section">
            <div class="inbox-words">Inbox (0)</div>
            <div class="inbox-words">Sent</div>
        </div>
    </div>
    <div class="messages-container">
        <div class="messages-column-wrapper">
            <div class="message-column-sorting-section">
                <div class="message-sorting-sort">Sort by</div>
                <div class="message-sorting-option">Date</div>
                <div class="message-sorting-option">Sender</div>
            </div>
            <div class="message-previews"></div>
        </div>
        <div class="message-wrapper">
            <div class="message-header"></div>
            <div class="message-body"></div>
        </div>
    </div>
</body>
</html>
