$(document).ready(function () {
    // 이메일 인증번호 요청
    $("#btnEmail").on("click", function () {
        var email = $("#email").val();
        var domain = $("#emailDomain").val();
        getEmailAuthNumber(email, domain);
    });

    // 아이디 찾기 요청
    $('#signupButton').on('click', function (event) {
        event.preventDefault();
        var userName = $("#name").val();
        var email = $("#email").val();
        var domain = $("#emailDomain").val();
        var authNumber = $("#authNumber").val();
        myFunction(userName, email, domain, authNumber);
    });

    function getEmailAuthNumber(email, domain) {
        if (!email) {
            alert("이메일을 입력하세요.");
            $("#email").focus();
            return;
        }

        if (!domain) {
            alert("도메인을 입력하세요.");
            $("#domain").focus();
            return;
        }

        let fullEmail = email + '@' + domain;
        $.ajax({
            url: "/user/getEmailAuthNumber",
            type: "post",
            dataType: "JSON",
            data: { email: fullEmail },
            success: function (json) {
                if (json.existsEmailYn === "Y") {
                    alert("이메일로 인증번호가 발송되었습니다.");
                } else {
                    alert("등록된 이메일이 아닙니다.");
                    $("#email").focus();
                }
            }
        });
    }

    function myFunction(userName, email, domain, authNumber) {
        let fullEmail = email + '@' + domain;

        if (!userName) {
            alert("이름을 입력하세요.");
            $("#name").focus();
            return;
        }

        if (!email || !domain) {
            alert("이메일 정보를 완전히 입력하세요.");
            $("#email").focus();
            return;
        }

        if (!authNumber) {
            alert("이메일 인증번호를 입력하세요.");
            $("#authNumber").focus();
            return;
        }

        $.ajax({
            url: "/user/searchUserId",
            type: "post",
            dataType: "JSON",
            data: { email: fullEmail, name: userName },
            success: function (json) {
                if (json) {
                    alert("사용자의 아이디는 [" + json.userId + "] 입니다.");
                    location.href = "/user/login";
                } else {
                    alert("아이디 찾기에 실패하셨습니다.");
                    $("#name").focus();
                }
            }
        });
    }
});
