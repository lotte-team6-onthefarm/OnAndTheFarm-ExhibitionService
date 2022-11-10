package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import team6.onandthefarmexhibitionservice.entity.ExhibitionTemporary;

public interface ExhibitionTemporaryRepository extends CrudRepository<ExhibitionTemporary, Long> {
	List<ExhibitionTemporary> findAll(Sort sort);
}
