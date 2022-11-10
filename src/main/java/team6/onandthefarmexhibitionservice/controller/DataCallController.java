package team6.onandthefarmexhibitionservice.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import team6.onandthefarmexhibitionservice.dto.datatool.BadgeDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.BannerDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.ProductDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.SnsDataRequestDto;
import team6.onandthefarmexhibitionservice.service.DataToolService;
import team6.onandthefarmexhibitionservice.util.BaseResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.BadgeDataRequest;
import team6.onandthefarmexhibitionservice.vo.datatool.BadgeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.BannerDataRequest;
import team6.onandthefarmexhibitionservice.vo.datatool.BannerResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductDataRequest;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.SnsDataRequest;
import team6.onandthefarmexhibitionservice.vo.datatool.SnsResponses;

@RestController
@RequestMapping("/api/user/data-call")
@RequiredArgsConstructor
public class DataCallController {
	private final DataToolService dataToolService;

	@GetMapping(value = "/banner")
	@ApiOperation(value = "배너 데이터 호출")
	public ResponseEntity<BaseResponse<BannerResponses>> getBannerItems(
			@RequestBody BannerDataRequest bannerDataRequest){
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		BannerDataRequestDto bannerDataRequestDto = modelMapper.map(bannerDataRequest, BannerDataRequestDto.class);

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
			@RequestBody BadgeDataRequest badgeDataRequest){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		BadgeDataRequestDto badgeDataRequestDto = modelMapper.map(badgeDataRequest, BadgeDataRequestDto.class);

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
			@RequestBody ProductDataRequest productDataRequest){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Long userId = null;
		if (principal != null){
			String[] principalInfo = principal.getName().split(" ");
			if(principalInfo[1].equals("user")){
				userId = Long.parseLong(principalInfo[0]);
			}
		}

		ProductDataRequestDto productDataRequestDto = modelMapper.map(productDataRequest, ProductDataRequestDto.class);

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
			@RequestBody SnsDataRequest snsDataRequest){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Long userId = null;
		if (principal != null){
			String[] principalInfo = principal.getName().split(" ");
			if(principalInfo[1].equals("user")){
				userId = Long.parseLong(principalInfo[0]);
			}
		}

		SnsDataRequestDto snsDataRequestDto = modelMapper.map(snsDataRequest, SnsDataRequestDto.class);

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
