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
    <div class="login-container">
        <div class="login-header">Elephant</div>
        <form method="post" action="/login">
            <div class="buttons-container">
                <div class="form-button-title">email</div>
                <input type="text" name="email" autocomplete="off" class="form-button">
                <div class="form-button-title">Password</div>
                <input type="password" name="password" autocomplete="off" class="form-button">
            </div>
            <button type="submit" onclick="document.getElementById('spinner').style.display = 'block'" class="button button-login">Login</button>
        </form>
        <div class="pass-forget">Forget your password?</div>
        <div class="get-account">Don't have an account?
            <a href="signup.jsp" class="orange-highlight">Get one</a>
        </div>
        <div id="spinner" class="loader" style="display: none;">Loading...</div>
    </div>

</div>
</body>
</html>
