package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.controller.exceptions.EntityNotFound;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.repository.SubjectRepository;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.util.List;
import java.util.Optional;

/**
 * The type Subject service.
 */
@Service
public class SubjectService extends BaseServiceLong<Subject, SubjectRepository> {

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Access checker.
     */
    @Autowired
    AccessChecker accessChecker;

    /**
     * Gets subject.
     *
     * @param id the id
     * @return the subject
     */
    public Subject getSubject(Long id) {
        Optional<Subject> oc = this.repo.findById(id);
        if (oc.isEmpty())
            throw new EntityNotFound(ErrorConstants.SUBJECT_NOT_EXIST);
        return oc.get();
    }


    /**
     * Create subject subject.
     *
     * @param name           the name
     * @param acronym        the acronym
     * @param loggedInUserId the logged in user id
     * @return the subject
     */
    @Transactional
    public Subject createSubject(String name, String acronym, String loggedInUserId) {
        User owner = userService.get(loggedInUserId);
        accessChecker.checkCanCreateSubject(owner);
        Subject  subject = new Subject(name,acronym, owner);
        owner.addOwnCourse(subject);
        repo.save(subject);
        return subject;
    }

    /**
     * Edit subject details subject.
     *
     * @param id             the id
     * @param name           the name
     * @param acronym        the acronym
     * @param loggedInUserId the logged in user id
     * @return the subject
     */
    public Subject editSubjectDetails(Long id, String name, String acronym, String loggedInUserId) {
        Subject subject = getSubject(id);
        accessChecker.checkCanManageSubject(subject, loggedInUserId);
        subject.setName(name);
        subject.setAcronym(acronym);
        repo.save(subject);
        return subject;
    }

    /**
     * Delete subject.
     *
     * @param id             the id
     * @param loggedInUserId the logged in user id
     */
    public void deleteSubject(Long id, String loggedInUserId) {
        Subject subject = getSubject(id);
        accessChecker.checkCanManageSubject(subject, loggedInUserId);
        repo.delete(subject);
    }

    /**
     * Find courses owned list.
     *
     * @param uuid the uuid
     * @return the list
     */
    List<Subject> findCoursesOwned(String uuid) {
        return this.repo.findByOwner(uuid);
    }
}
