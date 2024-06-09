$(document).ready(function () {
    // 로그인
    $("#btnLogin").on("click", function () {
        myFunction();  // myFunction 내에서 로그인 로직을 처리
    });

    function myFunction() {
        let userId = $("#userId").val();
        let password = $("#password").val();

        if (!userId) {
            alert("아이디를 입력하세요.");
            $("#userId").focus();
            return;
        }

        if (!password) {
            alert("비밀번호를 입력하세요.");
            $("#password").focus();
            return;
        }

        $.ajax({
            url: "/user/loginProc",
            type: "post",
            dataType: "json",
            data: {
                userId: $("#userId").val(),
                password: $("#password").val()
            },
            success: function (json) {
                if (json.res  === 1) {
                    alert(json.msg);
                    location.href = "/index/index";
                } else {
                    alert(json.msg);
                    $("#userId").focus();
                }
            },
            error: function() {
                alert("로그인 처리 중 오류가 발생했습니다.");
            }
        });
    }
});
