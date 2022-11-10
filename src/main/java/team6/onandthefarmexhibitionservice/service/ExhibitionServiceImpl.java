package team6.onandthefarmexhibitionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import team6.onandthefarmexhibitionservice.dto.DataPickerFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountDeleteDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountFormDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountPriorityUpdateFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionAccountUpdateFormDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionItemFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionItemPriorityUpdateFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionItemPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionItemsFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryApplyFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryDeleteFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryPriorityUpdateFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryPriorityUpdateFormsRequestDto;
import team6.onandthefarmexhibitionservice.dto.ExhibitionTemporaryUpdateFormRequestDto;
import team6.onandthefarmexhibitionservice.entity.DataPicker;
import team6.onandthefarmexhibitionservice.entity.Exhibition;
import team6.onandthefarmexhibitionservice.entity.ExhibitionAccount;
import team6.onandthefarmexhibitionservice.entity.ExhibitionCategory;
import team6.onandthefarmexhibitionservice.entity.ExhibitionTemporary;
import team6.onandthefarmexhibitionservice.entity.item.ExhibitionItem;
import team6.onandthefarmexhibitionservice.entity.item.ExhibitionItems;
import team6.onandthefarmexhibitionservice.repository.DataPickerRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionAccountPagingRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionAccountRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionCategoryRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionItemRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionItemsRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionTemporaryRepository;
import team6.onandthefarmexhibitionservice.util.DateUtils;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountDetailResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountItemDetailResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountItemsDetailResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAccountResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionAllResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionCategoryResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionItemInfoResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionItemsInfoResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionSelectionResponse;
import team6.onandthefarmexhibitionservice.vo.ExhibitionSelectionResponseResult;
import team6.onandthefarmexhibitionservice.vo.ExhibitionTemporaryAllResponse;
import team6.onandthefarmexhibitionservice.vo.PageVo;

@Service
@Transactional
@Slf4j
public class ExhibitionServiceImpl implements ExhibitionService {
	private ExhibitionAccountRepository exhibitionAccountRepository;
	private ExhibitionCategoryRepository exhibitionCategoryRepository;
	private ExhibitionItemsRepository exhibitionItemsRepository;
	private ExhibitionItemRepository exhibitionItemRepository;
	private ExhibitionTemporaryRepository exhibitionTemporaryRepository;
	private ExhibitionAccountPagingRepository exhibitionAccountPagingRepository;
	private ExhibitionRepository exhibitionRepository;
	private DataPickerRepository dataPickerRepository;
	private DateUtils dateUtils;
	private Environment env;

	public ExhibitionServiceImpl(ExhibitionAccountRepository exhibitionAccountRepository,
								ExhibitionCategoryRepository exhibitionCategoryRepository,
								ExhibitionItemsRepository exhibitionItemsRepository,
								ExhibitionItemRepository exhibitionItemRepository,
								DataPickerRepository dataPickerRepository,
								ExhibitionTemporaryRepository exhibitionTemporaryRepository,
								ExhibitionAccountPagingRepository exhibitionAccountPagingRepository,
								ExhibitionRepository exhibitionRepository,
								DateUtils dateUtils,
								Environment env){
		this.exhibitionAccountRepository = exhibitionAccountRepository;
		this.exhibitionCategoryRepository = exhibitionCategoryRepository;
		this.exhibitionItemsRepository = exhibitionItemsRepository;
		this.exhibitionItemRepository = exhibitionItemRepository;
		this.dataPickerRepository = dataPickerRepository;
		this.exhibitionTemporaryRepository = exhibitionTemporaryRepository;
		this.exhibitionAccountPagingRepository = exhibitionAccountPagingRepository;
		this.exhibitionRepository = exhibitionRepository;
		this.env = env;
		this.dateUtils = dateUtils;
	}


