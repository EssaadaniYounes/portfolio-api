package dev.younes.portfolio.skill;

import dev.younes.portfolio.exceptions.NotFoundException;
import dev.younes.portfolio.records.ResponseRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/skills")
@Slf4j
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseRecord<List<Skill>>> getListOfSkills(){
        List<Skill> skills = this.skillService.getListOfSkills();
        ResponseRecord<List<Skill>> response = new ResponseRecord<>(skills);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseRecord<SkillDto>> saveSkill(
            @RequestBody SkillDto skillDto
    ){
        log.info("Saving a skill from the controller");
       Skill skill = this.skillMapper.mapFrom(skillDto);
       Skill saved = this.skillService.saveSkill(skill);

       ResponseRecord<SkillDto> response;

       if( saved == null  ){
           response = new ResponseRecord<>(
                                        null,
                                       false,
                                       "Couldn't save this skill with this name"
           );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
       }

       response = new ResponseRecord<>(
               skillMapper.mapTo(saved),
               true,
               "Skill saved!"
       );

       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRecord<SkillDto>> getSkillById(
            @PathVariable("id") Long id
    ){
        try{
            Skill skill = this.skillService.getSkillById(id);

            SkillDto skillDto = skillMapper.mapTo(skill);

            ResponseRecord<SkillDto> response = new ResponseRecord<>(skillDto);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (NotFoundException exception){

            ResponseRecord<SkillDto> response = new ResponseRecord<>(
                    null,
                    false,
                    exception.getMessage());

            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRecord<SkillDto>> updateSkill(
            @PathVariable("id") Long id,
            @RequestBody SkillDto skillDto
    ){
        try {

            Skill skill = skillMapper.mapFrom(skillDto);
            Skill updatedSkill = this.skillService.updateSkill(id, skill);
            ResponseRecord<SkillDto> response;

            if( updatedSkill == null  ){
                response = new ResponseRecord<>(
                        null,
                        false,
                        "Couldn't update this skill "
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
            }

            SkillDto skillDtoUpdated = skillMapper.mapTo(updatedSkill);

            response = new ResponseRecord<>(skillDtoUpdated);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (NotFoundException exception){
            ResponseRecord<SkillDto> response = new ResponseRecord<>(null, false, exception.getMessage());
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSkill(
            @PathVariable("id") Long id
    ){
        try {
            this.skillService.deleteSkill(id);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        }catch (NotFoundException exception){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }

}
