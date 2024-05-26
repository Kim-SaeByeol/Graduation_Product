$(document).ready(function () {
    // 회원가입
    $('#signupButton').on('click', function (event) {
        event.preventDefault();
        myFunction();  // 원하는 함수 실행
    });

    // 로그인
    $("#btnLogin").on("click", function () {
        myFunction();  // myFunction 내에서 로그인 로직을 처리
    });

    // 구글 로그인
    $('#googlelogin').on('click', function() {
        googleLogin();
    });

    function myFunction() {
        let userId = $("#userId").val();
        let password = $("#pwd").val();

        if (!userId) {
            alert("아이디를 입력하세요.");
            $("#userId").focus();
            return;
        }

        if (!password) {
            alert("비밀번호를 입력하세요.");
            $("#pwd").focus();
            return;
        }

        $.ajax({
            url: "/user/loginProc",
            type: "post",
            dataType: "json",
            data: $("#f").serialize(),
            success: function (json) {
                if (json.res === 1) {
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
    function googleLogin() {
        $('#google-login-btn').on('click', function () {
            $.ajax({
                url: '/google/authorization', // Google 로그인을 시작하는 URL
                type: 'GET',
                success: function (response) {
                    alert(response.msg);
                    if (response.res === 1) {
                        window.location.href = '/index/index'; // 성공 시 페이지 리디렉션
                    }
                },
                error: function () {
                    alert("로그인 과정 중 문제가 발생했습니다.");
                }
            });
        });
    }
});
