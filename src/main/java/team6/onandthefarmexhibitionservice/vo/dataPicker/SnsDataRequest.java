package team6.onandthefarmexhibitionservice.vo.dataPicker;

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
public class SnsDataRequest {
	private Long dataToolId;
	private Long ItemsId;
}
