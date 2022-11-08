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
public class DataPickerFormRequest {

	private Long exhibitionCategoryId;

	private String dataPickerName;

	private String dataPickerDetail;

	private String dataPickerWriter;
}
