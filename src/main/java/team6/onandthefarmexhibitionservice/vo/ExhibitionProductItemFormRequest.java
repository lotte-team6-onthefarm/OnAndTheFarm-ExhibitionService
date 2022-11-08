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
public class ExhibitionProductItemFormRequest {
	private Long exhibitionAccountId;

	private Long exhibitionCategoryId;

	private Long exhibitionProductItemProductId;

	// private String exhibitionProductItemName;

	private Integer exhibitionProductItemPriority;

	// private String exhibitionProductItemUrl;

	private String exhibitionProductItemTime;
}