	@Override
	public Long createExhibitionAccount(ExhibitionAccountFormDto exhibitionAccountFormDto) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ExhibitionAccount exhibitionAccount = modelMapper.map(exhibitionAccountFormDto, ExhibitionAccount.class);

		Long categoryId = exhibitionAccountFormDto.getExhibitionAccountCategoryId();
		Optional<ExhibitionCategory> exhibitionCategory = exhibitionCategoryRepository.findById(categoryId);

		exhibitionAccount.setExhibitionCategory(exhibitionCategory.get());
		exhibitionAccount.setExhibitionAccountCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
		exhibitionAccount.setExhibitionAccountStatus(true);

		Long exhibitionAccountId = exhibitionAccountRepository.save(exhibitionAccount).getExhibitionAccountId();


		List<ExhibitionItemsFormRequestDto> exhibitionItemsFormRequestDtos = exhibitionAccountFormDto.getExhibitionItemsFormRequestDtos();
		for (ExhibitionItemsFormRequestDto exhibitionItemsFormRequestDto : exhibitionItemsFormRequestDtos){
			ExhibitionItems exhibitionItems = ExhibitionItems.builder()
					.exhibitionAccount(exhibitionAccountRepository.findById(exhibitionAccountId).get())
					.exhibitionItemsName(exhibitionItemsFormRequestDto.getExhibitionItemsName())
					.exhibitionItemsDetail(exhibitionItemsFormRequestDto.getExhibitionItemsDetail())
					.build();
			Long exhibitionItemsId = exhibitionItemsRepository.save(exhibitionItems).getExhibitionItemsId();
			List<ExhibitionItemFormRequestDto> exhibitionItemFormRequestDtos = exhibitionItemsFormRequestDto.getExhibitionItemFormRequestDtos();

			for(ExhibitionItemFormRequestDto itemFormRequestDto : exhibitionItemFormRequestDtos) {
				ExhibitionItem exhibitionItem = modelMapper.map(itemFormRequestDto, ExhibitionItem.class);
				exhibitionItem.setExhibitionItemCategoryId(categoryId);
				exhibitionItem.setExhibitionItemCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
				exhibitionItem.setExhibitionItemStatus(true);
				exhibitionItem.setExhibitionItems(exhibitionItemsRepository.findById(exhibitionItemsId).get());

				exhibitionItemRepository.save(exhibitionItem);
			}

		}
		return exhibitionAccountId;
	}
	@Override
	public Long updateExhibitionAccount(ExhibitionAccountUpdateFormDto exhibitionAccountUpdateFormDto){

		Optional<ExhibitionAccount> exhibitionAccount = exhibitionAccountRepository.findById(exhibitionAccountUpdateFormDto.getExhibitionAccountId());
		Optional<ExhibitionCategory> exhibitionCategory = exhibitionCategoryRepository.findById(exhibitionAccountUpdateFormDto.getExhibitionCategoryId());

		exhibitionAccount.get().setExhibitionAccountName(exhibitionAccountUpdateFormDto.getExhibitionAccountName());
		exhibitionAccount.get().setExhibitionCategory(exhibitionCategory.get());
		exhibitionAccount.get().setExhibitionAccountStartTime(exhibitionAccountUpdateFormDto.getExhibitionAccountStartTime());
		exhibitionAccount.get().setExhibitionAccountEndTime(exhibitionAccountUpdateFormDto.getExhibitionAccountEndTime());
		exhibitionAccount.get().setExhibitionAccountStatus(exhibitionAccountUpdateFormDto.isExhibitionAccountStatus());

		return exhibitionAccount.get().getExhibitionAccountId();
	}

	@Override
	public Long deleteExhibitionAccount(ExhibitionAccountDeleteDto exhibitionAccountDeleteDto){

		Optional<ExhibitionAccount> exhibitionAccount = exhibitionAccountRepository.findById(exhibitionAccountDeleteDto.getExhibitionAccountId());
		exhibitionAccount.get().setExhibitionAccountStatus(false);
		exhibitionAccount.get().setExhibitionAccountModifiedAt(dateUtils.transDate(env.getProperty("dateutils.format")));

		return exhibitionAccount.get().getExhibitionAccountId();
	}

	@Override
	public List<ExhibitionAccountResponse> getExhibitionAccountByExhibitionCategory(Long exhibitionCategoryId){

		List<ExhibitionAccountResponse> responses = new ArrayList<>();
		List<ExhibitionAccount> exhibitionAccounts = exhibitionAccountRepository.findByExhibitionCategoryId(exhibitionCategoryId);
		for (ExhibitionAccount e : exhibitionAccounts) {
			ExhibitionAccountResponse exhibitionAccountResponse = new ExhibitionAccountResponse();
			exhibitionAccountResponse.setExhibitionAccountId(e.getExhibitionAccountId());
			exhibitionAccountResponse.setExhibitionAccountName(e.getExhibitionAccountName());
			responses.add(exhibitionAccountResponse);
		}
		return responses;
	}

	@Override
	public List<ExhibitionCategoryResponse> getAllExhibitionCategory(){
		List<ExhibitionCategoryResponse> responses = new ArrayList<>();
		List<ExhibitionCategory> exhibitionCategories = new ArrayList<>();
		Iterable<ExhibitionCategory> all = exhibitionCategoryRepository.findAll();
		for (ExhibitionCategory exhibitionCategory : all) {
			exhibitionCategories.add(exhibitionCategory);
		}

		for (ExhibitionCategory e : exhibitionCategories) {
			ExhibitionCategoryResponse  exhibitionCategoryResponse = new ExhibitionCategoryResponse();
			exhibitionCategoryResponse.setExhibitionCategoryId(e.getExhibitionCategoryId());
			exhibitionCategoryResponse.setExhibitionCategoryName(e.getExhibitionCategoryName());
			responses.add(exhibitionCategoryResponse);
		}
		return responses;
	}

	@Override
	public List<ExhibitionItemsInfoResponse> getExhibitionItemsInfos(Long exhibitionAccountId){
		List<ExhibitionItems> exhibitionItemss = exhibitionItemsRepository.findExhibitionItemsByExhibitionAccountId(exhibitionAccountId);
		List<ExhibitionItemsInfoResponse> result = new ArrayList<>();
		for (ExhibitionItems exhibitionItems : exhibitionItemss) {
			ExhibitionItemsInfoResponse exhibitionItemsInfoResponse = ExhibitionItemsInfoResponse.builder()
					.exhibitionItemsId(exhibitionItems.getExhibitionItemsId())
					.exhibitionItemsName(exhibitionItems.getExhibitionItemsName())
					.exhibitionItemsDetail(exhibitionItems.getExhibitionItemsDetail())
					.build();
			result.add(exhibitionItemsInfoResponse);
		}
		return result;
	}

	@Override
	public List<ExhibitionItemInfoResponse> getExhibitionItemInfos(Long exhibitionItemsId){
		List<ExhibitionItem> exhibitionItems = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItemsId);
		List<ExhibitionItemInfoResponse> result = new ArrayList<>();
		for (ExhibitionItem exhibitionItem : exhibitionItems) {
			ExhibitionItemInfoResponse exhibitionItemInfoResponse = ExhibitionItemInfoResponse.builder()
					.exhibitionItemId(exhibitionItem.getExhibitionItemId())
					.exhibitionItemNumber(exhibitionItem.getExhibitionItemNumber())
					.exhibitionItemPriority(exhibitionItem.getExhibitionItemPriority())
					.build();
			result.add(exhibitionItemInfoResponse);
		}
		return result;
	}

	@Override
	public Long createDataPicker(DataPickerFormRequestDto dataPickerFormRequestDto){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		DataPicker dataPicker = modelMapper.map(dataPickerFormRequestDto, DataPicker.class);

		dataPicker.setExhibitionCategory(exhibitionCategoryRepository.findById(dataPickerFormRequestDto.getExhibitionCategoryId()).get());
		dataPicker.setDataPickerCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
		dataPicker.setDataPickerStatus(true);

		return dataPickerRepository.save(dataPicker).getDataPickerId();
	}

	@Override
	public ExhibitionSelectionResponseResult getAllExhibitionListOrderByNewest(Integer pageNumber){
		PageRequest pageRequest = PageRequest.of(pageNumber, 16, Sort.by("exhibitionAccountCreatedAt").descending());

		Page<ExhibitionAccount> exhibitionAccounts = exhibitionAccountPagingRepository.findExhibitionOrderBy(pageRequest);
		int totalPage = exhibitionAccounts.getTotalPages();
		Long totalElements = exhibitionAccounts.getTotalElements();

		PageVo pageVo = PageVo.builder()
				.totalPage(totalPage)
				.nowPage(pageNumber)
				.totalElement(totalElements)
				.build();

		List<ExhibitionSelectionResponse> exhibitionAccountList = new ArrayList<>();
		for(ExhibitionAccount e : exhibitionAccounts) {
			ExhibitionSelectionResponse eResponse = new ExhibitionSelectionResponse(e);
			exhibitionAccountList.add(eResponse);
		}
		ExhibitionSelectionResponseResult exhibitionSelectionResponseResult = ExhibitionSelectionResponseResult.builder()
					.exhibitionSelectionResponses(exhibitionAccountList)
					.pageVo(pageVo)
					.build();
		return exhibitionSelectionResponseResult;
	}

	@Override
	public ExhibitionAccountDetailResponse getExhibitionAccountDetail(Long exhibitionAccountId){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ExhibitionAccountDetailResponse exhibitionAccountDetail = new ExhibitionAccountDetailResponse();	// 프론트에 보내 줄 responses 빈 class 생성
		exhibitionAccountDetail.setExhibitionAccountItemsDetailResponseList(new ArrayList<>());

		ExhibitionAccount exhibitionAccount = exhibitionAccountRepository.findByExhibitionAccountId(exhibitionAccountId);
		exhibitionAccountDetail.setExhibitionAccountId(exhibitionAccount.getExhibitionAccountId());
		exhibitionAccountDetail.setExhibitionAccountName(exhibitionAccount.getExhibitionAccountName());
		exhibitionAccountDetail.setExhibitionAccountDetail(exhibitionAccount.getExhibitionAccountDetail());
		exhibitionAccountDetail.setExhibitionAccountStartTime(exhibitionAccount.getExhibitionAccountStartTime());
		exhibitionAccountDetail.setExhibitionAccountEndTime(exhibitionAccount.getExhibitionAccountEndTime());

		List<ExhibitionItems> exhibitionsItemsDetailList =  exhibitionItemsRepository.findExhibitionItemsDetail(exhibitionAccountId);
		for(ExhibitionItems items : exhibitionsItemsDetailList) {
			ExhibitionAccountItemsDetailResponse exhibitionAccountItemsDetail = new ExhibitionAccountItemsDetailResponse();	// 소재 리스트 respose 선언
			exhibitionAccountItemsDetail.setExhibitionAccountItemDetailResponseList(new ArrayList<>());

			exhibitionAccountItemsDetail.setExhibitionItemsId(items.getExhibitionItemsId());
			exhibitionAccountItemsDetail.setExhibitionItemsName(items.getExhibitionItemsName());
			exhibitionAccountItemsDetail.setExhibitionItemsDetail(items.getExhibitionItemsDetail());

			List<ExhibitionItem> exhibitionItemDetailList = exhibitionItemRepository.findExhibitionItemDetail(items.getExhibitionItemsId());
			for(ExhibitionItem item  : exhibitionItemDetailList){
				ExhibitionAccountItemDetailResponse exhibitionAccountItemDetail = new ExhibitionAccountItemDetailResponse();	// 소재 respose 선언
				exhibitionAccountItemDetail.setExhibitionItemId(item.getExhibitionItemId());
				exhibitionAccountItemDetail.setExhibitionItemCategoryId(item.getExhibitionItemCategoryId());
				exhibitionAccountItemDetail.setExhibitionItemPriority(item.getExhibitionItemPriority());

				exhibitionAccountItemsDetail.getExhibitionAccountItemDetailResponseList().add(exhibitionAccountItemDetail);	// 소재 리스트에 소재 추가
			}
			exhibitionAccountDetail.getExhibitionAccountItemsDetailResponseList().add(exhibitionAccountItemsDetail);	// 구좌에 소재 리스트 추가
		}
		return exhibitionAccountDetail;
	}

	@Override
	public Long createExhibitionTemporary(ExhibitionTemporaryFormRequestDto exhibitionTemporaryFormRequestDto){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ExhibitionTemporary exhibitionTemporary = modelMapper.map(exhibitionTemporaryFormRequestDto, ExhibitionTemporary.class);

		exhibitionTemporary.setExhibitionTemporaryCategory(exhibitionCategoryRepository.findById(exhibitionTemporaryFormRequestDto.getExhibitionTemporaryCategoryId()).get());
		Long exhibitionTemporaryId = exhibitionTemporaryRepository.save(exhibitionTemporary).getExhibitionTemporaryId();

		return exhibitionTemporaryId;
	}

	@Override
	public Long updateExhibitionTemporary(
			ExhibitionTemporaryUpdateFormRequestDto exhibitionTemporaryUpdateFormRequestDto){

		ExhibitionTemporary savedExhibitionTemporary = exhibitionTemporaryRepository.findById(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryId()).get();

		savedExhibitionTemporary.setExhibitionTemporaryAccountId(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryAccountId());
		savedExhibitionTemporary.setExhibitionTemporaryCategory(exhibitionCategoryRepository.findById(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryCategoryId()).get());
		savedExhibitionTemporary.setExhibitionTemporaryItemsId(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryItemsId());
		savedExhibitionTemporary.setExhibitionTemporaryModuleName(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryModuleName());
		savedExhibitionTemporary.setExhibitionTemporaryDataPicker(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryDataPicker());
		savedExhibitionTemporary.setExhibitionTemporaryPriority(exhibitionTemporaryUpdateFormRequestDto.getExhibitionTemporaryPriority());

		ExhibitionTemporary exhibitionTemporaryId = exhibitionTemporaryRepository.save(savedExhibitionTemporary);
		return exhibitionTemporaryId.getExhibitionTemporaryId();
	}

	@Override
	public Long deleteExhibitionTemporary(
			ExhibitionTemporaryDeleteFormRequestDto exhibitionTemporaryDeleteFormRequestDto){
		Long exhibitionTemporaryId = exhibitionTemporaryDeleteFormRequestDto.getExhibitionTemporaryId();
		ExhibitionTemporary targetExhibitionTemporary = exhibitionTemporaryRepository.findById(exhibitionTemporaryId).get();
		exhibitionTemporaryRepository.delete(targetExhibitionTemporary);

		return exhibitionTemporaryId;
	}

	@Override
	public List<ExhibitionTemporaryAllResponse> getAllExhibitionTemporary(){
		List<ExhibitionTemporary> exhibitionTemporaries = (List<ExhibitionTemporary>)exhibitionTemporaryRepository.findAll(Sort.by(Sort.Direction.ASC, "exhibitionTemporaryPriority"));
		List<ExhibitionTemporaryAllResponse> exhibitionTemporaryAllResponses = new ArrayList<>();
		for (ExhibitionTemporary exhibitionTemporary : exhibitionTemporaries) {
			ExhibitionAccount exhibitionAccount = exhibitionAccountRepository.findById(exhibitionTemporary.getExhibitionTemporaryAccountId()).get();
			ExhibitionTemporaryAllResponse exhibitionTemporaryAllResponse = ExhibitionTemporaryAllResponse.builder()
					.exhibitionTemporaryId(exhibitionTemporary.getExhibitionTemporaryId())
					.exhibitionTemporaryCategoryId(exhibitionTemporary.getExhibitionTemporaryCategory().getExhibitionCategoryId())
					.exhibitionTemporaryModuleName(exhibitionTemporary.getExhibitionTemporaryModuleName())
					.exhibitionTemporaryDataPicker(exhibitionTemporary.getExhibitionTemporaryDataPicker())
					.exhibitionTemporaryAccountId(exhibitionTemporary.getExhibitionTemporaryAccountId())
					.exhibitionTemporaryAccountName(exhibitionAccount.getExhibitionAccountName())
					.exhibitionTemporaryItemsId(exhibitionTemporary.getExhibitionTemporaryItemsId())
					.exhibitionTemporaryPriority(exhibitionTemporary.getExhibitionTemporaryPriority())
					.build();
			exhibitionTemporaryAllResponses.add(exhibitionTemporaryAllResponse);
		}
		return exhibitionTemporaryAllResponses;
	}

	@Override
	public List<ExhibitionAllResponse> getAllExhibition(){
		List<Exhibition> exhibitions = exhibitionRepository.getTrueExhibitionOrderByPriority();
		List<ExhibitionAllResponse> exhibitionAllResponses = new ArrayList<>();
		for(Exhibition e : exhibitions){
			ExhibitionAccount exhibitionAccount = exhibitionAccountRepository.findById(e.getExhibitionId()).get();
			ExhibitionAllResponse exhibitionAllResponse = ExhibitionAllResponse.builder()
					.exhibitionId(e.getExhibitionId())
					.exhibitionCategoryId(e.getExhibitionCategory().getExhibitionCategoryId())
					.exhibitionModuleName(e.getExhibitionModuleName())
					.exhibitionDataPickerId(e.getExhibitionDataPickerId())
					.exhibitionAccountId(e.getExhibitionAccountId())
					.exhibitionAccountName(exhibitionAccount.getExhibitionAccountName())
					.exhibitionItemsId(e.getExhibitionItemsId())
					.exhibitionPriority(e.getExhibitionPriority())
					.build();
			exhibitionAllResponses.add(exhibitionAllResponse);
		}
		return exhibitionAllResponses;
	}

	@Override
	public List<Long> updateExhibitionItemPriority(
			ExhibitionItemPriorityUpdateFormsRequestDto exhibitionItemPriorityUpdateFormsRequestDto){
		List<Long> changedItemIds = new ArrayList<>();
		List<ExhibitionItemPriorityUpdateFormRequestDto> exhibitionItemPriorityUpdateFormRequestDtos =
				exhibitionItemPriorityUpdateFormsRequestDto.getExhibitionItemPriorityUpdateFormRequestDtos();
		for (ExhibitionItemPriorityUpdateFormRequestDto eDto : exhibitionItemPriorityUpdateFormRequestDtos) {
			Long targetId = eDto.getExhibitionItemId();
			ExhibitionItem exhibitionItem = exhibitionItemRepository.findById(targetId).get();
			exhibitionItem.setExhibitionItemPriority(eDto.getExhibitionItemPriority());
			changedItemIds.add(exhibitionItem.getExhibitionItemId());
		}
		return changedItemIds;
	}

	@Override
	public List<Long> updateExhibitionAccountPriority(
			ExhibitionAccountPriorityUpdateFormsRequestDto exhibitionAccountPriorityUpdateFormsRequestDto){
		List<Long> changedAccountIds = new ArrayList<>();
		List<ExhibitionAccountPriorityUpdateFormRequestDto> exhibitionAccountPriorityUpdateFormRequestDtos =
				exhibitionAccountPriorityUpdateFormsRequestDto.getExhibitionAccountPriorityUpdateFormRequestDtos();
		for (ExhibitionAccountPriorityUpdateFormRequestDto eDto : exhibitionAccountPriorityUpdateFormRequestDtos) {
			Long targetId = eDto.getExhibitionAccountId();
			ExhibitionAccount exhibitionAccount = exhibitionAccountRepository.findById(targetId).get();
			exhibitionAccount.setExhibitionAccountPriority(eDto.getExhibitionAccountPriority());
			changedAccountIds.add(exhibitionAccount.getExhibitionAccountId());
		}
		return changedAccountIds;
	}

	@Override
	public List<Long> updateExhibitionTemporaryPriority(
			ExhibitionTemporaryPriorityUpdateFormsRequestDto exhibitionTemporaryPriorityUpdateFormsRequestDto){
		List<Long> changedExhibitionTemporaryIds = new ArrayList<>();
		List<ExhibitionTemporaryPriorityUpdateFormRequestDto> exhibitionTemporaryPriorityUpdateFormRequestDtos =
				exhibitionTemporaryPriorityUpdateFormsRequestDto.getExhibitionTemporaryPriorityUpdateFormRequests();

		for (ExhibitionTemporaryPriorityUpdateFormRequestDto eDto : exhibitionTemporaryPriorityUpdateFormRequestDtos) {
			Long targetId = eDto.getExhibitionTemporaryId();
			ExhibitionTemporary exhibitionTemporary = exhibitionTemporaryRepository.findById(targetId).get();
			exhibitionTemporary.setExhibitionTemporaryPriority(eDto.getExhibitionTemporaryPriority());
			changedExhibitionTemporaryIds.add(exhibitionTemporary.getExhibitionTemporaryId());
		}
		return changedExhibitionTemporaryIds;
	}

	@Override
	public List<Long> applyExhibitionTemporary(
			ExhibitionTemporaryApplyFormRequestDto exhibitionTemporaryApplyFormRequestDto){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<Long> exhibitionIds = new ArrayList<>();

		List<Long> exhibitionTemporaryIds = exhibitionTemporaryApplyFormRequestDto.getExhibitionTemporaryIds();

		List<Exhibition> trueExhibition = exhibitionRepository.getTrueExhibitions();
		//true -> false로 변경 , 수정시간 갱신
		for (Exhibition exhibition : trueExhibition) {
			exhibition.setExhibitionActivation(false);
			exhibition.setExhibitionStatus(false);
			exhibition.setExhibitionTemporaryModifiedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
		}

		for (Long exhibitionTemporaryId : exhibitionTemporaryIds) {
			ExhibitionTemporary exhibitionTemporary = exhibitionTemporaryRepository.findById(exhibitionTemporaryId).get();
			Exhibition exhibition = Exhibition.builder()
					.exhibitionAccountId(exhibitionTemporary.getExhibitionTemporaryAccountId())
					.exhibitionActivation(true)
					.exhibitionStatus(true)
					.exhibitionDataPickerId(exhibitionTemporary.getExhibitionTemporaryDataPicker())
					.exhibitionItemsId(exhibitionTemporary.getExhibitionTemporaryItemsId())
					.exhibitionModuleName(exhibitionTemporary.getExhibitionTemporaryModuleName())
					.exhibitionPriority(exhibitionTemporary.getExhibitionTemporaryPriority())
					.exhibitionCategory(exhibitionCategoryRepository.findById(exhibitionTemporary.getExhibitionTemporaryCategory().getExhibitionCategoryId()).get())
					.build();
			exhibition.setExhibitionTemporaryCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
			exhibitionIds.add(exhibitionRepository.save(exhibition).getExhibitionId());
		}

		return exhibitionIds;

	}
}
