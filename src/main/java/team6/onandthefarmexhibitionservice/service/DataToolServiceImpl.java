package team6.onandthefarmexhibitionservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.extern.slf4j.Slf4j;
import team6.onandthefarmexhibitionservice.dto.datatool.BadgeDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.BannerDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.ProductDataRequestDto;
import team6.onandthefarmexhibitionservice.dto.datatool.SnsDataRequestDto;
import team6.onandthefarmexhibitionservice.entity.item.Badge;
import team6.onandthefarmexhibitionservice.entity.item.Banner;
import team6.onandthefarmexhibitionservice.entity.item.ExhibitionItem;
import team6.onandthefarmexhibitionservice.entity.item.ExhibitionItems;
import team6.onandthefarmexhibitionservice.feignclient.MemberServiceClient;
import team6.onandthefarmexhibitionservice.feignclient.ProductServiceClient;
import team6.onandthefarmexhibitionservice.feignclient.vo.ProductVo;
import team6.onandthefarmexhibitionservice.feignclient.vo.ReviewInfoToExbt;
import team6.onandthefarmexhibitionservice.feignclient.vo.SellerClientSellerDetailResponse;
import team6.onandthefarmexhibitionservice.feignclient.vo.WishVo;
import team6.onandthefarmexhibitionservice.repository.DataPickerRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionAccountRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionCategoryRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionItemRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionItemsRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionRepository;
import team6.onandthefarmexhibitionservice.repository.item.BadgeRepository;
import team6.onandthefarmexhibitionservice.repository.item.BannerRepository;
import team6.onandthefarmexhibitionservice.util.DateUtils;
import team6.onandthefarmexhibitionservice.vo.datatool.BadgeATypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.BadgeATypeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.BannerATypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.BannerATypeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductATypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductATypeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductBTypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductBTypeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductCTypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.ProductCTypeResponses;
import team6.onandthefarmexhibitionservice.vo.datatool.SnsATypeResponse;
import team6.onandthefarmexhibitionservice.vo.datatool.SnsATypeResponses;

@Service
@Transactional
@Slf4j
public class DataToolServiceImpl implements DataToolService{
	private ExhibitionAccountRepository exhibitionAccountRepository;
	private ExhibitionCategoryRepository exhibitionCategoryRepository;
	private ExhibitionItemsRepository exhibitionItemsRepository;
	private ExhibitionItemRepository exhibitionItemRepository;
	private ExhibitionRepository exhibitionRepository;
	private DataPickerRepository dataPickerRepository;
	private BannerRepository bannerRepository;
	private BadgeRepository badgeRepository;
	private final ProductServiceClient productServiceClient;
	private final MemberServiceClient memberServiceClient;
	private DateUtils dateUtils;
	private Environment env;

	private ExhibitionItemComparator exhibitionItemComparator = new ExhibitionItemComparator();

	public DataToolServiceImpl(
			ExhibitionAccountRepository exhibitionAccountRepository,
			ExhibitionCategoryRepository exhibitionCategoryRepository,
			ExhibitionItemsRepository exhibitionItemsRepository,
			ExhibitionItemRepository exhibitionItemRepository,
			ExhibitionRepository exhibitionRepository,
			DataPickerRepository dataPickerRepository,
			BannerRepository bannerRepository,
			BadgeRepository badgeRepository,
			ProductServiceClient productServiceClient,
			MemberServiceClient memberServiceClient,
			DateUtils dateUtils, Environment env) {
		this.exhibitionAccountRepository = exhibitionAccountRepository;
		this.exhibitionCategoryRepository = exhibitionCategoryRepository;
		this.exhibitionItemsRepository = exhibitionItemsRepository;
		this.exhibitionItemRepository = exhibitionItemRepository;
		this.exhibitionRepository = exhibitionRepository;
		this.dataPickerRepository = dataPickerRepository;
		this.bannerRepository = bannerRepository;
		this.badgeRepository = badgeRepository;
		this.productServiceClient = productServiceClient;
		this.memberServiceClient = memberServiceClient;
		this.dateUtils = dateUtils;
		this.env = env;
	}

