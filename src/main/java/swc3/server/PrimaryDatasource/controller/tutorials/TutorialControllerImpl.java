package swc3.server.PrimaryDatasource.controller.tutorials;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.dto.TutorialDto;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.services.tutorial.TutorialService2;

import java.util.List;
import java.util.stream.Collectors;

//REST controller uses DTO for data exchange with the web client app
//Service layer uses entity class, that is why we need to convert between entity and dto class.
//NOTE: We could also do the conversion in the service layer and thus decouple the controller from the model class.

@RestController
@RequestMapping("/api7/tutorials")
public class TutorialControllerImpl implements TutorialOperations{
	TutorialService2 tutorialService;
	ModelMapper modelMapper; // for entity <--> DTO conversion

	@Autowired //dependency injection via constructor
	public TutorialControllerImpl(TutorialService2 tutorialService){
		this.tutorialService = tutorialService;
		this.modelMapper = new ModelMapper();
	}

	private TutorialDto convertToDto(Tutorial tutorial) {
		return modelMapper.map(tutorial, TutorialDto.class);
	}

	private Tutorial convertToEntity(TutorialDto tutorialDto) {
		return modelMapper.map(tutorialDto, Tutorial.class);
	}

	@Override
	public List<TutorialDto> getAll(String title) {
		//return type List<TutorialDto>
		return tutorialService.getAll(title)
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public TutorialDto getById(long id) {
		return convertToDto(tutorialService.getById(id));
	}

	@Override
	public void create(TutorialDto tutorialDto) {
		tutorialService.create(convertToEntity(tutorialDto));
	}

	@Override
	public void update(long id, TutorialDto tutorialDto) {
		tutorialService.update(id, convertToEntity(tutorialDto));
	}

	@Override
	public void delete(long id) {
		tutorialService.delete(id);
	}

	@Override
	public void deleteAll() {
		tutorialService.deleteAll();
	}

	@Override
	public List<TutorialDto> findByPublished(boolean published) {
		return tutorialService.findByPublished(published)
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
}


