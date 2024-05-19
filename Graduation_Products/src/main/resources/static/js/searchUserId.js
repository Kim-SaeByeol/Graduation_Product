$(document).ready(function () {
    let userEmailCheck = "N";

    // 이메일 인증번호
    $("#btnemail").on("click", function () {
        getEmailAuthNumber(f)

    })

    // 아이디 찾기
    $('#signupButton').on('click', function (event) {
        event.preventDefault();
        myFunction();  // 원하는 함수 실행
    });

    // 로그인
    $("#btnSearchId").on("click", function () {
        myFunction();  // myFunction 내에서 로그인 로직을 처리
    });


    function myFunction() {

        // 예제 로그인 로직
        let userName = $("#userName").val();
        let email = $("#email").val();
        let authNumber = $("#authNumber").val();

        if (!userName) {
            alert("이름를 입력하세요.");
            $("#userId").focus();
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

        // Ajax 호출해서 로그인하기
        $.ajax({
            url: "/user/searchUserId",
            type: "post",
            dataType: "JSON", // "datatype" 대신 "dataType"으로 올바르게 수정
            data: $("#f").serialize(),
            success: function (json) {
                if (json.res === 1 && userEmailCheck === "Y") {
                    alert(json.msg);
                    location.href = "/user/login";
                } else {
                    alert(json.msg);
                    $("#userId").focus();  // 아이디 입력 항목에 마우스 커서 이동
                }
            }
        });
    }

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

});
