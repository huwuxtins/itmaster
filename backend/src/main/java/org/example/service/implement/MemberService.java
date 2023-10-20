package org.example.service.implement;

import org.example.model.Member;
import org.example.model.Project;
import org.example.repository.MemberRepository;
import org.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService extends BaseService<Member, MemberRepository> {
    @Autowired
    ProjectRepository projectRepository;
    public Member update(UUID memberId, Member newMember) throws NoSuchElementException {
        Optional<Member> member = repository.findById(memberId);
        if(member.isPresent()){
            newMember.setId(memberId);
            Set<Project> projectIdSet = newMember.getProjects();
            Set<Project> projects = new HashSet<>();
            for (Project projectId : projectIdSet) {
                Optional<Project> projectOptional = projectRepository.findById(projectId.getId());
                if(projectOptional.isPresent()) {
                    Project project = projectOptional.get();
                    projects.add(project);
                } else {
                    throw new NoSuchElementException("Project id: " + projectId.getId() + "is not exist");
                }
            }
            newMember.setProjects(projects);
        } else {
            throw new NoSuchElementException("Can't find member with id: " + memberId.toString());
        }
        return repository.save(newMember);
    }
}
