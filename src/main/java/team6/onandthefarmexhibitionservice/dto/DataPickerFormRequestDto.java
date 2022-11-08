package team6.onandthefarmexhibitionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataPickerFormRequestDto {
	private Long dataPickerId;

	private Long exhibitionCategoryId;

	private String dataPickerName;

	private String dataPickerDetail;

	private boolean dataPickerStatus;

	private String dataPickerCreatedAt;

	private String dataPickerModifiedAt;

	private String dataPickerWriter;
}
