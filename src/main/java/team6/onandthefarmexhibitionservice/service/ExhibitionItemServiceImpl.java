package team6.onandthefarmexhibitionservice.service;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.extern.slf4j.Slf4j;
import team6.onandthefarmexhibitionservice.dto.item.BadgeFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.item.BannerFormRequestDto;
import team6.onandthefarmexhibitionservice.entity.item.Badge;
import team6.onandthefarmexhibitionservice.entity.item.Banner;
import team6.onandthefarmexhibitionservice.repository.ExhibitionAccountRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionCategoryRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionItemRepository;
import team6.onandthefarmexhibitionservice.repository.item.BadgeRepository;
import team6.onandthefarmexhibitionservice.repository.item.BannerRepository;
import team6.onandthefarmexhibitionservice.util.DateUtils;
import team6.onandthefarmexhibitionservice.util.S3Upload;

@Service
@Transactional
@Slf4j
public class ExhibitionItemServiceImpl implements ExhibitionItemService {

	private ExhibitionAccountRepository exhibitionAccountRepository;
	private ExhibitionCategoryRepository exhibitionCategoryRepository;
	private ExhibitionItemRepository exhibitionItemRepository;
	private BadgeRepository badgeRepository;
	private BannerRepository bannerRepository;
	private DateUtils dateUtils;
	private Environment env;
	private S3Upload s3Upload;

	public ExhibitionItemServiceImpl(ExhibitionAccountRepository exhibitionAccountRepository,
			ExhibitionCategoryRepository exhibitionCategoryRepository,
			ExhibitionItemRepository exhibitionItemRepository,
			BannerRepository bannerRepository,
			BadgeRepository badgeRepository,
			DateUtils dateUtils,
			Environment env,
			S3Upload s3UpLoad){
		this.exhibitionAccountRepository = exhibitionAccountRepository;
		this.exhibitionCategoryRepository = exhibitionCategoryRepository;
		this.exhibitionItemRepository = exhibitionItemRepository;
		this.bannerRepository = bannerRepository;
		this.badgeRepository = badgeRepository;
		this.dateUtils = dateUtils;
		this.s3Upload = s3UpLoad;
		this.env = env;
	}
	@Override
	public Long createBanner(BannerFormRequestDto bannerFormRequestDto) throws IOException {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Banner banner = modelMapper.map(bannerFormRequestDto, Banner.class);

		String url = s3Upload.bannerUpload(bannerFormRequestDto.getBannerImg().get(0));
		banner.setBannerImg(url);

		banner.setBannerCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
		banner.setBannerStatus(true);

		return bannerRepository.save(banner).getBannerId();
	}

	@Override
	public Long createBadge(BadgeFormRequestDto badgeFormRequestDto)throws IOException{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Badge badge = modelMapper.map(badgeFormRequestDto, Badge.class);

		String url = s3Upload.badgeUpload(badgeFormRequestDto.getBadgeImg().get(0));
		badge.setBadgeImg(url);

		badge.setBadgeCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
		badge.setBadgeStatus(true);

		return badgeRepository.save(badge).getBadgeId();

	}
	//
	// @Override
	// public Long createExhibitionProductItem(ExhibitionProductItemFormRequestDto exhibitionProductItemFormRequestDto){
	// 	ModelMapper modelMapper = new ModelMapper();
	// 	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	//
	// 	ExhibitionProductItem exhibitionProductItem = modelMapper.map(exhibitionProductItemFormRequestDto, ExhibitionProductItem.class);
	//
	// 	Long exhibitionAccountId = exhibitionProductItemFormRequestDto.getExhibitionAccountId();
	// 	Long exhibitionCategoryId = exhibitionProductItemFormRequestDto.getExhibitionCategoryId();
	// 	Optional<ExhibitionAccount> exhibitionAccount = exhibitionAccountRepository.findById(exhibitionAccountId);
	// 	Optional<ExhibitionCategory> exhibitionCategory = exhibitionCategoryRepository.findById(exhibitionCategoryId);
	//
	// 	exhibitionProductItem.setExhibitionAccount(exhibitionAccount.get());
	// 	exhibitionProductItem.setExhibitionCategory(exhibitionCategory.get());
	// 	exhibitionProductItem.setExhibitionProductItemCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
	// 	exhibitionProductItem.setExhibitionProductItemStatus(true);
	//
	// 	return exhibitionProductItemRepository.save(exhibitionProductItem).getExhibitionProductItemId();
	// }
}
