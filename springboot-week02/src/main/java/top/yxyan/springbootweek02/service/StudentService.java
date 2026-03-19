package top.yxyan.springbootweek02.service;

import org.springframework.stereotype.Service;
import top.yxyan.springbootweek02.entity.Phone;
import top.yxyan.springbootweek02.entity.Student;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 严亚轩
 * @description:StudentService
 * @date 2020/4/5
 **/
@Service
public class StudentService {
    private static final Map<Long, Student> STUDENT_DATA = new ConcurrentHashMap<>();

    static {
        Student student1 = Student.builder().id(1001L).name("张三").gender("男").birthday(LocalDate.of(1990, 1,
                1)).phone(Phone.builder().band("iphone").price(9999.99).color("black").build()).build();
        Student student2 = Student.builder().id(1002L).name("李四").gender("女").birthday(LocalDate.of(1991, 2,
                2)).phone(Phone.builder().band("huawei").price(8888.88).color("white").build()).build();
        STUDENT_DATA.put(student1.getId(), student1);
        STUDENT_DATA.put(student2.getId(), student2);
    }
    /**
     * 创建学生
     *
     * @param student 学生对象
    */
    public void createStudent(Student student) {
        STUDENT_DATA.put(student.getId(), student);
    }

    /**
     * 根据id查询学生
     *
     * @param id 学生id
     * @return 学生对象
    */
    public Student getStudentById(Long id) {
        return STUDENT_DATA.get(id);
    }

    /**
     * 根据姓名查询学生
     *
     * @param name 学生姓名
     * @return 学生对象
    */
    public Student getStudentByName(String name) {
        return STUDENT_DATA.values().stream().filter(stu -> stu.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * 获取学生
     *
     * @return 所有学生列表
     */
    public Iterable<Student> getAllStudents() {
        return STUDENT_DATA.values();
    }

    /**
     * 根据id更新学生信息
     *
     * @param id 学生id
     * @param student 学生对象
     */
    public void updateStudentById(Long id, Student student) {
        STUDENT_DATA.put(id, student);
    }

    /**
     * 根据id删除学生
     *
     * @param id 学生id
     */
    public void deleteStudentById(Long id) {
        STUDENT_DATA.remove(id);
    }
}
