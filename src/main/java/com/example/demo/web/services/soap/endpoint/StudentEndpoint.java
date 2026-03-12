package com.example.demo.web.services.soap.endpoint;

import com.example.demo.Exception.StudentException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.kholty.student.AddStudentRequest;
import com.kholty.student.AddStudentResponse;
import com.kholty.student.GetAllStudentsRequest;
import com.kholty.student.GetAllStudentsResponse;
import com.kholty.student.GetStudentRequest;
import com.kholty.student.GetStudentResponse;
import com.kholty.student.StudentType;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint {

public static final String namespace = "http://kholty.com/student";




    // input addStudentRequest

    @Autowired
    private StudentRepository Studentrepo;
    @Transactional
    @PayloadRoot(namespace = namespace, localPart = "addStudentRequest")
    @ResponsePayload
    public AddStudentResponse addStudent(@RequestPayload AddStudentRequest request) {
        Student student =new Student();
         student.setName(request.getName());
         student.setEmail(request.getEmail());
         Studentrepo.save(student);
        StudentType st=new StudentType();
        st.setId(student.getId());
        st.setName(student.getName());
        st.setEmail(student.getEmail());
        AddStudentResponse response=new AddStudentResponse();
        response.setStudent(st);
        return response;
    }


@PayloadRoot(namespace = namespace, localPart = "getStudentRequest")
@ResponsePayload
    public GetStudentResponse getStudentById(@RequestPayload GetStudentRequest request) throws StudentException{
    Optional <Student> student= Studentrepo.findById(request.getId());
    Student st=new Student();
    if(student.isPresent()){
        st=student.get();
    }else {
        throw new StudentException("student with id ( "+request.getId()+" ) not found!");
    }
    StudentType stf=new StudentType();
    stf.setId(st.getId());
    stf.setName(st.getName());
    stf.setEmail(st.getEmail());
        GetStudentResponse response =new GetStudentResponse();
        response.setStudent(stf);
        return response;
    }


    @PayloadRoot(namespace = namespace,localPart = "getAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getAllStudent(@RequestPayload GetAllStudentsRequest request)throws StudentException{

        GetAllStudentsResponse response= new GetAllStudentsResponse();
        List<Student> student=Studentrepo.findAll();

if(student.isEmpty()){

     throw new StudentException("students are not exist yet!");
}else{
        for( Student model:student){

            StudentType st=new StudentType();
            st.setId(model.getId());
            st.setName(model.getName());
            st.setEmail(model.getEmail());

            response.getStudent().add(st);


        }


        



    
    }

    return response;

    }


}