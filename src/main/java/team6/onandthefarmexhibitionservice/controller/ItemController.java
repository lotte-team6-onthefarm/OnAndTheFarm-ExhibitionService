package team6.onandthefarmexhibitionservice.controller;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import team6.onandthefarmexhibitionservice.dto.item.BadgeFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.item.BannerFormRequestDto;
import team6.onandthefarmexhibitionservice.entity.item.Badge;
import team6.onandthefarmexhibitionservice.entity.item.Banner;
import team6.onandthefarmexhibitionservice.service.ExhibitionItemService;
import team6.onandthefarmexhibitionservice.util.BaseResponse;
import team6.onandthefarmexhibitionservice.vo.item.BadgeFormRequest;
import team6.onandthefarmexhibitionservice.vo.item.BannerFormRequest;

@RestController
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class ItemController {

	private final ExhibitionItemService exhibitionItemService;

	@PostMapping(value = "/new/banner")
	@ApiOperation(value = "배너 등록")
	public ResponseEntity<BaseResponse<Banner>> createBanner(
			@ApiIgnore Principal principal,
			@RequestPart(value = "images", required = false) List<MultipartFile> photo,
			@RequestPart(value = "data", required = false) BannerFormRequest bannerFormRequest) throws Exception{

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		BannerFormRequestDto bannerFormRequestDto = modelMapper.map(bannerFormRequest, BannerFormRequestDto.class);

		bannerFormRequestDto.setBannerImg(photo);

		Long bannerId = exhibitionItemService.createBanner(bannerFormRequestDto);

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.CREATED)
				.message("Banner CREATED")
				.data(bannerId)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.CREATED);
	}

	@PostMapping(value = "/new/badge")
	@ApiOperation(value = "뱃지 등록")
	public ResponseEntity<BaseResponse<Badge>> createBadge(
			@ApiIgnore Principal principal,
			@RequestPart(value = "images", required = false) List<MultipartFile> photo,
			@RequestPart(value = "data", required = false) BadgeFormRequest badgeFormRequest) throws Exception{

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		BadgeFormRequestDto badgeFormRequestDto = modelMapper.map(badgeFormRequest, BadgeFormRequestDto.class);

		badgeFormRequestDto.setBadgeImg(photo);

		Long badgeId = exhibitionItemService.createBadge(badgeFormRequestDto);

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.CREATED)
				.message("Badge CREATED")
				.data(badgeId)
				.build();

		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
}
