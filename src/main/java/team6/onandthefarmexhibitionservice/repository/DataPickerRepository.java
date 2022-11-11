package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import team6.onandthefarmexhibitionservice.entity.DataPicker;

public interface DataPickerRepository extends CrudRepository<DataPicker, Long> {
	public List<DataPicker> findAllOrderByDataPickerCreatedAt();
}
