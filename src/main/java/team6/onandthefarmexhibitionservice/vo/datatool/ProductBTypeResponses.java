package team6.onandthefarmexhibitionservice.vo.datatool;

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
public class ProductBTypeResponses implements ProductResponses {
	private List<ProductBTypeResponse> bTypeResponses;
}
