package com.ttc.diary.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttc.diary.extension.SpecificationEx;
import com.ttc.diary.model.*;
import com.ttc.diary.model.dto.*;
import com.ttc.diary.model.entity.Diary;
import com.ttc.diary.model.entity.DiaryImage;
import com.ttc.diary.model.entity.Topic;
import com.ttc.diary.model.entity.User;
import com.ttc.diary.model.request.DiaryGridParam;
import com.ttc.diary.model.response.*;
import com.ttc.diary.repository.DiaryImageRepository;
import com.ttc.diary.repository.DiaryRepository;
import com.ttc.diary.service.DiaryService;
import com.ttc.diary.service.UserService;
import com.ttc.diary.util.Constants;
import com.ttc.diary.util.SearchUtil;
import com.ttc.diary.util.StatusCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    private final DiaryImageRepository diaryImageRepository;

    private final UserService userService;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, DiaryImageRepository diaryImageRepository, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.diaryImageRepository = diaryImageRepository;
        this.userService = userService;
    }

    public ResponseEntity<BaseResponse<DiaryDto>> createDiary(DiaryDto dto) {
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        Diary diary = new Diary();
        diary.setOwner(new User(principal.getId()));
        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setFavorite(false);
        diary.setTopics(dto.getTopicIds().stream().map(Topic::new).collect(Collectors.toList()));

        diaryRepository.saveAndFlush(diary);
        dto.setId(diary.getId());
        if (dto.getImageDtos() != null && dto.getImageDtos().size() > 0) {
            List<DiaryImage> images = dto.getImageDtos().stream()
                    .map(s -> new DiaryImage(s.getPath(), diary)).collect(Collectors.toList());
            diaryImageRepository.saveAll(images);
            for (int i = 0; i < images.size(); i++) {
                dto.getImageDtos().get(i).setId(images.get(i).getId());
            }
        }
        return Response.ok(dto);
    }

    @Override
    public ResponseEntity<BaseResponse<Diary>> changeFavoriteStatus(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diary not found!!!"));
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        if (!diary.getOwner().getId().equals(principal.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access to this diary!!!");
        diary.setFavorite(!diary.getFavorite());

        return Response.ok();
    }

    @Override
    public ResponseEntity<BaseResponse<DiaryDetailDto>> getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diary not found!!!"));
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        if (!diary.getOwner().getId().equals(principal.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access to this diary!!!");

        DiaryDetailDto diaryDetailDto = new DiaryDetailDto();

        diaryDetailDto.setId(diary.getId());
        diaryDetailDto.setTopics(diary.getTopics().stream()
                .map(s -> new TopicDto(s.getId(), s.getName()))
                .collect(Collectors.toList()));
        diaryDetailDto.setTitle(diary.getTitle());
        diaryDetailDto.setContent(diary.getContent());
        diaryDetailDto.setFavorite(diary.getFavorite());
        diaryDetailDto.setImages(diaryImageRepository.findAllByDiaryId(id).stream()
                .map(s -> new ImageDto(s.getId(), s.getPath()))
                .collect(Collectors.toList()));
        diaryDetailDto.setCreationTime(diary.getCreationTime());
        diaryDetailDto.setModificationTime(diary.getModificationTime());

        return Response.ok(diaryDetailDto);
    }

    @Override
    public ResponseEntity<BaseResponse<Diary>> delete(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diary not found!!!"));
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        if (!diary.getOwner().getId().equals(principal.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access to this diary!!!");
        List<DiaryImage> images = diaryImageRepository.findAllByDiaryId(id);
        for (DiaryImage image : images) {
            File imageFile = new File(Constants.BASE_URL + image.getPath());
            if (imageFile.exists() && imageFile.isFile()) {
                imageFile.delete();
            }
        }
        diaryImageRepository.deleteInBatch(images);

        diaryRepository.delete(diary);
        return Response.ok(100, "Delete success");
    }

    @Override
    public ResponseEntity<BaseResponse<DiaryDto>> updateDiary(Long id, DiaryDto diaryDto) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diary not exist with id " + id));
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        if (!diary.getOwner().getId().equals(principal.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access to this diary!!!");
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());

        diary.setTopics(diaryDto.getTopicIds().stream().map(Topic::new).collect(Collectors.toList()));

        if (diaryDto.getImageDtos() != null && diaryDto.getImageDtos().size() > 0) {
            List<Long> imageIds = diaryDto.getImageDtos().stream().map(ImageDto::getId).collect(Collectors.toList());
            List<DiaryImage> diaryImages = diaryImageRepository.findAllByDiaryId(id);

            List<DiaryImage> diaryOldImages = diaryImages.stream()
                    .filter(s -> !imageIds.contains(s.getId()))
                    .collect(Collectors.toList());
            diaryImageRepository.deleteInBatch(diaryOldImages);

            List<DiaryImage> diaryNewImages = diaryDto.getImageDtos().stream()
                    .filter(s -> s.getId() == null || (s.getId() != null && s.getId() == 0))
                    .map(s -> new DiaryImage(s.getPath(), diary))
                    .collect(Collectors.toList());
            diaryImageRepository.saveAll(diaryNewImages);
            for (int i = 0; i < diaryNewImages.size(); i++) {
                diaryDto.getImageDtos().get(i).setId(diaryNewImages.get(i).getId());
            }
        }

        return Response.ok(diaryDto);
    }

    @Override
    public ResponseEntity<BaseResponse<GridResult<DiaryInListDto>>> searchWithPaging(String param) {
        UserPrincipal principal = userService.getCurrentAuthenticatedUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user"));
        try {
            DiaryGridParam diaryGridParam = SearchUtil.getGridParam(SearchUtil.decodeParam(param), DiaryGridParam.class);
            Specification<Diary> specification = new SpecificationEx<>(diaryGridParam.getFilters());
            specification = specification.and(Specification.where(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), principal.getId())
            ));
            if (diaryGridParam.getTopics() != null && diaryGridParam.getTopics().size() > 0) {
                specification = specification.and(Specification.where(
                        (root, query, criteriaBuilder) -> {
                            Predicate predicate = criteriaBuilder.conjunction();
                            for (Object topic : diaryGridParam.getTopics().stream().map(Topic::new).collect(Collectors.toList())) {
                                predicate = criteriaBuilder.and(criteriaBuilder.isMember(topic, root.get("topics")));
                            }
                            return predicate;
                        }
                ));
            }
            Pageable pageable = PageRequest.of(diaryGridParam.getPage(), diaryGridParam.getLength());
            Sort sort = SearchUtil.getSort(diaryGridParam.getSorts());
            if (sort != null) {
                pageable = PageRequest.of(diaryGridParam.getPage(), diaryGridParam.getLength(), sort);
            }
            Page<Diary> diaryPage = diaryRepository.findAll(specification, pageable);
            List<Long> diaryIds = diaryPage.stream().map(Diary::getId).collect(Collectors.toList());
            List<DiaryInListDto> dtos = diaryRepository.streamAllByIdIn(diaryIds)
                    .collect(Collectors.groupingBy(s -> s.getId())).values()
                    .stream().map(s -> new DiaryInListDto(
                            s.get(0).getId(),
                            s.get(0).getTitle(),
                            s.get(0).getCreationTime(),
                            s.get(0).getModificationTime(),
                            s.get(0).getFavorite(),
                            s.get(0).getTopics().stream().map(x -> new TopicDto(x.getId(), x.getName())).collect(Collectors.toList())))
                    .collect(Collectors.toList());
            GridResult<DiaryInListDto> gridResult = new GridResult<>(diaryPage.getTotalElements(), dtos.size(), dtos);
            return Response.ok(gridResult);
        } catch (UnsupportedEncodingException e) {
            return Response.badRequest(StatusCodeResponse.UN_SUPPORTED, StatusCodeResponse.getMessage(StatusCodeResponse.UN_SUPPORTED));
        } catch (JsonProcessingException e) {
            return Response.badRequest(StatusCodeResponse.CAN_NOT_PARSE, StatusCodeResponse.getMessage(StatusCodeResponse.CAN_NOT_PARSE));
        }
    }
}
