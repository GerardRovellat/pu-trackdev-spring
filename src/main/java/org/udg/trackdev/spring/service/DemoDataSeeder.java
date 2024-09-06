package org.udg.trackdev.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.dto.request.sprints.SprintRequestDTO;
import org.udg.trackdev.spring.dto.request.tasks.MergePatchTaskDTO;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.entity.enums.SprintStatus;
import org.udg.trackdev.spring.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * The type Demo data seeder.
 */
@Component
public class DemoDataSeeder {

    private final Logger logger = LoggerFactory.getLogger(Global.class);
    private final List<Integer> possibleEstimationPoints = Arrays.asList(1, 2, 3, 5, 8, 13, 20, 40, 100);

    /**
     * The Global.
     */
    @Autowired
    Global global;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    /**
     * Seed demo data.
     */
    public void seedDemoData() {
        logger.info("Starting populating database ...");
        // users
        List<User> enrolledStudents = createDemoStudents();
        User nacho = userService.addUserInternal("Ignacio Martín", "ignacio.martin@udg.edu ", global.getPasswordEncoder().encode("N123123n"), List.of(UserType.ADMIN, UserType.PROFESSOR));
        User gerard = userService.addUserInternal("Gerard Rovellat", "gerard.rovellat@gmail.com", global.getPasswordEncoder().encode("G123123r"), List.of(UserType.STUDENT));
        User marc = userService.addUserInternal("Marc Got", "gotcritgmarc@gmail.com", global.getPasswordEncoder().encode("M123123g"), List.of(UserType.STUDENT));
        User admin = userService.addUserInternal("TrackDev Administrator", "admin@trackdev.com", global.getPasswordEncoder().encode("admin"), List.of(UserType.ADMIN, UserType.PROFESSOR));

        User student1 = userService.addUserInternal("Steve Jobs", "student1@trackdev.com", global.getPasswordEncoder().encode("1111"), List.of(UserType.STUDENT));
        User student2 = userService.addUserInternal("Mark Zuckerberg", "student2@trackdev.com", global.getPasswordEncoder().encode("2222"), List.of(UserType.STUDENT));
        User student3 = userService.addUserInternal("Jeff Bezos", "student3@trackdev.com", global.getPasswordEncoder().encode("3333"), List.of(UserType.STUDENT));
        User student4 = userService.addUserInternal("Elon Musk", "student4@trackdev.com", global.getPasswordEncoder().encode("4444"), List.of(UserType.STUDENT));

        //DEMO PFG
        User demo1 = userService.addUserInternal("Bill Gates", "demo1@trackdev.com", global.getPasswordEncoder().encode("D123123d"), List.of(UserType.STUDENT));
        User demo2 = userService.addUserInternal("Alan Turing", "demo2@trackdev.com", global.getPasswordEncoder().encode("D123123d"), List.of(UserType.STUDENT));
        User demo3 = userService.addUserInternal("Michael Sipser", "demo3@trackdev.com", global.getPasswordEncoder().encode("D123123d"), List.of(UserType.STUDENT));
        User demo4 = userService.addUserInternal("James Gosling", "demo4@trackdev.com", global.getPasswordEncoder().encode("D123123d"), List.of(UserType.STUDENT));

        enrolledStudents.add(student1);
        enrolledStudents.add(student2);
        enrolledStudents.add(student3);
        enrolledStudents.add(demo1);

        // Subject
        Subject subject = subjectService.createSubject("PDS2024","PDS" ,admin.getId());
        Subject subject1 = subjectService.createSubject("Projecte web","PW" ,admin.getId());
        Subject subject2 = subjectService.createSubject("Sistema de Computadors I","SO1" ,admin.getId());
        Subject subject3 = subjectService.createSubject("Sistema de Computadors II","SO2" ,admin.getId());
        Subject subject4 = subjectService.createSubject("Projectes final de grau","PFG" ,admin.getId());

        // Course
        Course course = courseService.createCourse(subject.getId(), 2024,null, admin.getId());
        Course course4 = courseService.createCourse(subject.getId(), 2025,null, admin.getId());
        Course course1 = courseService.createCourse(subject1.getId(), 2026,null, admin.getId());
        Course course2 = courseService.createCourse(subject2.getId(), 2027,null, admin.getId());
        Course course3 = courseService.createCourse(subject3.getId(), 2028,null, admin.getId());

        // one subject set up
        populateProject(admin, course, "Movie reviews", enrolledStudents.subList(0,12));
        populateProject(admin, course, "Calendar", enrolledStudents.subList(12,22));
        populateProject(admin, course, "FaceNotes", enrolledStudents.subList(22,26));
        populateProject(admin, course, "Filmer", enrolledStudents.subList(22,26));

        //DEMO PFG
        Course course5 = courseService.createCourse(subject4.getId(), 2023,null, admin.getId());
        populateProjectDemo(
                admin,
                course5,
                "Gestió de projectes en metodologia Agile",
                new ArrayList<>(Arrays.asList(nacho, gerard, marc, demo1, demo2, demo3, demo4))
        );

        logger.info("Done populating database");
    }

