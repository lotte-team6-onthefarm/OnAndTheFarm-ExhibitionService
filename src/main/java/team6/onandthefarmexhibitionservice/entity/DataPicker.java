package team6.onandthefarmexhibitionservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(
		name="EXHIBITION_SEQ_GENERATOR",
		sequenceName = "EXHIBITION_SEQ",
		initialValue = 100000, allocationSize = 1
)
public class DataPicker {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "EXHIBITION_SEQ_GENERATOR")
	private Long dataPickerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionCategoryId")
	private ExhibitionCategory exhibitionCategory;

	private String dataPickerName;

	private String dataPickerDetail;

	private boolean dataPickerStatus;

	private String dataPickerCreatedAt;

	private String dataPickerModifiedAt;

	private String dataPickerWriter;
}
