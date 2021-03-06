package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repository.BacklogRepository;
import io.agileintelligence.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try{
            // Uniform the identifier to Upper case
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier((project.getProjectIdentifier().toUpperCase())));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("ProjectId " + project.getProjectIdentifier().toUpperCase()+" already exists!");
        }
    }

    public  Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null) {
            throw new ProjectIdException("Project "+ projectId + " doesn't exist!");
        }

        return project;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null) {
            throw new ProjectIdException("Project "+ projectId + " doesn't exist! Can't not delete the project");
        }
        projectRepository.delete(project);
    }



}
