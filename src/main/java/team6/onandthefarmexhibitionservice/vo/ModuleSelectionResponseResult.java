package team6.onandthefarmexhibitionservice.vo;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleSelectionResponseResult {
    private List<ModuleSelectionResponse> moduleListResponses;
    private PageVo pageVo;
}
