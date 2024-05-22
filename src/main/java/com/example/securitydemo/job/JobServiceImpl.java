package com.example.securitydemo.job;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

// when you mark it as service than spring boot make it available at run time and it will inject it to the controller
@Service
public class JobServiceImpl implements JobService{

    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }



    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }



    }

    @Override
    public boolean updateById(Long id, Job updateJob) {

        Optional<Job> jobOptional = jobRepository.findById(id);

            if(jobOptional.isPresent()){
                Job job= jobOptional.get();
                job. setTitle(updateJob.getTitle());
                job.setDescription(updateJob.getDescription());
                job.setMinSalary(updateJob.getMinSalary());
                job.setMaxSalary(updateJob.getMaxSalary());
                job.setLocation(updateJob.getLocation());
                jobRepository.save(job);
                return true;

        }
        return false;
    }


}
