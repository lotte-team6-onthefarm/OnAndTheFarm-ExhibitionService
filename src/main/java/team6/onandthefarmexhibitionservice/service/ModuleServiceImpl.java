package team6.onandthefarmexhibitionservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.extern.slf4j.Slf4j;
import team6.onandthefarmexhibitionservice.dto.ModuleFormDto;
import team6.onandthefarmexhibitionservice.repository.ModuleImgRepository;
import team6.onandthefarmexhibitionservice.repository.ModulePagingRepository;
import team6.onandthefarmexhibitionservice.repository.ModuleRepository;
import team6.onandthefarmexhibitionservice.util.DateUtils;
import team6.onandthefarmexhibitionservice.util.S3Upload;
import team6.onandthefarmexhibitionservice.vo.ModuleSelectionResponse;
import team6.onandthefarmexhibitionservice.vo.ModuleSelectionResponseResult;
import team6.onandthefarmexhibitionservice.vo.PageVo;
import team6.onandthefarmexhibitionservice.entity.Module;

@Service
@Transactional
@Slf4j
public class ModuleServiceImpl implements ModuleService{
    private final ModuleRepository moduleRepository;

    private final ModulePagingRepository modulePagingRepository;

    private final ModuleImgRepository moduleImgRepository;

    private final DateUtils dateUtils;

    private final S3Upload s3Upload;

    private Environment env;

    @Autowired
    public ModuleServiceImpl(ModuleRepository moduleRepository,
                             ModulePagingRepository modulePagingRepository,
                             ModuleImgRepository moduleImgRepository,
                             DateUtils dateUtils,
                             Environment env,
                             S3Upload s3Upload){
        this.moduleRepository = moduleRepository;
        this.modulePagingRepository = modulePagingRepository;
        this.moduleImgRepository = moduleImgRepository;
        this.dateUtils = dateUtils;
        this.env = env;
        this.s3Upload = s3Upload;
    }

    @Override
    public Long saveModule(ModuleFormDto moduleFormDto) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Module module = modelMapper.map(moduleFormDto, Module.class);

        String url = s3Upload.moduleUpload(moduleFormDto.getImage());
        module.setModuleImgSrc(url);
        module.setModuleCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
        module.setModuleStatus(true);
        module.setModuleUsableStatus(true);

        return moduleRepository.save(module).getModuleId();
    }

    @Override
    public ModuleSelectionResponseResult getAllModuleListOrderByNewest (Integer pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, 8, Sort.by("moduleCreatedAt").descending());

        Page<Module> moduleList = modulePagingRepository.findModuleOrderBy(pageRequest);
        System.out.println(moduleList);
        int totalPage = moduleList.getTotalPages();
        Long totalElements = moduleList.getTotalElements();

        PageVo pageVo = PageVo.builder()
                .totalPage(totalPage)
                .nowPage(pageNumber)
                .totalElement(totalElements)
                .build();

        List<ModuleSelectionResponse> moduleResponseList = new ArrayList<>();

        for(Module m : moduleList){
            ModuleSelectionResponse mResponse = ModuleSelectionResponse.builder()
                    .moduleId(m.getModuleId())
                    .moduleName(m.getModuleName())
                    .moduleContent(m.getModuleContent())
                    .moduleImgSrc(m.getModuleImgSrc())
                    .moduleDataSize(m.getModuleDataSize())
                    .moduleUsableStatus(m.isModuleUsableStatus())
                    .moduleStatus(m.isModuleStatus())
                    .moduleCreatedAt(m.getModuleCreatedAt())
                    .moduleModifiedAt(m.getModuleModifiedAt())
                    .moduleDevelopCompletedAt(m.getModuleDevelopCompletedAt())
                    .moduleDevelopModifiedAt(m.getModuleDevelopModifiedAt())
                    .build();
            moduleResponseList.add(mResponse);
        }

        ModuleSelectionResponseResult moduleListResponse= ModuleSelectionResponseResult.builder()
                .moduleListResponses(moduleResponseList)
                .pageVo(pageVo)
                .build();
        return moduleListResponse;
    }


    @Override
    public List<ModuleSelectionResponse> getAllModuleList (){

        Iterator<Module> moduleIterable = moduleRepository.findAll().iterator();
        List<ModuleSelectionResponse> moduleResponseList = new ArrayList<>();
        while (true){
            if(moduleIterable.hasNext()){
                Module m = moduleIterable.next();
                ModuleSelectionResponse mResponse = ModuleSelectionResponse.builder()
                        .moduleId(m.getModuleId())
                        .moduleName(m.getModuleName())
                        .moduleContent(m.getModuleContent())
                        .moduleImgSrc(m.getModuleImgSrc())
                        .moduleDataSize(m.getModuleDataSize())
                        .moduleUsableStatus(m.isModuleUsableStatus())
                        .moduleStatus(m.isModuleStatus())
                        .moduleCreatedAt(m.getModuleCreatedAt())
                        .moduleModifiedAt(m.getModuleModifiedAt())
                        .moduleDevelopCompletedAt(m.getModuleDevelopCompletedAt())
                        .moduleDevelopModifiedAt(m.getModuleDevelopModifiedAt())
                        .build();
                moduleResponseList.add(mResponse);
            }
            else{
                break;
            }
        }
        return moduleResponseList;
    }

}
