package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import team6.onandthefarmexhibitionservice.entity.ExhibitionItem;

public interface ExhibitionItemRepository extends CrudRepository<ExhibitionItem, Long> {    // 소재 데이터 수 불러오기
    @Query("select p from ExhibitionItem p where p.exhibitionItems.exhibitionItemsId =:exhibitionItemsId")
    List<ExhibitionItem> findExhibitionItemDetail(@Param("exhibitionItemsId") Long exhibitionItemsId);

    @Query("select e from ExhibitionItem e where e.exhibitionItems.exhibitionItemsId =:exhibitionItemsId")
    List<ExhibitionItem> findExhibitionItemByExhibitionItemsId(@Param("exhibitionItemsId") Long exhibitionItemsId);

}