	@Override
	public ProductATypeResponses getProductATypeItems(ProductDataRequestDto productDataRequestDto, Long userId){
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(productDataRequestDto.getItemsId()).get();
		ProductATypeResponses productATypeResponsesResult = new ProductATypeResponses();
		List<ProductATypeResponse> productATypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);
		for (ExhibitionItem item : items) {
			ProductVo product = productServiceClient.findProductByProductId(item.getExhibitionItemNumber());

			ReviewInfoToExbt reviewInfoToExbt = productServiceClient.getReviewsInfoProductId(product.getProductId());
			SellerClientSellerDetailResponse sellerClientSellerDetailResponse = memberServiceClient.findBySellerId(product.getSellerId());

			ProductATypeResponse productATypeResponse = ProductATypeResponse.builder()
					.productId(product.getProductId())
					.ImgSrc(product.getProductMainImgSrc())
					.productName(product.getProductName())
					.productPrice(product.getProductPrice())
					.sellerName(sellerClientSellerDetailResponse.getSellerName())
					.reviewRate(reviewInfoToExbt.getReviewRate())
					.reviewCount(reviewInfoToExbt.getReviewCount())
					.wishStatus(false)
					.cartStatus(false)
					.build();

			if(userId != null){
				boolean isSavedWish = productServiceClient.getWishByProductUserId(product.getProductId(), userId);
				if(isSavedWish){
					productATypeResponse.setWishStatus(true);
				}

				boolean isSavedCart = productServiceClient.getCartByProductUserId(product.getProductId(), userId);
				if(isSavedCart){
					productATypeResponse.setCartStatus(true);
				}
			}
			productATypeResponses.add(productATypeResponse);
		}
		productATypeResponsesResult.setProductATypeResponses(productATypeResponses);

