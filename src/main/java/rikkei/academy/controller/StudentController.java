package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Student;
import rikkei.academy.service.IStudentService;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/student"})
public class StudentController {
    @Autowired
    IStudentService studentService;

    @GetMapping
    public String showListStudent(Model model) {
        List<Student> studentList = studentService.findAll();
        model.addAttribute("studentList", studentList);
        return "student/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        System.out.println("id--->" + id);
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student/detail";
    }

    @GetMapping("/create")
    public String showFormCreate() {
        return "student/create";
    }

    @PostMapping("/create")
    public String actionCreate(@RequestParam(name = "name") String name,
                               @RequestParam(name = "address") String address,
                               @RequestParam(name = "age") int age) {
        long id = 0;
        if (studentService.findAll().size() == 0) {
            id = 1;
        } else {
            id = studentService.findAll().get(studentService.findAll().size() - 1).getId() + 1;
        }
        Student student = new Student(id, name, address, age);
        studentService.save(student);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable Long id,Model model){
        System.out.println("id--->"+ id);
       Student student= studentService.findById(id);
       model.addAttribute("student", student);
        return "student/edit";
    }
    @PostMapping("/edit/{id}")
    public String actionEdit(@PathVariable Long id,
                             @RequestParam(name="name") String name,
                             @RequestParam(name="address") String address,
                             @RequestParam(name= "age") int age){
        Student student = new Student(id, name, address, age);
        studentService.save(student);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String showFormDelete(@PathVariable Long id,Model model){
        Student student = studentService.findById(id);
        model.addAttribute(student);
        return "student/delete";
    }
    @PostMapping("/delete/{id}")
    public String actionDelete(@PathVariable Long id){
        studentService.deleteById(id);
        return "redirect:/";
    }
}
