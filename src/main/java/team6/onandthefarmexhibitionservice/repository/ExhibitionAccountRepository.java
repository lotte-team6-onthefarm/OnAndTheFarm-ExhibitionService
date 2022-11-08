package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import team6.onandthefarmexhibitionservice.entity.ExhibitionAccount;

public interface ExhibitionAccountRepository extends CrudRepository<ExhibitionAccount, Long> {
	@Query("select e from ExhibitionAccount e join fetch e.exhibitionCategory where e.exhibitionCategory.exhibitionCategoryId =:exhibitionCategoryId and e.exhibitionAccountStatus = true")
	List<ExhibitionAccount> findByExhibitionCategoryId(@Param("exhibitionCategoryId") Long exhibitionCategoryId);


	@Query("select e from ExhibitionAccount e where e.exhibitionAccountId =:exhibitionAccountId")	// 구좌 Id로 구좌 정보 불러오기
	ExhibitionAccount findByExhibitionAccountId(@Param("exhibitionAccountId") Long exhibitionAccountId);
}
