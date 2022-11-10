package team6.onandthefarmexhibitionservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.onandthefarmexhibitionservice.feignclient.vo.FeedImgVo;
import team6.onandthefarmexhibitionservice.feignclient.vo.FeedInfoVo;

@FeignClient(name = "sns-service")
public interface SnsServiceClient {
	@GetMapping("/api/feign/user/feed/feed-service/feed/{feed-no}")
	public FeedInfoVo findFeedInfoVoByFeedId(@PathVariable("feed-no") Long feedId);

	@GetMapping("/api/feign/user/feed/feed-service/feedImgVo/{feed-no}")
	public FeedImgVo findFeedImgVoByFeedId(@PathVariable("feed-no") Long feedId);
}
