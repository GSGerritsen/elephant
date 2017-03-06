<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript">
        var isLoggedIn = "<%= session.getAttribute("isLoggedIn")%>";
        if(isLoggedIn === "true") {
            window.location.href = "home.jsp";
        }
    </script>
</head>
<body class="login">
<link rel="stylesheet" href="/css/styles.css">
<div class="login-wrapper">
   <a href="/login.jsp" class="login-choices">Login</a>
    <span class="login-divider">||</span>
    <a href="/signup.jsp" class="login-choices">Sign up</a>
</div>
</body>
</html>
