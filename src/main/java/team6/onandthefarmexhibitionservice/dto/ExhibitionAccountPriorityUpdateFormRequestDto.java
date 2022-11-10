package team6.onandthefarmexhibitionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionAccountPriorityUpdateFormRequestDto {
	private Long exhibitionAccountId;
	private Integer exhibitionAccountPriority;
}
