package dev.younes.portfolio.skill;

import dev.younes.portfolio.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getListOfSkills(){
        return this.skillRepository.findAll();
    }
    public List<Skill> getListOfSkills(List<Long> skillsIds){
        return this.skillRepository.findAllById(skillsIds);
    }

    public Skill getSkillById(Long id){
        return this.skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find this skill")) ;
    }

    public Optional<Skill> getSkillByName(String name){
        return this.skillRepository.getSkillByName(name);
    }


    public Skill saveSkill( Skill skill ){
        try{

            log.info("Saving skills");

            if(skill.getName() == null) return null;
            Optional<Skill> optionalSkill =getSkillByName(skill.getName());
            if(optionalSkill.isPresent()){
                return optionalSkill.get();
            }

        }catch (Exception e){
            log.error("Error while saving : {}", e.getMessage());
        }
        return this.skillRepository.save(skill);
    }

    @Transactional
    public Skill updateSkill( Long id, Skill skillData ){
        if(skillData.getName() == null) return null;

        Skill skill = this.getSkillById(id);
        skill.setUsers(null);
        skill.setName(skillData.getName());

        return skill;

    }

    public void deleteSkill(Long id){
        Skill skill = this.getSkillById(id);
        this.skillRepository.delete(skill);
    }




}

