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
public class SnsATypeResponse {
	private Long feedId;
	private String memberName;
	private String feedImageSrc;
}

