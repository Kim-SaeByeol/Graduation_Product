$(document).ready(function () {

    $("#btnSearchPassword").on("click", function () {
        myFunction();  // myFunction 내에서 로그인 로직을 처리
    });

    function myFunction() {

        // 예제 로그인 로직
        let password = $("#password").val();
        let password2 = $("#password2").val();

        if (!password) {
            alert("새 비밀번호를 입력하세요.");
            $("#password").focus();
            return;
        }

        if (!password2) {
            alert("새 비밀번호를 재입력하세요.");
            $("#password2").focus();
            return;
        }

        // 비밀번호가 일치하는지 확인
        if (password !== password2) {
            alert("입력한 비밀번호가 일치하지 않습니다.");
            return;
        }

        // Ajax 호출해서 로그인하기
        $.ajax({
            url: "/user/newPassword",
            type: "POST",  // 반드시 'POST'로 설정
            dataType: "json",
            data: {
                password: $("#password").val(),
                password2: $("#password2").val()
            },
            success: function (json) {
                if (json !== null && json !== undefined) {
                    if (json.msg === "success") {
                        alert("비밀번호가 재설정되었습니다.");
                        window.location.href = "/user/login"; // 로그인 페이지로 이동
                    } else if(json.msg === "false"){
                        alert("서버 오류: 비밀번호를 재설정할 수 없습니다.");
                    }
                } else {
                    alert("서버로부터 응답을 받지 못했습니다.");
                }
            },
            error: function () {
                alert("서버 요청 중 오류가 발생했습니다.");
            }
        });
    }
});