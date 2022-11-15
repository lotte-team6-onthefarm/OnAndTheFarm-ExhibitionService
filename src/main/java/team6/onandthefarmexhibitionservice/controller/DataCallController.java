package team6.onandthefarmexhibitionservice.controller;

import java.security.Principal;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import team6.onandthefarmexhibitionservice.dto.datatool.BadgeDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.BannerDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.ProductDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.SnsDataRequestDto;
import team6.onandthefarmexhibitionservice.service.DataToolService;
import team6.onandthefarmexhibitionservice.util.BaseResponse;
import team6.onandthefarmexhibitionservice.vo.dataPicker.BadgeResponses;
import team6.onandthefarmexhibitionservice.vo.dataPicker.BannerResponses;
import team6.onandthefarmexhibitionservice.vo.dataPicker.ProductResponses;
import team6.onandthefarmexhibitionservice.vo.dataPicker.SnsResponses;

@RestController
@RequestMapping("/api/user/data-call")
@RequiredArgsConstructor
public class DataCallController {
	private final DataToolService dataToolService;

	@GetMapping(value = "/banner")
	@ApiOperation(value = "배너 데이터 호출")
	public ResponseEntity<BaseResponse<BannerResponses>> getBannerItems(
			@RequestParam Map<String, String> request){

		BannerDataRequestDto bannerDataRequestDto = BannerDataRequestDto.builder()
				.ItemsId(Long.parseLong(request.get("ItemsId")))
				.dataToolId(Long.parseLong(request.get("dataToolId")))
				.build();

		BannerResponses bannerResponses = null;

		if (bannerDataRequestDto.getDataToolId().equals(777L)) {
			bannerResponses = dataToolService.getBannerATypeItems(bannerDataRequestDto);
		}

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.OK)
				.message("BannerResponse gotten")
				.data(bannerResponses)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/badge")
	@ApiOperation(value = "뱃지 데이터 호출")
	public ResponseEntity<BaseResponse<BadgeResponses>> getBadgeItems(
			@RequestParam Map<String, String> request){

		BadgeDataRequestDto badgeDataRequestDto = BadgeDataRequestDto.builder()
				.ItemsId(Long.parseLong(request.get("ItemsId")))
				.dataToolId(Long.parseLong(request.get("dataToolId")))
				.build();

		BadgeResponses badgeResponses = null;

		if(badgeDataRequestDto.getDataToolId().equals(888L)){
			badgeResponses = dataToolService.getBadgeATypeItems(badgeDataRequestDto);
		}

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.OK)
				.message("BadgeResponse gotten")
				.data(badgeResponses)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/product")
	@ApiOperation(value = "상품 데이터 호출")
	public ResponseEntity<BaseResponse<ProductResponses>> getProductItems(@ApiIgnore Principal principal,
			@RequestParam Map<String, String> request){

		Long userId = null;
		if (principal != null){
			String[] principalInfo = principal.getName().split(" ");
			if(principalInfo[1].equals("user")){
				userId = Long.parseLong(principalInfo[0]);
			}
		}

		ProductDataRequestDto productDataRequestDto = ProductDataRequestDto.builder()
				.ItemsId(Long.parseLong(request.get("ItemsId")))
				.dataToolId(Long.parseLong(request.get("dataToolId")))
				.build();

		ProductResponses productResponses = null;

		if(productDataRequestDto.getDataToolId().equals(555L)){
			productResponses = dataToolService.getProductATypeItems(productDataRequestDto, userId);
		}
		else if (productDataRequestDto.getDataToolId().equals(556L)){
			productResponses = dataToolService.getProductCTypeItems(productDataRequestDto, userId);
		}
		else if (productDataRequestDto.getDataToolId().equals(557L)){
			productResponses = dataToolService.getProductBTypeItems(productDataRequestDto, userId);
		}

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.OK)
				.message("ProductResponse gotten")
				.data(productResponses)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}
	@GetMapping(value = "/sns")
	@ApiOperation(value = "sns 데이터 호출")
	public ResponseEntity<BaseResponse<SnsResponses>> getSnsItems(@ApiIgnore Principal principal,
			@RequestParam Map<String, String> request){

		Long userId = null;
		if (principal != null){
			String[] principalInfo = principal.getName().split(" ");
			if(principalInfo[1].equals("user")){
				userId = Long.parseLong(principalInfo[0]);
			}
		}

		SnsDataRequestDto snsDataRequestDto = SnsDataRequestDto.builder()
				.dataToolId(Long.parseLong(request.get("dataToolId")))
				.ItemsId(Long.parseLong(request.get("ItemsId")))
				.build();

		SnsResponses snsResponses = null;

		if(snsDataRequestDto.getDataToolId().equals(666L)){
			snsResponses = dataToolService.getSnsATypeItems(snsDataRequestDto);
		}

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.OK)
				.message("SnsReponses gotten")
				.data(snsResponses)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}
}
