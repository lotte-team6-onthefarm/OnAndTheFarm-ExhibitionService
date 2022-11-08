package team6.onandthefarmexhibitionservice.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import team6.onandthefarmexhibitionservice.entity.ExhibitionAccount;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionSelectionResponse {
		private Long exhibitionAccountId;
		private Long exhibitionCategoryId;
		private String exhibitionCategoryName;
		private String exhibitionAccountName;
		private String exhibitionAccountStartTime;
		private String exhibitionAccountEndTime;

	public ExhibitionSelectionResponse(ExhibitionAccount exhibitionAccount) {
		this.exhibitionAccountId = exhibitionAccount.getExhibitionAccountId();
		this.exhibitionCategoryId = exhibitionAccount.getExhibitionCategory().getExhibitionCategoryId();
		this.exhibitionCategoryName = exhibitionAccount.getExhibitionCategory().getExhibitionCategoryName();
		this.exhibitionAccountName = exhibitionAccount.getExhibitionAccountName();
		this.exhibitionAccountStartTime = exhibitionAccount.getExhibitionAccountStartTime();
		this.exhibitionAccountEndTime = exhibitionAccount.getExhibitionAccountEndTime();
	}
}
