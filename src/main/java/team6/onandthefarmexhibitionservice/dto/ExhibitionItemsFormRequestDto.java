package team6.onandthefarmexhibitionservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionItemsFormRequestDto {
	private Long exhibitionItemsId;

	private Long exhibitionAccountId;

	private String exhibitionItemsName;

	private String exhibitionItemsDetail;

	private List<ExhibitionItemFormRequestDto> exhibitionItemFormRequestDtos;
}
