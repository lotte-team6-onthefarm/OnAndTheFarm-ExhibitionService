package team6.onandthefarmexhibitionservice.feignclient;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.onandthefarmexhibitionservice.feignclient.vo.SellerClientSellerDetailResponse;
import team6.onandthefarmexhibitionservice.feignclient.vo.UserClientUserShortInfoResponse;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
	@GetMapping("/api/feign/user/members/member-service/seller-detail/{seller-no}")
	public SellerClientSellerDetailResponse findBySellerIdFromExhibition(@PathVariable("seller-no") Long sellerId);

	@GetMapping("/api/feign/user/members/member-service/username/{user-no}")
	public UserClientUserShortInfoResponse findUserNameByUserId(@PathVariable("user-no") Long userId);
}
