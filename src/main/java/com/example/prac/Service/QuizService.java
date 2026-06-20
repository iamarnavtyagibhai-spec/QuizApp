package com.example.prac.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.prac.dto.CreateQuizRequest;
import com.example.prac.model.Question;
import com.example.prac.model.Quiz;
import com.example.prac.model.User;
import com.example.prac.repository.QuizRepository;
import com.example.prac.repository.UserRepository;
import java.util.UUID;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizrepository;
    @Autowired
    UserRepository userrepo;
    

    public Quiz createQuiz(CreateQuizRequest request) {
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();

        Quiz quiz = new Quiz();

        quiz.setOwneremail(auth.getName());

        quiz.setQuizname(request.getQuizname());
        quiz.setDescription(request.getDescription());
        quiz.setStarttime(request.getStarttime());
        quiz.setEndtime(request.getEndtime());
        quiz.setPassword(request.getPassword());
         quiz.setAdmin(new HashSet<>());
        quiz.setQuestions(new ArrayList<>());

        return quizrepository.save(quiz);
    }
    public Quiz addAdmin(String id,String email){
        Optional<User> e= userrepo.findByEmail(email);
         Quiz q = quizrepository.findById(id).orElseThrow();
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!q.getOwneremail().equals(auth.getName())){
        throw new RuntimeException("Only owner can add admin");
}
        if (e.isEmpty()){
            System.out.println("user not found");
            throw new RuntimeException("user not found");
        }
        q.getAdmin().add(email);
        quizrepository.save(q);
        return q;
       

    }
    public Quiz addQuestion(String id,Question que){
         Authentication auth =
        SecurityContextHolder.getContext()
                             .getAuthentication();

    String cur= auth.getName();
    que.setCreatedBy(cur);
     Quiz a = quizrepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
     if (!a.getAdmin().contains(cur) && !a.getOwneremail().equals(cur)){
        throw new RuntimeException("Not authorized to this quiz");

    }
     que.setId(UUID.randomUUID().toString());

     
           a.getQuestions().add(que);




          return  quizrepository.save(a);

    }
    public void deleteQuiz(String id) {

        

    Quiz quiz = quizrepository.findById(id)
            .orElseThrow(() -> new RuntimeException("you are not allowed"));
              Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!quiz.getOwneremail().equals(auth.getName())){
        throw new RuntimeException("Only owner can add admin");
}

    quizrepository.delete(quiz);
}
public Quiz deleteQuestion(String quizId, String questionId) {


    Quiz quiz = quizrepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("cannot delete quiz"));
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!quiz.getOwneremail().equals(auth.getName())){
        throw new RuntimeException("Only owner can add admin");
}

    quiz.getQuestions()
            .removeIf(q -> q.getId().equals(questionId));

    return quizrepository.save(quiz);
}
public Quiz updateQuiz(CreateQuizRequest req,String id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = authentication.getName();
    Quiz a = quizrepository.findById(id).orElseThrow(() -> new RuntimeException("You are not autorize"));
    if (!a.getAdmin().contains(currentEmail) && !a.getOwneremail().equals(currentEmail)){
        throw new RuntimeException("Not authorized to this quiz");
    }
    a.setQuizname(req.getQuizname());
    a.setDescription(req.getDescription());
    a.setStarttime(req.getStarttime());
    a.setEndtime(req.getEndtime());
    return quizrepository.save(a);

}








}