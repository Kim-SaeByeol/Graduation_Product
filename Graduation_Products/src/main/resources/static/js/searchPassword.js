$(document).ready(function () {
    let userEmailCheck = "N";

    // 이메일 인증번호
    $("#btnemail").on("click", function () {
        getEmailAuthNumber(f)

    })

    // 로그인
    $("#btnSearchPassword").on("click", function () {
        myFunction();  // myFunction 내에서 로그인 로직을 처리
    });

    // 인증번호
    function getEmailAuthNumber(f) {

        if (f.email.value === ""){
            alert("이메일을 입력하세요.");
            f.email.focus();
            return;
        }

        $.ajax({
                url: "/user/getEmailAuthNumber",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {
                    if(json.existsEmailYn === "Y") {
                        alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.");
                        emailAuthNumber = json.authNumber;
                        userEmailCheck = "Y";
                    } else {
                        alert("등록된 이메일이 아닙니다.");
                        f.email.focus();
                    }
                }
            }
        )
    }

    function myFunction() {

        let userId = $("#userId").val();
        let userName = $("#userName").val();
        let email = $("#email").val();
        let authNumber = $("#authNumber").val();

        if (!userId) {
            alert("아이디를 입력하세요.");
            $("#userId").focus();
            return;
        }

        if (!userName) {
            alert("이름를 입력하세요.");
            $("#userName").focus();
            return;
        }

        if (!email) {
            alert("이메일를 입력하세요.");
            $("#email").focus();
            return;
        }
        if (!authNumber) {
            alert("이메일 인증번호를 입력하세요.");
            $("#authNumber").focus();
            return;
        }

        $.ajax({
            url: "/user/searchPasswordProc",
            type: "post",
            dataType: "json",
            data: $("#f").serialize(),
            success: function (json) {
                if (json !== null && json !== undefined) {
                    if (json.msg === "success") {
                        alert("사용자를 찾았습니다. 비밀번호를 재설정하세요.");
                        // 비밀번호 재설정 페이지로 이동하면서 섹션 아이디를 설정
                        window.location.href = "/user/newPassword?userId=" + $("#userId").val();
                    } else if (json.msg === "fail") {
                        alert("비밀번호를 찾을 수 없습니다. 입력 정보를 다시 확인해주세요.");
                    } else {
                        alert("알 수 없는 오류가 발생했습니다.");
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