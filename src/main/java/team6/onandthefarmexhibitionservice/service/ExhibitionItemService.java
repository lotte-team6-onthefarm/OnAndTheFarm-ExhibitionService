package team6.onandthefarmexhibitionservice.service;

import java.io.IOException;

import team6.onandthefarmexhibitionservice.dto.item.BadgeFormRequestDto;
import team6.onandthefarmexhibitionservice.dto.item.BannerFormRequestDto;

public interface ExhibitionItemService {
	Long createBanner(BannerFormRequestDto bannerFormRequestDto) throws IOException;
	Long createBadge(BadgeFormRequestDto badgeFormRequestDto)throws IOException;
}
