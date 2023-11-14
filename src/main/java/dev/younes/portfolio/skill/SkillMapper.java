package dev.younes.portfolio.skill;

import dev.younes.portfolio.mappers.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper implements IMapper<Skill, SkillDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public SkillMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public SkillDto mapTo(Skill skill) {
        return  modelMapper.map(skill, SkillDto.class);
    }

    @Override
    public Skill mapFrom(SkillDto skillDto) {
        return  modelMapper.map(skillDto, Skill.class);
    }
}
