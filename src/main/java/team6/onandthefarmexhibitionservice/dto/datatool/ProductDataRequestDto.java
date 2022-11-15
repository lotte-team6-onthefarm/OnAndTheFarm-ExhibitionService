package team6.onandthefarmexhibitionservice.dto.datatool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDataRequestDto {
	private Long dataToolId;
	private Long itemsId;
}
