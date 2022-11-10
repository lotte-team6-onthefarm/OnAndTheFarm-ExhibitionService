package team6.onandthefarmexhibitionservice.controller;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team6.onandthefarm.dto.exhibition.ModuleFormDto;
import com.team6.onandthefarm.service.exhibition.ModuleService;
import com.team6.onandthefarm.util.BaseResponse;
import com.team6.onandthefarm.vo.exhibition.ModuleFormRequest;
import com.team6.onandthefarm.vo.exhibition.ModuleSelectionResponse;
import com.team6.onandthefarm.vo.exhibition.ModuleSelectionResponseResult;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/admin/module")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService){
        this.moduleService = moduleService;
    }

    @PostMapping(value = "/new")
    @ApiOperation(value = "모듈등록")
    public ResponseEntity<BaseResponse<Module>> moduleForm(
            @ApiIgnore Principal principal,
            @RequestPart(value = "image", required = false) MultipartFile photo,
            @RequestPart(value = "data", required = false)ModuleFormRequest moduleFormRequest
    ) throws Exception {
        if(principal == null){
            BaseResponse baseResponse = BaseResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("no authorization")
                    .build();
            return new ResponseEntity(baseResponse, HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ModuleFormDto moduleFormDto = modelMapper.map(moduleFormRequest, ModuleFormDto.class);
        moduleFormDto.setImage(photo);

        Long moduleId = moduleService.saveModule(moduleFormDto);
        System.out.println(photo);
        System.out.println(moduleFormDto);

        BaseResponse baseResponse = BaseResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("module register completed")
                .data(moduleId)
                .build();

        return new ResponseEntity(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping(value="/list/all/newest/{page-no}")
    @ApiOperation(value="모든 모듈 최신순 조회")
    public ResponseEntity<BaseResponse<ModuleSelectionResponseResult>> getAllModuleListPagination(
            @PathVariable("page-no") String pageNumber){
        ModuleSelectionResponseResult modules = moduleService.getAllModuleListOrderByNewest(Integer.valueOf(pageNumber));

        BaseResponse baseResponse = BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("getting All module by newest")
                .data(modules)
                .build();

        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }

    @GetMapping(value="/list/all")
    @ApiOperation(value="모든 모듈 조회")
    public ResponseEntity<BaseResponse<ModuleSelectionResponseResult>> getAllModuleList(){
        List<ModuleSelectionResponse> modules = moduleService.getAllModuleList();
        BaseResponse baseResponse = BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("getting All module by newest")
                .data(modules)
                .build();

        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }

}