package team6.onandthefarmexhibitionservice.util;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team6.onandthefarmexhibitionservice.entity.Exhibition;
import team6.onandthefarmexhibitionservice.entity.ExhibitionTemporary;
import team6.onandthefarmexhibitionservice.repository.ExhibitionCategoryRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionRepository;
import team6.onandthefarmexhibitionservice.repository.ExhibitionTemporaryRepository;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class SchedulingUtil {

    private final ExhibitionTemporaryRepository exhibitionTemporaryRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionCategoryRepository exhibitionCategoryRepository;
    private DateUtils dateUtils;
    private Environment env;

    public SchedulingUtil(ExhibitionTemporaryRepository exhibitionTemporaryRepository,
                          ExhibitionRepository exhibitionRepository,
                          ExhibitionCategoryRepository exhibitionCategoryRepository,
                          DateUtils dateUtils,
                          Environment env){
        this.exhibitionTemporaryRepository = exhibitionTemporaryRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitionCategoryRepository = exhibitionCategoryRepository;
        this.dateUtils = dateUtils;
        this.env = env;

    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void updateExhibition () {

        //exhibitionTemp에서 해당 시간 데이터를 불러옴
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

        cal.setTime(date);
        String startTime = timeFormat.format(cal.getTime())+":00";
        String endTime = timeFormat.format(cal.getTime())+":59";

        List<ExhibitionTemporary> exhibitionTemporaryList = exhibitionTemporaryRepository.getExhibitionTemporaryByExhibitionTemporaryStartTimeIsBetween(startTime, endTime);

        if(!exhibitionTemporaryList.isEmpty()) {
            //exhibition에 이전 데이터 false로 변경
            List<Exhibition> exhibitionList = exhibitionRepository.getTrueExhibitions();
            System.out.println("!!!!!!!!!!!!!!!!!!!!exhibition : "+exhibitionList.size());
            for (Exhibition exhibition : exhibitionList) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!id : "+exhibition.getExhibitionId());
                Exhibition savedExhibition = exhibitionRepository.findById(exhibition.getExhibitionId()).get();
                savedExhibition.setExhibitionActivation(false);
                savedExhibition.setExhibitionStatus(false);
                savedExhibition.setExhibitionTemporaryModifiedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
            }

            //exhibition에 exhibitionTemp 데이터 넣기
            for (ExhibitionTemporary exhibitionTemporary : exhibitionTemporaryList) {
                Exhibition exhibition = Exhibition.builder()
                        .exhibitionAccountId(exhibitionTemporary.getExhibitionTemporaryAccountId())
                        .exhibitionActivation(true)
                        .exhibitionStatus(true)
                        .exhibitionDataPickerId(exhibitionTemporary.getExhibitionTemporaryDataPicker())
                        .exhibitionItemsId(exhibitionTemporary.getExhibitionTemporaryItemsId())
                        .exhibitionModuleName(exhibitionTemporary.getExhibitionTemporaryModuleName())
                        .exhibitionPriority(exhibitionTemporary.getExhibitionTemporaryPriority())
                        .exhibitionCategory(exhibitionCategoryRepository.findById(exhibitionTemporary.getExhibitionTemporaryCategory().getExhibitionCategoryId()).get())
                        .build();
                exhibition.setExhibitionTemporaryCreatedAt(dateUtils.transDate(env.getProperty("dateutils.format")));
                exhibitionRepository.save(exhibition);
            }
        }

    }

}
