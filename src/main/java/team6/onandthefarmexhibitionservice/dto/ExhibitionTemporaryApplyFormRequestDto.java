package team6.onandthefarmexhibitionservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExhibitionTemporaryApplyFormRequestDto {
	private List<Long> exhibitionTemporaryIds;
}
