package team6.onandthefarmexhibitionservice.entity.item;

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
import team6.onandthefarmexhibitionservice.entity.ExhibitionAccount;

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
public class ExhibitionItems {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "EXHIBITION_SEQ_GENERATOR")
	private Long exhibitionItemsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionAccountId")
	private ExhibitionAccount exhibitionAccount;

	private String exhibitionItemsName;

	private String exhibitionItemsDetail;

	private String exhibitionItemsStartTime;

	private String exhibitionItemsEndTime;

}
