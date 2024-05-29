
var searchType = 'itemName';  // 검색 유형을 전역으로 관리
var searchText = '';  // 검색어를 전역 변수로 선언
$(document).ready(function () {

    $('.selectset-link').click(function() {
        searchType = $(this).data('value');
        $('#searchType').text($(this).find('span').text()); // 선택된 검색 유형을 버튼에 표시
    });

    // 검색 버튼 클릭 이벤트 핸들러
    $('#fn_btn_search').click(function (e) {
        searchText = $('#searchInput').val();
        alert("버튼이 클릭됨" + " searchType : " + searchType + " searchText : " + searchText);

        // Ajax를 통해 페이지 1을 로드
        loadPage(1);
    });
});

// 페이지 로딩 함수
function loadPage(page) {

    alert("페이지 로드가 시작됨" + "page : " + page + "  searchType : "+ searchType + "  searchText : " + searchText);

    if (searchType.trim() === '') {
        searchType = "itemName";
    }

    if (searchText.trim() === '') {
        searchText = "";
    }
    $.ajax({
        type: 'POST',
        url: '/medicine/Search',
        data: { searchType: searchType, searchText: searchText, page: page },
        success: function (response) {
            // 성공 시 결과를 처리하는 함수 호출
            handleSearchResults(response);
        },
        error: function (error) {
            console.error(error);
        }
    });
}

// Ajax 요청 후 성공 시 호출되는 함수
function handleSearchResults(response) {
    alert("handleSearchResults 실행됨")
    var resultsContainer = $('.contents-group');
    var paginationContainer = $('.pagination');

    resultsContainer.empty();
    paginationContainer.empty();

    for (var i = 0; i < response.searchResults.length; i++) {
        var dto = response.searchResults[i];

        // 메인 링크 요소 생성 및 속성 설정
        var listItem = $('<a>').addClass('cardset cardset-inner cardset-hover cardset-border');
        listItem.attr('href', '/notice/noticeInfo?nSeq=' + dto.noticeSeq);  // 링크 설정

        // 내부 컨테이너 div 생성
        var contentDiv = $('<div>').addClass('cardset-cont');
        listItem.append(contentDiv);

        // 제목 h2 요소 생성 및 텍스트 설정
        var titleH2 = $('<h2>').addClass('cardset-tit').text(dto.title);
        contentDiv.append(titleH2);

        // 내용 p 요소 생성. 길이 조건에 따라 내용을 자르고 '...' 추가
        var descText = dto.contents.length > 45 ? dto.contents.substring(0, 45) + '...' : dto.contents;
        var contentP = $('<p>').addClass('cardset-desc').text(descText);
        contentDiv.append(contentP);

        // 날짜 표시 span 요소 생성 및 텍스트 설정
        var regDtSpan = $('<span>').addClass('cardset-txt').text(dto.regDt);
        contentDiv.append(regDtSpan);

        // 줄바꿈 요소 추가
        contentDiv.append($('<br>'));

        // 사용자 ID 표시 span 요소 생성 및 텍스트 설정
        var userIdSpan = $('<span>').addClass('cardset-txt').text(dto.userId);
        contentDiv.append(userIdSpan);

        // 최종적으로 생성된 listItem을 DOM에 추가하기 (예: 결과를 보여줄 컨테이너에 추가)
        $('#resultsContainer').append(listItem);
    }

    for (var i = 1; i <= response.totalPages; i++) {
        var pageClass = 'pagiset-link' + (i === response.currentPage ? ' active-fill' : '');
        var pageItem = $('<a>', {
            class: pageClass,
            href: '#',
            text: i,
            click: function (e) {
                e.preventDefault();  // 이벤트의 기본 동작을 막습니다.
                var currentPage = parseInt($(this).text());  // 클릭된 페이지 번호를 읽습니다.
                loadPage(currentPage);  // loadPage 함수를 호출합니다.
            }
        });
        paginationContainer.append(pageItem);
    }
}