		return productATypeResponsesResult;
	}

	@Override
	public BannerATypeResponses getBannerATypeItems(BannerDataRequestDto bannerDataRequestDto){
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(bannerDataRequestDto.getItemsId()).get();
		BannerATypeResponses bannerATypeResponsesResult = new BannerATypeResponses();
		List<BannerATypeResponse> bannerATypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);
		for (ExhibitionItem item : items) {
			Banner banner = bannerRepository.findById(item.getExhibitionItemNumber()).get();
			BannerATypeResponse bannerATypeResponse = BannerATypeResponse.builder()
					.ImgSrc(banner.getBannerImg())
					.connectUrl(banner.getBannerConnectUrl())
					.priority(item.getExhibitionItemPriority())
					.build();
			bannerATypeResponses.add(bannerATypeResponse);
		}
		bannerATypeResponsesResult.setBannerATypeResponses(bannerATypeResponses);

		return bannerATypeResponsesResult;
	}

	@Override
	public BadgeATypeResponses getBadgeATypeItems(BadgeDataRequestDto badgeDataRequestDto) {
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(badgeDataRequestDto.getItemsId()).get();
		BadgeATypeResponses badgeATypeResponsesResult = new BadgeATypeResponses();
		List<BadgeATypeResponse> BadgeATypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(
				exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);

		for (ExhibitionItem item : items) {
			Badge badge = badgeRepository.findById(item.getExhibitionItemId()).get();
			BadgeATypeResponse badgeATypeResponse = BadgeATypeResponse.builder()
					.ImgSrc(badge.getBadgeImg())
					.connectUrl(badge.getBadgeConnectUrl())
					.badgeName(badge.getBadgeName())
					.priority(item.getExhibitionItemPriority())
					.build();
			BadgeATypeResponses.add(badgeATypeResponse);
		}
		badgeATypeResponsesResult.setBadgeATypeResponseList(BadgeATypeResponses);

		return badgeATypeResponsesResult;
	}

	@Override
	public ProductBTypeResponses getProductBTypeItems(ProductDataRequestDto productDataRequestDto, Long userId){
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(productDataRequestDto.getItemsId()).get();
		ProductBTypeResponses productBTypeResponsesResult = new ProductBTypeResponses();
		List<ProductBTypeResponse> productBTypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);
		for (ExhibitionItem item : items) {
			ProductVo product = productServiceClient.findProductByProductId(item.getExhibitionItemNumber());

			ProductBTypeResponse productBTypeResponse = ProductBTypeResponse.builder()
					.productId(product.getProductId())
					.ImgSrc(product.getProductMainImgSrc())
					.productName(product.getProductName())
					.productPrice(product.getProductPrice())
					.soldCount(product.getProductSoldCount())
					.build();
			productBTypeResponses.add(productBTypeResponse);
		}

		productBTypeResponsesResult.setBTypeResponses(productBTypeResponses);

		return productBTypeResponsesResult;
	}

	@Override
	public ProductCTypeResponses getProductCTypeItems(ProductDataRequestDto productDataRequestDto, Long serId){
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(productDataRequestDto.getItemsId()).get();
		ProductCTypeResponses productCTypeResponseResult = new ProductCTypeResponses();
		List<ProductCTypeResponse> productCTypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);
		for (ExhibitionItem item : items) {
			ProductVo product = productServiceClient.findProductByProductId(item.getExhibitionItemNumber());
			SellerClientSellerDetailResponse sellerClientSellerDetailResponse = memberServiceClient.findBySellerId(product.getSellerId());

			ProductCTypeResponse productCTypeResponse = ProductCTypeResponse.builder()
					.productId(product.getProductId())
					.ImgSrc(product.getProductMainImgSrc())
					.sellerName(sellerClientSellerDetailResponse.getSellerName())
					.productName(product.getProductName())
					.productPrice(product.getProductPrice())
					.soldCount(product.getProductSoldCount())
					.build();
			productCTypeResponses.add(productCTypeResponse);
		}
		productCTypeResponseResult.setProductCTypeResponses(productCTypeResponses);

		return productCTypeResponseResult;
	}

	@Override
	public SnsATypeResponses getSnsATypeItems(SnsDataRequestDto snsDataRequestDto){
		ExhibitionItems exhibitionItems = exhibitionItemsRepository.findById(snsDataRequestDto.getItemsId()).get();
		SnsATypeResponses snsATypeResponsesResult = new SnsATypeResponses();
		List<SnsATypeResponse> snsATypeResponses = new ArrayList<>();

		List<ExhibitionItem> items = exhibitionItemRepository.findExhibitionItemByExhibitionItemsId(exhibitionItems.getExhibitionItemsId());
		Collections.sort(items, exhibitionItemComparator);
		for (ExhibitionItem item : items) {
			Feed feed = feedRepository.findById(item.getExhibitionItemNumber()).get();
			String memberName = "";
			Optional<User> user = userRepository.findById(feed.getMemberId());
			if(user.isPresent()){
				memberName = user.get().getUserName();
			} else{
				Seller seller = sellerRepository.findById(feed.getMemberId()).get();
				memberName = seller.getSellerName();
			}
			String feedImageSrc = feedImageRepository.findByFeed(feed).get(0).getFeedImageSrc();

			SnsATypeResponse snsATypeResponse = SnsATypeResponse.builder()
					.feedId(feed.getFeedId())
					.memberName(memberName)
					.feedImageSrc(feedImageSrc)
					.build();
			snsATypeResponses.add(snsATypeResponse);
		}
		snsATypeResponsesResult.setSnsATypeResponses(snsATypeResponses);

		return snsATypeResponsesResult;
	}

	class ExhibitionItemComparator implements Comparator<ExhibitionItem> {

		@Override
		public int compare(ExhibitionItem exhibitionItem1, ExhibitionItem exhibitionItem2) {
			Integer item1Priority = exhibitionItem1.getExhibitionItemPriority();
			Integer item2Priority = exhibitionItem2.getExhibitionItemPriority();

			if(item1Priority < item2Priority){
				return -1;
			}
			else if (item1Priority > item2Priority){
				return 1;
			}
			else {
				return 0;
			}

		}
	}


}
