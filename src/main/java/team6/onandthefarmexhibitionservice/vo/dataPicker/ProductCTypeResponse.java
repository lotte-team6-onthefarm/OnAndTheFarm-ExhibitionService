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
public class ProductCTypeResponse {
	private Long productId;
	private String ImgSrc;
	private String sellerName;
	private String productName;
	private Integer productPrice;
	private Integer soldCount;
}
