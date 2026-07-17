package com.example.prac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prac.Service.QuizService;
import com.example.prac.dto.CreateQuizRequest;
import com.example.prac.dto.LeaderboardResponse;
import com.example.prac.dto.QuizResultResponse;
import com.example.prac.dto.SubmitQuizRequest;
import com.example.prac.model.Question;
import com.example.prac.model.Quiz;
import com.example.prac.repository.QuizRepository;



@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizservice;
    @Autowired
    QuizRepository repo;

   @GetMapping("/my")
public List<Quiz> getMyQuizzes() {
        String email = SecurityContextHolder
            .getContext()
            .getAuthentication().getName();

   

    return quizservice.getMyQuizzes(email);
}


    @PostMapping("/create")
    public Quiz Create(@RequestBody CreateQuizRequest req) {
        return quizservice.createQuiz(req);
        
        
        
    }
    @PostMapping("/{id}/admin")
    public Quiz addingAdmin(@PathVariable String id,@RequestParam String email) {
        return  quizservice.addAdmin(id,email);
    
        
    }
    @PostMapping("/{id}/que")
    public Quiz Questionading(@PathVariable String id,@RequestBody Question que) {
        
        
        return quizservice.addQuestion(id,que);
    }
    
    
    @DeleteMapping("/{id}")
    public String deleteQuiz(@PathVariable String id) {

    quizservice.deleteQuiz(id);

    return "Quiz Deleted Successfully";
}
@DeleteMapping("/{quizId}/question/{questionId}")
public Quiz deleteQuestion(
        @PathVariable String quizId,
        @PathVariable String questionId) {

    return quizservice.deleteQuestion(quizId, questionId);
}
   @PatchMapping("/{id}")
public Quiz updateQuiz(
        @PathVariable String id,
        @RequestBody CreateQuizRequest req) {

    return quizservice.updateQuiz(req, id);
}
@GetMapping("/all")
public List<Quiz> findAlldata(){
    List<Quiz> a = repo.findAll();
    return a;
}
@GetMapping("/search/{name}")
public List<Quiz> searchQuiz(@PathVariable String name) {
    return repo.findByQuiznameContainingIgnoreCase(name);
}
@GetMapping("/{id}")
public Quiz searchQuizById(@PathVariable String id) {
    return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
}
@PostMapping("/submit/{quizId}")
public QuizResultResponse submitQuiz(
        @PathVariable String quizId,
        @RequestBody SubmitQuizRequest request) {

    return quizservice.submitQuiz(quizId, request);
}
@GetMapping("/start/{quizId}")
public Quiz startQuiz(@PathVariable String quizId) {

    quizservice.duplicateAttempt(quizId);

    return quizservice.getQuiz(quizId);
}
@GetMapping("/leaderboard/{quizId}")
public List<LeaderboardResponse> leaderboard(
        @PathVariable String quizId) {

    return quizservice.leaderboard(quizId);
}
@GetMapping("/my/search")
public List<Quiz> searchMyQuiz(@RequestParam String keyword) {
     String email = SecurityContextHolder
            .getContext()
            .getAuthentication().getName();
    return quizservice.searchMyQuiz(email,keyword);
}


}
