package kopo.poly.DTO;

public class CenterDTO {

    private String seq;          // 순번
    private String region;        // 지역
    private String centerName;   // 센터명
    private String address;     // 주소
    private String phone;     // 담당 연락처


    // 페이징 리스트 조회를 위한 DTO 설정
    private int page;             // 현재 페이지 번호
    private int recordSize;       // 페이지당 출력할 데이터 개수
    private int pageSize;         // 화면 하단에 출력할 페이지 사이즈

    public CenterDTO() {
        this.page = 1;
        this.recordSize = 5;
        this.pageSize = 5;
    }

    public int getOffset() {
        return (page - 1) * recordSize;
    }

}