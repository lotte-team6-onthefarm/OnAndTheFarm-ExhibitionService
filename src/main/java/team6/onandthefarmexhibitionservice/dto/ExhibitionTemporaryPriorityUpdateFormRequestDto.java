package team6.onandthefarmexhibitionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionTemporaryPriorityUpdateFormRequestDto {
	private Long exhibitionTemporaryId;
	private Integer exhibitionTemporaryPriority;
}
