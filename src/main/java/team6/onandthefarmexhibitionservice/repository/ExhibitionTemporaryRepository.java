package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import team6.onandthefarmexhibitionservice.entity.ExhibitionTemporary;

public interface ExhibitionTemporaryRepository extends CrudRepository<ExhibitionTemporary, Long> {
	List<ExhibitionTemporary> findAll(Sort sort);

	@Query("select e from ExhibitionTemporary e where e.exhibitionTemporaryStartTime =:time order by 'exhibitionTemporaryPriority' ASC")
	List<ExhibitionTemporary> findExhibitionTemporariesBy(Long time);

}
