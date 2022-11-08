package team6.onandthefarmexhibitionservice.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 아이템 리스트 상세정보
public class ExhibitionAccountItemsDetailResponse {
    private Long exhibitionItemsId;

    private String exhibitionItemsName;

    private String exhibitionItemsDetail;

    private List<ExhibitionAccountItemDetailResponse> exhibitionAccountItemDetailResponseList;
}
