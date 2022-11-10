package team6.onandthefarmexhibitionservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionItemPriorityUpdateFormsRequestDto {
	private List<ExhibitionItemPriorityUpdateFormRequestDto> exhibitionItemPriorityUpdateFormRequestDtos;
}
