package team6.onandthefarmexhibitionservice.service;

import java.util.List;

import team6.onandthefarmexhibitionservice.dto.DataPickerFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountDeleteDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountFormDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountUpdateFormDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionItemPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryApplyFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryDeleteFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryUpdateFormRequestDto;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountDetailResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionCategoryResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionItemInfoResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionItemsInfoResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionSelectionResponseResult;
import team6.onandthefarmexhibitionservice.vo.ExhibitionTemporaryAllResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAllResponse;
import team6.onandthefarmexhibitionservice.vo.dataPicker.DataPickerResponse;

public interface ExhibitionService {
	Long createExhibitionAccount(ExhibitionAccountFormDto exhibitionAccountFormDto);
	Long updateExhibitionAccount(ExhibitionAccountUpdateFormDto exhibitionAccountUpdateFormDto);
	Long deleteExhibitionAccount(ExhibitionAccountDeleteDto exhibitionAccountDeleteDto);
	List<ExhibitionCategoryResponse> getAllExhibitionCategory();
	List<ExhibitionAccountResponse> getExhibitionAccountByExhibitionCategory(Long exhibitionCategoryId);
	Long createDataPicker(DataPickerFormRequestDto dataPickerFormRequestDto);
	ExhibitionSelectionResponseResult getAllExhibitionListOrderByNewest(Integer pageNumber);
	ExhibitionAccountDetailResponse getExhibitionAccountDetail(Long exhibitionAccountId);
	Long createExhibitionTemporary(ExhibitionTemporaryFormRequestDto exhibitionTemporaryFormRequestDto);
	Long updateExhibitionTemporary(ExhibitionTemporaryUpdateFormRequestDto exhibitionTemporaryUpdateFormRequestDto);
	Long deleteExhibitionTemporary(ExhibitionTemporaryDeleteFormRequestDto exhibitionTemporaryDeleteFormRequestDto);
	List<Long> applyExhibitionTemporary(ExhibitionTemporaryApplyFormRequestDto exhibitionTemporaryApplyFormRequestDto);
	List<ExhibitionItemsInfoResponse> getExhibitionItemsInfos(Long exhibitionAccountId);
	List<ExhibitionItemInfoResponse> getExhibitionItemInfos(Long exhibitionItemsId);
	List<Long> updateExhibitionItemPriority(
			ExhibitionItemPriorityUpdateFormsRequestDto exhibitionItemPriorityUpdateFormsRequestDto);
	List<Long> updateExhibitionAccountPriority(
			ExhibitionAccountPriorityUpdateFormsRequestDto exhibitionAccountPriorityUpdateFormsRequestDto);
	List<Long> updateExhibitionTemporaryPriority(
			ExhibitionTemporaryPriorityUpdateFormsRequestDto exhibitionTemporaryPriorityUpdateFormsRequestDto);
	List<ExhibitionTemporaryAllResponse> getAllExhibitionTemporary(String time);
	List<ExhibitionAllResponse> getAllExhibition();
	List<DataPickerResponse> getAllDataPicker();
}
