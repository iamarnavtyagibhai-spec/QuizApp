package com.example.prac.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.prac.dto.AnswerRequest;
import com.example.prac.dto.CreateQuizRequest;
import com.example.prac.dto.LeaderboardResponse;
import com.example.prac.dto.QuestionResponse;
import com.example.prac.dto.QuizResultResponse;
import com.example.prac.dto.SubmitQuizRequest;
import com.example.prac.model.Question;
import com.example.prac.model.Quiz;
import com.example.prac.model.QuizAttempt;
import com.example.prac.model.User;
import com.example.prac.repository.QuizAttemptRepository;
import com.example.prac.repository.QuizRepository;
import com.example.prac.repository.UserRepository;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizrepository;
    @Autowired
    UserRepository userrepo;
    @Autowired
    QuizAttemptRepository quizAttemptrepository;
    

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
public List<QuestionResponse> attemptQuiz(String id,String password){
    Quiz a = quizrepository.findById(id).orElseThrow(() -> new RuntimeException("didnt get quiz"));
     if (!a.getPassword().equals(password)) {
        throw new ResponseStatusException(
    HttpStatus.BAD_REQUEST,
    "Password is wrong"
);
    }
     
     List<QuestionResponse> q= new ArrayList<>();
     for (Question question : a.getQuestions()){
        QuestionResponse dto=new QuestionResponse();
        dto.setId(question.getId());
        dto.setQuestiontext(question.getQuestiontext());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setMarks(question.getMarks());
        q.add(dto);
        

        



     }
     return q;
     



}
public QuizResultResponse submitQuiz(String quizId,SubmitQuizRequest request){
     Quiz quiz = quizrepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Map<String, Question> mapp = new HashMap<>();
        List<Question> q=quiz.getQuestions();
        for (Question que:q ){
        mapp.put(que.getId(),que);



}
 int score = 0;
int correctAnswers = 0;
int wrongAnswers = 0;
int unattempt=0;
int len=0;

    for (AnswerRequest answer : request.getAnswers()) {
        Question a =mapp.get(answer.getQuestionId());
        len=len+1;
        if (answer.getQuestionId()==null || a==null){
            continue;
        }
        if (answer.getSelectedOption()==null){
            unattempt+=1;
        }
        else{
            if (a.getCorrectoption()==answer.getSelectedOption()){
                correctAnswers+=1;
                score+=a.getMarks();


            }
            else{
                wrongAnswers+=1;
            }

        }
       

    }
      QuizResultResponse res=new QuizResultResponse();
        res.setScore(score);
        res.setTotalQuestions(quiz.getQuestions().size());
        res.setUnattempt(unattempt);
        res.setWrongAnswers(wrongAnswers);
        res.setCorrectAnswers(correctAnswers);
        res.setScore(score);
        String email = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getName();
        QuizAttempt qat=new QuizAttempt();
        qat.setQuizId(quizId);
        qat.setAttemptedAt(LocalDateTime.now());
        qat.setCorrectAnswers(correctAnswers);
        qat.setUnattempt(unattempt);
        qat.setScore(score);
        qat.setTotalQuestions(quiz.getQuestions().size());
        qat.setWrongAnswers(wrongAnswers);
        qat.setUserEmail(email);
        quizAttemptrepository.save(qat);



        

return res;


}
public void duplicateAttempt(String quizId){
    String email =SecurityContextHolder.getContext().getAuthentication().getName();
    if (quizAttemptrepository.findByUserEmailandQuizId(email,quizId)!=null){
        throw new RuntimeException("You have already attempted this quiz");
    }
   

}
public List<LeaderboardResponse> leaderboard(String quizId){
    List<QuizAttempt> que=quizAttemptrepository.findByQuizIdOrderByScoreDescAttemptedAtAsc(quizId);
    List<LeaderboardResponse> ans=new ArrayList<>();
    int i=0;
    for (QuizAttempt at:que){
        LeaderboardResponse re=new LeaderboardResponse();
        i=i+1;
        re.setAttemptedAt(at.getAttemptedAt());
        re.setRank(i);
        re.setScore(at.getScore());
        re.setUserEmail(at.getUserEmail());
        ans.add(re);
    }
    return ans;
    }
    public Quiz getQuiz(String quizId) {

    return quizrepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
}
}









}