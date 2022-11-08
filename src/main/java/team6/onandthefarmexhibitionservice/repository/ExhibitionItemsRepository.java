package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import team6.onandthefarmexhibitionservice.entity.ExhibitionItems;

public interface ExhibitionItemsRepository extends CrudRepository<ExhibitionItems, Long> {

    // 구좌 디테일 데이터 수 불러오기
    @Query("select p from ExhibitionItems p where p.exhibitionAccount.exhibitionAccountId =:exhibitionAccountId")
    List<ExhibitionItems> findExhibitionItemsDetail(@Param("exhibitionAccountId") Long exhibitionAccountId);

    @Query("select p from ExhibitionItems p where p.exhibitionAccount.exhibitionAccountId =:exhibitionAccountId")
    List<ExhibitionItems> findExhibitionItemsByExhibitionAccountId(@Param("exhibitionAccountId") Long exhibitionAccountId);

}
