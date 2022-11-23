package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import team6.onandthefarmexhibitionservice.entity.ExhibitionTemporary;

public interface ExhibitionTemporaryRepository extends CrudRepository<ExhibitionTemporary, Long> {
	List<ExhibitionTemporary> findAll(Sort sort);

	@Query("select e from ExhibitionTemporary e where e.exhibitionTemporaryStartTime =:time order by 'exhibitionTemporaryPriority' DESC ")
	List<ExhibitionTemporary> findExhibitionTemporariesBy(@Param("time") String time);

	List<ExhibitionTemporary> getExhibitionTemporaryByExhibitionTemporaryStartTimeIsBetween(String startTime, String endTime);

}
