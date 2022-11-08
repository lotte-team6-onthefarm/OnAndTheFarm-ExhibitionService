package team6.onandthefarmexhibitionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionItemPriorityUpdateFormRequestDto {
	private Long exhibitionItemId;
	private Integer exhibitionItemPriority;
}
