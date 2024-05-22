package com.example.securitydemo.review;

import com.example.securitydemo.company.Company;
import com.example.securitydemo.company.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements  ReviewService{

    private ReviewRepository reviewRepository;
    private CompanyRepository companyRepository;
    ReviewServiceImpl(ReviewRepository reviewRepository, CompanyRepository companyRepository){
        this.reviewRepository= reviewRepository;
        this.companyRepository= companyRepository;
    }

    @Override
    public List<Review> getAllReview(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean saveReview(Long companyId, Review review) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company != null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }else{
            return false;
        }

    }
}
