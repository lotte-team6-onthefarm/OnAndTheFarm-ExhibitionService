package team6.onandthefarmexhibitionservice.vo.datatool;

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
public class BadgeATypeResponse {
	private String ImgSrc;
	private String connectUrl;
	private String badgeName;
	private Integer priority;
}
