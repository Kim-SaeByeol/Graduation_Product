
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
    var resultsContainer = $('.medicineTableBody');
    var paginationContainer = $('.pagination');

    resultsContainer.empty();
    paginationContainer.empty();

    for (var i = 0; i < response.searchResults.length; i++) {
        var dto = response.searchResults[i];

        var tableRow = $('<tr>');
        tableRow.append('<td class="th-num">' + (i + 1) + '</td>');
        tableRow.append('<td class="th-date">' + dto.itemName + '</td>');
        tableRow.append('<td class="th-date">' + dto.entpName + '</td>');
        tableRow.append('<td class="th-date">' + dto.itemSeq + '</td>');

        resultsContainer.append(tableRow);
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