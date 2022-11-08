package team6.onandthefarmexhibitionservice.dto;

import javax.validation.constraints.NotBlank;
import team6.onandthefarmexhibitionservice.entity.Module;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleFormDto {
    private Long moduleId;

    @NotBlank(message = "모듈이름은 필수 입력 값입니다.")
    private String moduleName;

    @NotBlank(message = "모듈정보는 필수 입력 값입니다.")
    private String moduleContent;

    @NotBlank(message = "모듈이미지는 필수 입력 값입니다.")
    private String moduleImgSrc;

    private MultipartFile image;
}
