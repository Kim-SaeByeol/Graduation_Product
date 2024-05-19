
$(document).ready(function () {
    let userIdCheck = "Y";
    let userEmailCheck = "Y";
    let userNickCheck = "Y";
    let emailAuthNumber = "";

    let f = document.getElementById("f");
    // 아이디 중복체크
    $("#btnUserId").on("click", function () {
        userIdExists(f)

    })

    // 이메일 인증번호
    $("#btnemail").on("click", function () {
        emailExists(f)

    })

    $("#btnnick").on("click", function () {
        UserNickExists(f)

    })

    // 회원가입 버튼
    $("#btnSend").on("click", function () {
        doSubmit(f)

    })

})

function userIdExists(f) {

    if (f.userId.value === ""){
        alert("아이디를 입력하세요.");
        f.userId.focus();
        return;
    }

    $.ajax({
            url: "/user/getUserIdExists",
            type: "post",
            dataType: "JSON",
            data: $("#f").serialize(),
            success: function (json) {
                if(json.existsIdYn === "Y") {
                    alert("이미 가입된 아이디가 존재합니다.");
                    f.userId.focus();
                } else {
                    alert("가입 가능한 아이디입니다.");
                    userIdCheck = "N";
                }
            }
        }
    )
}

function emailExists(f) {

    if (f.email.value === ""){
        alert("이메일을 입력하세요.");
        f.email.focus();
        return;
    }

    $.ajax({
            url: "/user/getUserEmailExists",
            type: "post",
            dataType: "JSON",
            data: $("#f").serialize(),
            success: function (json) {
                if(json.existsEmailYn === "Y") {
                    alert("이미 가입된 이메일 주소가 존재합니다.");
                    f.email.focus();
                } else {
                    alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.");
                    emailAuthNumber = json.authNumber;
                    userEmailCheck = "N";
                }
            }
        }
    )
}

function UserNickExists(f) {

    if (f.nick.value === ""){
        alert("별명을 입력하세요.");
        f.nick.focus();
        return;
    }

    $.ajax({
            url: "/user/getUserNickExists",
            type: "post",
            dataType: "JSON",
            data: $("#f").serialize(),
            success: function (json) {
                if(json.existsNickYn === "Y") {
                    alert("이미 해당 별명이 존재합니다.");
                    f.nick.focus();
                } else {
                    alert("사용 가능한 별명입니다.");
                    userNickCheck = "N";
                }
            }
        }
    )
}

//회원가입 정보의 유효성 체크하기
function doSubmit(f) {


    if (f.userId.value === "") {
        alert("아이디를 입력하세요.");
        f.userId.focus();
        return;
    }

    if (userIdCheck != "N") {
        alert("아이디 중복 체크 및 중복되지 않은 아이디로 가입 바랍니다.");
        f.userId.focus();
        return;
    }

    if (userEmailCheck != "N"){
        alert("이메일 중복 체크 및 중복되지 않은 이메일로 가입 바랍니다.");
        f.email.focus();
        return;
    }


    if (userNickCheck != "N") {
        alert("별명 중복 체크 및 중복되지 않은 별명으로 가입 바랍니다.");
        f.nick.focus();
        return;
    }

    if (f.name.value === "") {
        alert("이름을 입력하세요.");
        f.name.focus();
        return;
    }

    if (f.pwd.value === "") {
        alert("비밀번호를 입력하세요.");
        f.pwd.focus();
        return;
    }

    if (f.pwdCheck.value === "") {
        alert("비밀번호확인을 입력하세요.");
        f.pwdCheck.focus();
        return;
    }

    if (f.pwd.value != f.pwdCheck.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        f.pwd.focus();
        return;
    }

    if (f.email.value === "") {
        alert("이메일을 입력하세요.");
        f.email.focus();
        return;
    }

    if (f.authNumber.value === "") {
        alert("이메일 인증번호를 입력하세요.");
        f.authNumber.focus();
        return;
    }

    if (f.authNumber.value != emailAuthNumber) {
        alert("이메일 인증번호가 일치하지 않습니다.");
        f.authNumber.focus();
        return;
    }
    if (f.nick.value === "") {
        alert("별명을 입력하세요.");
        f.nick.focus();
        return;
    }

    // Ajax 호출해서 회원가입하기
    $.ajax({
        url: "/user/insertUserInfo",
        type: "post",
        dataType: "JSON", // "datatype" 대신 "dataType"으로 올바르게 수정
        data: $("#f").serialize(),
        success: function (json) {
            if (json.res === 1) {
                alert(json.msg);
                location.href = "/user/login";
            } else {
                alert(json.msg);
                location.href = "/user/userRegForm";
            }
        }
    });


}