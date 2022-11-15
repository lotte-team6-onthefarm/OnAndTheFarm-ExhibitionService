package team6.onandthefarmexhibitionservice.vo;

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
// 아이템 상세정보
public class ExhibitionAccountItemDetailResponse {
    private Long exhibitionItemId;

    private Long exhibitionItemNumber;

    private Long exhibitionItemCategoryId;

    private Integer exhibitionItemPriority;
}