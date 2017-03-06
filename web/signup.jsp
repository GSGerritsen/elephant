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
        <form method="post" action="/signup">
            <div class="buttons-container">
                <div class="form-button-title">Email</div>
                <input type="email" name="email" autocomplete="off" class="form-button">
                <div class="form-button-title">First name</div>
                <input type="text" name="firstName" autocomplete="off" class="form-button">
                <div class="form-button-title">Last name</div>
                <input type="text" name="lastName" autocomplete="off" class="form-button">
                <div class="form-button-title">Password</div>
                <input type="password" name="password" autocomplete="off" class="form-button">
                <div class="form-button-title">Confirm Password</div>
                <input type="password" name="confPassword" autocomplete="off" class="form-button">
            </div>
            <button type="submit" onclick="document.getElementById('spinner').style.display = 'block'" class="button button-login">Sign up</button>
        </form>
        <div class="get-account">Already have an account?
            <span class="orange-highlight">Login here</span>
        </div>
        <div id="spinner" class="loader" style="display: none;">Loading...</div>
    </div>
</div>
</body>
</html>
