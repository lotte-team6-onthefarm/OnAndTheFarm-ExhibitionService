package team6.onandthefarmexhibitionservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import team6.onandthefarmexhibitionservice.entity.ExhibitionCategory;

public interface ExhibitionCategoryRepository extends CrudRepository<ExhibitionCategory, Long> {
	@Override
	Optional<ExhibitionCategory> findById(Long exhibitionCategoryId);
}
