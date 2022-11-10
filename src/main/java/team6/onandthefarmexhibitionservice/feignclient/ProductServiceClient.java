package team6.onandthefarmexhibitionservice.feignclient;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.onandthefarmexhibitionservice.feignclient.vo.ProductVo;
import team6.onandthefarmexhibitionservice.feignclient.vo.ReviewInfoToExbt;
import team6.onandthefarmexhibitionservice.feignclient.vo.WishVo;

@FeignClient(name = "product-service", contextId = "product-service-user")
public interface ProductServiceClient {
	@GetMapping("/api/feign/user/product/product-service/product/{product-no}")
	public ProductVo findProductByProductId(@PathVariable("product-no") Long productId);

	@GetMapping("/api/feign/user/product/product-service/reviews-info-response/{product-no}")
	public ReviewInfoToExbt getReviewsInfoProductId(@PathVariable("product-no") Long productId);

	@GetMapping("/api/feign/user/product/product-service/wish")
	public boolean getWishByProductUserId(Long productId, Long userId);

	@GetMapping("/api/feign/user/product/product-service/cart")
	public boolean getCartByProductUserId(Long productId, Long userId)
}
