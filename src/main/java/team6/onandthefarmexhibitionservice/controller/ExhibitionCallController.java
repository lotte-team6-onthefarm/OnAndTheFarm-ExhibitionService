package team6.onandthefarmexhibitionservice.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team6.onandthefarmexhibitionservice.service.ExhibitionService;
import team6.onandthefarmexhibitionservice.util.BaseResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAllResponse;

import java.util.List;

@RestController
@RequestMapping("/api/user/exhibition")
@RequiredArgsConstructor
public class ExhibitionCallController {

    private final ExhibitionService exhibitionService;

    @GetMapping(value = "/all")
    @ApiOperation(value = "전시 전부 조회(main view not temp)")
    public ResponseEntity<BaseResponse<ExhibitionAllResponse>> getAllExhibition(){
        List<ExhibitionAllResponse> exhibitions = exhibitionService.getAllExhibition();

        BaseResponse baseResponse = BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Exhibition All")
                .data(exhibitions)
                .build();

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
