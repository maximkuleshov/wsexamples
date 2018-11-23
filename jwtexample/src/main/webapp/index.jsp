<html>
<head>
<script
  src="https://code.jquery.com/jquery-2.2.4.js"
  integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
  crossorigin="anonymous"></script>

  <script type="text/javascript">
    $(document).ready(function() {
        $("#getToken").click(function () {
            $.ajax({
                url: 'auth?username=' + $("#username").val(),
                success: function (data) {
                    $("#token").val(data.token);
                },
                error: function(xhr, textStatus, error) {
                    alert('Got error code: ' + xhr.status);
                }
            });
        });

        $("#getData").click(function() {
            $.ajax({
                type: 'GET',
                url: 'protected',
                headers: {'JWT': $("#token").val() },
                success: function(data) {
                    $("#response").html(data.text);
                },
                error: function(xhr, textStatus, error) {
                    alert('Got error code: ' + xhr.status);
                }
            });
        });
    });
  </script>
</head>
<body>
<h2>Hello World!</h2>

<h3>Token endpoint</h3>

<p><textarea id="token" rows="5" cols="80">Here will be jwt token</textarea></p>

Username: <input type="text" id="username"/>&nbsp;<button id="getToken">Request token</button>

<h3>Protected endpoint</h3>

<p>Response data from server: <span id="response"></span></p>

<button id="getData">Request protected data</button>

</body>
</html>
