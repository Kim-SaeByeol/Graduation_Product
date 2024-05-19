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

    function myFunction() {

        // 예제 로그인 로직
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

        // Ajax 호출해서 로그인하기
        $.ajax({
            url: "/user/loginProc",
            type: "post",
            dataType: "JSON", // "datatype" 대신 "dataType"으로 올바르게 수정
            data: $("#f").serialize(),
            success: function (json) {
                if (json.res === 1) {
                    alert(json.msg);
                    location.href = "/index/index";
                } else {
                    alert(json.msg);
                    $("#userId").focus();  // 아이디 입력 항목에 마우스 커서 이동
                }
            }
        });

    }
});
