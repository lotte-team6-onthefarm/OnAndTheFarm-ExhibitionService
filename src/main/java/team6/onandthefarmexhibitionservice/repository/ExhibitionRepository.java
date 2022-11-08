package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import team6.onandthefarmexhibitionservice.entity.Exhibition;

public interface ExhibitionRepository extends CrudRepository<Exhibition, Long> {

	@Query("select e from Exhibition e where e.exhibitionStatus = true")
	List<Exhibition> getTrueExhibitions();
}
