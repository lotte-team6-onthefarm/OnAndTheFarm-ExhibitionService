package team6.onandthefarmexhibitionservice.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import team6.onandthefarmexhibitionservice.dto.DataPickerFormRequestDto;
import team6.onandthefarmexhibitionservice.entity.DataPicker;
import team6.onandthefarmexhibitionservice.service.ExhibitionService;
import team6.onandthefarmexhibitionservice.util.BaseResponse;
import team6.onandthefarmexhibitionservice.vo.DataPickerFormRequest;

@RestController
@RequestMapping("/api/admin/data-picker")
@RequiredArgsConstructor
public class DataPickerController {

	private final ExhibitionService exhibitionService;

	@PostMapping(value="/new")
	@ApiOperation(value="데이터 피커 등록")
	public ResponseEntity<BaseResponse<DataPicker>> createDataPicker(@ApiIgnore Principal principal,
			@RequestBody DataPickerFormRequest dataPickerFormRequest){

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		DataPickerFormRequestDto dataPickerFormRequestDto = modelMapper.map(dataPickerFormRequest, DataPickerFormRequestDto.class);

		Long dataPickerId = exhibitionService.createDataPicker(dataPickerFormRequestDto);

		BaseResponse baseResponse = BaseResponse.builder()
				.httpStatus(HttpStatus.CREATED)
				.message("DataPicker CREATED")
				.data(dataPickerId)
				.build();

		return new ResponseEntity(baseResponse, HttpStatus.CREATED);
	}
}
