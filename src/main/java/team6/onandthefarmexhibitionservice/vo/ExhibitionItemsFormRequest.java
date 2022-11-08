package team6.onandthefarmexhibitionservice.vo;

import java.util.List;

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
public class ExhibitionItemsFormRequest {

	private Long exhibitionAccountId;

	private String exhibitionItemsName;

	private String exhibitionItemsDetail;

	private List<ExhibitionItemFormRequest> exhibitionItemFormRequests;

}
