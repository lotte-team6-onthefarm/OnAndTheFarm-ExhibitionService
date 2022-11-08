package team6.onandthefarmexhibitionservice.service;

import java.io.IOException;
import java.util.List;

import team6.onandthefarmexhibitionservice.dto.ModuleFormDto;
import team6.onandthefarmexhibitionservice.vo.ModuleSelectionResponse;
import team6.onandthefarmexhibitionservice.vo.ModuleSelectionResponseResult;

public interface ModuleService {
    Long saveModule(ModuleFormDto moduleFormDto) throws IOException;

    ModuleSelectionResponseResult getAllModuleListOrderByNewest(Integer pageNumber);

    List<ModuleSelectionResponse> getAllModuleList();

}
