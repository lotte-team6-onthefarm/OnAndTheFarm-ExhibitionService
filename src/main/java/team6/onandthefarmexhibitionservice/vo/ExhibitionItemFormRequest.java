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
public class ExhibitionItemFormRequest {
	private Long exhibitionItemsId;

	private Long exhibitionAccountId;

	private Long exhibitionCategoryId;

	private Long exhibitionItemId;

	private Long exhibitionItemNumber;

	private Integer exhibitionItemPriority;

	private String exhibitionItemStartTime;

	private String exhibitionItemEndTime;
}
