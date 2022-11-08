package team6.onandthefarmexhibitionservice.vo;

import java.util.List;

import com.team6.onandthefarm.vo.PageVo;

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
public class ExhibitionSelectionResponseResult {
	private List<ExhibitionSelectionResponse> exhibitionSelectionResponses;
	private PageVo pageVo;

}
