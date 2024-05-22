package com.example.securitydemo.job;

import java.util.List;
import java.util.Optional;

public interface JobService {

   List<Job> findAll();
   void createJob(Job job);
   Job findById(Long id);
   boolean deleteById(Long id);
   boolean updateById(Long id, Job updateJob);
}

