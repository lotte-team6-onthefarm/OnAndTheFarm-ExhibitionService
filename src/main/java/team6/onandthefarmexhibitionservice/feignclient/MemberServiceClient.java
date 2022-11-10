package team6.onandthefarmexhibitionservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.onandthefarmexhibitionservice.feignclient.vo.SellerClientSellerDetailResponse;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
	@GetMapping("/api/feign/seller/members/member-service/seller-detail/{seller-no}")
	public SellerClientSellerDetailResponse findBySellerId(@PathVariable("seller-no") Long sellerId);
}