    private void populateProjectDemo(User admin, Course course, String projectName, List<User> users){
        List<String> emails = new ArrayList<>();
        for(User user: users) {
            emails.add(user.getEmail());
        }
        Project project = projectService.createProject(projectName, emails, course.getId(), admin.getId());
        //Sprint 1
        LocalDate start1 = LocalDate.of(2024,1,2);
        LocalDate end1 = start1.plusDays(14);
        User sprintCreator1 = users.get(1);
        Sprint sprint1 = sprintService.create(project, "Sprint 1", Date.from(start1.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(end1.atStartOfDay(ZoneId.systemDefault()).toInstant()), sprintCreator1.getId());
        //Sprint 2
        LocalDate start2 = LocalDate.of(2024,1,29);
        LocalDate end2 = start2.plusDays(14);
        User sprintCreator2 = users.get(2);
        Sprint sprint2 = sprintService.create(project, "Sprint 2", Date.from(start2.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(end2.atStartOfDay(ZoneId.systemDefault()).toInstant()), sprintCreator2.getId());
        //Sprint 3
        LocalDate start3 = LocalDate.of(2024,3,15);
        LocalDate end3 = start3.plusDays(7);
        User sprintCreator3 = users.get(6);
        Sprint sprint3 = sprintService.create(project, "Sprint 3", Date.from(start3.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(end3.atStartOfDay(ZoneId.systemDefault()).toInstant()), sprintCreator3.getId());
        //Task1
        Task task1 = taskService.createTask(project.getId(), "Anàlisis i disseny de la base de dades", users.get(2).getId());
        MergePatchTaskDTO editTask1 = new MergePatchTaskDTO();
        editTask1.setName(Optional.of("S'ha de fer una anàlisi i disseny de la base de dades pel desenvolupament del projecte final de grau. S'ha de fer un diagrama entitat-relació i un diagrama de classes."));
        editTask1.setAssignee(Optional.of(users.get(2).getEmail()));
        editTask1.setEstimationPoints(Optional.of(6));
        editTask1.setStatus(Optional.of(TaskStatus.DONE));
        editTask1.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        taskService.editTask(task1.getId(), editTask1, users.get(2).getId());
        commentService.addComment( "Algú sap si necessitarem crear un xat?", users.get(2),task1);
        commentService.addComment( "No ho tinc clar, és un stopper? ", users.get(3),task1);
        commentService.addComment( "Sí, no sé si haig de crear l'objecte \"comentari\"", users.get(2),task1);
        commentService.addComment( "Jo crec que sí que el crearem, fem-ho", users.get(4),task1);
        commentService.addComment( "Agree", users.get(5),task1);
        commentService.addComment( "Em sembla bé també", users.get(6),task1);
        commentService.addComment( "Recordeu que una funcionalitat no completada no contarà per l'sprint", users.get(0),task1);
        Task subtask11 = taskService.createSubTask(task1.getId(), "Diagrama entitat-relació", users.get(2).getId());
        Task subtask12 = taskService.createSubTask(task1.getId(), "Diagrama classes", users.get(2).getId());
        MergePatchTaskDTO editTaskSub11 = new MergePatchTaskDTO();
        MergePatchTaskDTO editTaskSub12 = new MergePatchTaskDTO();
        editTaskSub11.setAssignee(Optional.of(users.get(2).getEmail()));
        editTaskSub12.setAssignee(Optional.of(users.get(2).getEmail()));
        editTaskSub11.setStatus(Optional.of(TaskStatus.DONE));
        editTaskSub12.setStatus(Optional.of(TaskStatus.DONE));
        editTaskSub11.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        editTaskSub12.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        taskService.editTask(subtask11.getId(), editTaskSub11, users.get(0).getId());
        taskService.editTask(subtask12.getId(), editTaskSub12, users.get(0).getId());
        //Task2
        Task task2 = taskService.createTask(project.getId(), "Implementació Swagger", users.get(3).getId());
        MergePatchTaskDTO editTask2 = new MergePatchTaskDTO();
        editTask2.setDescription(Optional.of("S'ha de fer una implementació de Swagger per a la documentació de l'API REST del projecte final de grau."));
        editTask2.setAssignee(Optional.of(users.get(3).getEmail()));
        editTask2.setEstimationPoints(Optional.of(10));
        editTask2.setStatus(Optional.of(TaskStatus.INPROGRESS));
        editTask2.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint2.getId()))));
        taskService.editTask(task2.getId(), editTask2, users.get(3).getId());
        //Task3
        Task task3 = taskService.createTask(project.getId(), "Documentació memoria", users.get(4).getId());
        MergePatchTaskDTO editTask3 = new MergePatchTaskDTO();
        editTask3.setDescription(Optional.of("S'ha de fer una documentació de la memòria del projecte final de grau."));
        editTask3.setAssignee(Optional.of(users.get(4).getEmail()));
        editTask3.setEstimationPoints(Optional.of(15));
        taskService.editTask(task3.getId(), editTask3, users.get(4).getId());
        //Task4
        Task task4 = taskService.createTask(project.getId(), "Preparació defensa oral", users.get(5).getId());
        MergePatchTaskDTO editTask4 = new MergePatchTaskDTO();
        editTask4.setDescription(Optional.of("S'ha de preparar la defensa oral del projecte final de grau."));
        editTask4.setAssignee(Optional.of(users.get(5).getEmail()));
        editTask4.setEstimationPoints(Optional.of(10));
        editTask4.setStatus(Optional.of(TaskStatus.TODO));
        editTask4.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint3.getId()))));
        taskService.editTask(task4.getId(), editTask4, users.get(5).getId());
        //Task5
        Task task5 = taskService.createTask(project.getId(), "Implementació integració GitHub", users.get(6).getId());
        MergePatchTaskDTO editTask5 = new MergePatchTaskDTO();
        editTask5.setDescription(Optional.of("S'ha de fer una implementació de la integració de GitHub per tal de recollir dades de la plataforma."));
        editTask5.setAssignee(Optional.of(users.get(6).getEmail()));
        editTask5.setEstimationPoints(Optional.of(8));
        editTask5.setStatus(Optional.of(TaskStatus.VERIFY));
        editTask5.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint2.getId()))));
        taskService.editTask(task5.getId(), editTask5, users.get(6).getId());
        //Task6
        Task task6 = taskService.createTask(project.getId(), "Implementació React router", users.get(2).getId());
        MergePatchTaskDTO editTask6 = new MergePatchTaskDTO();
        editTask6.setDescription(Optional.of("S'ha de fer una implementació de React router per tal de poder navegar entre les diferents pàgines de l'aplicació."));
        editTask6.setAssignee(Optional.of(users.get(2).getEmail()));
        editTask6.setEstimationPoints(Optional.of(5));
        editTask6.setStatus(Optional.of(TaskStatus.INPROGRESS));
        editTask6.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint2.getId()))));
        taskService.editTask(task6.getId(), editTask6, users.get(2).getId());
        //Task7
        Task task7 = taskService.createTask(project.getId(), "Sistema de avaluació projecte", users.get(1).getId());
        MergePatchTaskDTO editTask7 = new MergePatchTaskDTO();
        editTask7.setDescription(Optional.of("S'ha de fer una implementació de React router per tal de poder navegar entre les diferents pàgines de l'aplicació."));
        editTask7.setAssignee(Optional.of(users.get(1).getEmail()));
        editTask7.setEstimationPoints(Optional.of(9));
        editTask7.setStatus(Optional.of(TaskStatus.DONE));
        editTask7.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        taskService.editTask(task7.getId(), editTask7, users.get(1).getId());
        //Task8
        Task task8 = taskService.createTask(project.getId(), "Infrastructura arquitectura Controller-Service-Repo", users.get(1).getId());
        MergePatchTaskDTO editTask8 = new MergePatchTaskDTO();
        editTask8.setDescription(Optional.of("Ideació de la infrastructura de l'arquitectura Controller-Service-Repo per tal de poder implementar-la en el projecte final de grau."));
        editTask8.setAssignee(Optional.of(users.get(3).getEmail()));
        editTask8.setEstimationPoints(Optional.of(10));
        taskService.editTask(task8.getId(), editTask8, users.get(1).getId());
        //Task9
        Task task9 = taskService.createTask(project.getId(), "Maquetació component taula de tasques", users.get(2).getId());
        MergePatchTaskDTO editTask9 = new MergePatchTaskDTO();
        editTask9.setDescription(Optional.of("Maquetació del component taula de tasques per tal de poder implementar-la en el projecte final de grau."));
        editTask9.setAssignee(Optional.of(users.get(2).getEmail()));
        editTask9.setEstimationPoints(Optional.of(5));
        editTask9.setStatus(Optional.of(TaskStatus.VERIFY));
        editTask9.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint2.getId()))));
        taskService.editTask(task9.getId(), editTask9, users.get(2).getId());
        //Task10
        Task task10 = taskService.createTask(project.getId(), "Instal·lació SonarQube Server/SonarQube/SonarScanner CLI", users.get(4).getId());
        MergePatchTaskDTO editTask10 = new MergePatchTaskDTO();
        editTask10.setDescription(Optional.of("Instal·lació del SonarQube Server/SonarQube/SonarScanner CLI per tal de poder implementar-la en el projecte final de grau."));
        editTask10.setAssignee(Optional.of(users.get(1).getEmail()));
        editTask10.setEstimationPoints(Optional.of(10));
        editTask10.setStatus(Optional.of(TaskStatus.DONE));
        editTask10.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        taskService.editTask(task10.getId(), editTask10, users.get(4).getId());
        //Task11
        Task task11 = taskService.createTask(project.getId(), "Desenvolupament Spring Security & JWT", users.get(5).getId());
        MergePatchTaskDTO editTask11 = new MergePatchTaskDTO();
        editTask11.setDescription(Optional.of("Desenvolupament de Spring Security & JWT per tal de poder implementar-la en el projecte final de grau."));
        editTask11.setAssignee(Optional.of(users.get(6).getEmail()));
        editTask11.setEstimationPoints(Optional.of(6));
        editTask11.setStatus(Optional.of(TaskStatus.DONE));
        editTask11.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint1.getId()))));
        taskService.editTask(task11.getId(), editTask11, users.get(5).getId());
        //Task12
        Task task12 = taskService.createTask(project.getId(), "Implementació Spring Data JPA", users.get(6).getId());
        MergePatchTaskDTO editTask12 = new MergePatchTaskDTO();
        editTask12.setDescription(Optional.of("Implementació de Spring Data JPA per tal de poder implementar-la en el projecte final de grau."));
        editTask12.setAssignee(Optional.of(users.get(6).getEmail()));
        editTask12.setEstimationPoints(Optional.of(9));
        editTask12.setStatus(Optional.of(TaskStatus.INPROGRESS));
        editTask12.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint2.getId()))));
        taskService.editTask(task12.getId(), editTask12, users.get(6).getId());
        //Task13
        Task task13 = taskService.createTask(project.getId(), "Preparació MVP defensa oral PFG ", users.get(2).getId());
        MergePatchTaskDTO editTask13 = new MergePatchTaskDTO();
        editTask13.setDescription(Optional.of("Preparació del MVP de la defensa oral del projecte final de grau."));
        editTask13.setAssignee(Optional.of(users.get(2).getEmail()));
        editTask13.setEstimationPoints(Optional.of(6));
        editTask13.setStatus(Optional.of(TaskStatus.TODO));
        editTask13.setActiveSprints(Optional.of(new ArrayList<>(Collections.singletonList(sprint3.getId()))));
        taskService.editTask(task13.getId(), editTask13, users.get(2).getId());

        userService.setCurrentProject(admin,project);
    }

    private void populateProject(User admin, Course course, String projectName, List<User> users) {
        List<String> emails = new ArrayList<>();
        for(User user: users) {
            emails.add(user.getEmail());
        }

        Project project = projectService.createProject(projectName, emails, course.getId(), admin.getId());
        int j = new Random().nextInt(10);
        if(j % 2 != 0) {
            projectService.editProject(project.getId(), project.getName(),project.getMembers().stream().map(User::getEmail).collect(java.util.stream.Collectors.toList()), project.getCourse().getId(), 6.5,admin.getId());
        }
        else projectService.editProject(project.getId(), project.getName(),project.getMembers().stream().map(User::getEmail).collect(java.util.stream.Collectors.toList()), project.getCourse().getId(), null,admin.getId());

        Random random = new Random();
        LocalDate start = LocalDate.of(2021,3,1);
        LocalDate end = start.plusDays(14);
        populatePastSprint(project, "First iteration", start, end, users, true);
        start = end;
        end = start.plusDays(14);
        //populatePastSprint(backlog.getId(), "Second iteration", start, end, users, true);
        start = end;
        end = start.plusDays(14);
        //populatePastSprint(backlog.getId(), "Third iteration", start, end, users, false);

        for(int i = 0; i <= 15; i++) {
            User reporter = users.get(random.nextInt(users.size()));
            Task task = taskService.createTask(project.getId(), "Lorem ipsum dolor sit amet", reporter.getId());

            if(random.nextBoolean()) {
                MergePatchTaskDTO editTask = buildBacklogEditTask(users, random);
                taskService.editTask(task.getId(), editTask, reporter.getId());
            }
            if(i == 0) {
                taskService.createSubTask(task.getId(), "Create endpoint GET /items", reporter.getId());
                taskService.createSubTask(task.getId(), "Show list of items in UI", reporter.getId());
                taskService.createSubTask(task.getId(), "Style items", reporter.getId());
            }
        }
    }

    private void populatePastSprint(Project project, String name, LocalDate start, LocalDate end, List<User> users, boolean close) {
        Random random = new Random();
        User sprintCreator = users.get(random.nextInt(users.size()));
        Sprint sprint = sprintService.create(project, name, Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()), sprintCreator.getId());

        List<Task> tasks = createTasks(project.getId(), 5, users, random);
        User editor = users.get(random.nextInt(users.size()));
        Integer rank = 1;
        for(Task task : tasks) {
            // Add to sprint
            MergePatchTaskDTO change = new MergePatchTaskDTO();
            Collection<Long> sprints = new ArrayList<>();
            sprints.add(sprint.getId());
            change.setActiveSprints(Optional.of(sprints));
            change.setRank(Optional.of(rank));
            taskService.editTask(task.getId(), change, editor.getId());

            // Random change
            MergePatchTaskDTO editTask = buildBacklogEditTask(users, random);
            taskService.editTask(task.getId(), editTask, editor.getId());

            rank++;
        }
        saveOpenSprint(sprintCreator, sprint);

        if(close) {
            for(Task task : tasks) {
                if(task.getStatus() != TaskStatus.DONE) {
                    MergePatchTaskDTO change = new MergePatchTaskDTO();
                    change.setStatus(Optional.of(TaskStatus.DONE));
                    taskService.editTask(task.getId(), change, editor.getId());
                }
            }
            saveCloseSprint(sprintCreator, sprint);
        } else {
            for(Task task : tasks) {
                if(random.nextBoolean()) {
                    MergePatchTaskDTO change = new MergePatchTaskDTO();
                    change.setStatus(Optional.of(getRandomStatus(random)));
                    taskService.editTask(task.getId(), change, editor.getId());
                }
            }
        }
    }

    private void saveOpenSprint(User sprintCreator, Sprint sprint) {
        SprintRequestDTO sprintChange = new SprintRequestDTO();
        sprintChange.status = SprintStatus.ACTIVE;
        sprintService.editSprint(sprint.getId(), sprintChange, sprintCreator.getId());
    }

    private void saveCloseSprint(User sprintCreator, Sprint sprint) {
        SprintRequestDTO sprintChange = new SprintRequestDTO();
        sprintChange.status = SprintStatus.CLOSED;
        sprintService.editSprint(sprint.getId(), sprintChange, sprintCreator.getId());
    }

    private List<Task> createTasks(Long projectId, int amount, List<User> users, Random random) {
        List<Task> tasks = new ArrayList<>();
        for(int i = 0; i <= amount; i++) {
            User reporter = users.get(random.nextInt(users.size()));
            Task task = taskService.createTask(projectId, "Lorem ipsum dolor sit amet", reporter.getId());
            tasks.add(task);
        }
        return tasks;
    }

    private MergePatchTaskDTO buildBacklogEditTask(List<User> users, Random random) {
        Integer points = possibleEstimationPoints.get(random.nextInt(possibleEstimationPoints.size()));
        User assignee = users.get(random.nextInt(users.size()));

        MergePatchTaskDTO editTask = new MergePatchTaskDTO();
        editTask.setAssignee(Optional.of(assignee.getEmail()));
        editTask.setEstimationPoints(Optional.of(points));
        return editTask;
    }

    private TaskStatus getRandomStatus(Random random) {
        TaskStatus status = TaskStatus.BACKLOG;
        int index = random.nextInt(3);
        switch (index) {
            case 0:
                status = TaskStatus.TODO;
                break;
            case 1:
                status = TaskStatus.INPROGRESS;
                break;
            case 2:
                status = TaskStatus.DONE;
                break;
        }
        return status;
    }

    private List<User> createDemoStudents() {
        List<String> names = Arrays.asList(
            "Blanca", "Said", "Carles", "Ferran", "Joanot", "Hassen", "Malek", "Osman", "Mahomet", "Guillem", "Roc", // mar i cel
            "Hamlet", "Claudius", "Gertrude", "The Ghost", "Polonius", "Ophelia", "Horatio", "Laertes", "Rosencrantz", "Guildenstern", "Fortinbras" // hamlet
        );
        List<User> users = new ArrayList<>();
        for(String name: names) {
            String username = name.toLowerCase(Locale.ROOT).replace(" ", ".");
            String encodedPassword = global.getPasswordEncoder().encode(username + "1234");
            User user = userService.addUserInternal(username, username + "@trackdev.com", encodedPassword, List.of(UserType.STUDENT));
            users.add(user);
        }
        return users;
    }

}
