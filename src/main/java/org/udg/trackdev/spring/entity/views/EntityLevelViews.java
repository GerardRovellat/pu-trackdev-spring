package org.udg.trackdev.spring.entity.views;

/**
 * The type Entity level views.
 */
// Views for use by Json serialization
// to solve problems of recursion in entities with bidirectional relationships
public class EntityLevelViews {
    /**
     * The type Hierarchy.
     */
    static public class Hierarchy { }

    /**
     * The type Basic.
     */
    static public class Basic { }

    /**
     * The type Subject complete.
     */
    static public class SubjectComplete extends Basic { }

    /**
     * The type Course complete.
     */
    static public class CourseComplete extends Basic { }

    /**
     * The type Project with user.
     */
    static public class ProjectWithUser extends Basic { }

    /**
     * The type Project complete.
     */
    static public class ProjectComplete extends ProjectWithUser { }

    /**
     * The type Task complete.
     */
    static public class TaskComplete extends Basic { }

    /**
     * The type Sprint complete.
     */
    static public class SprintComplete extends Basic { }

    /**
     * The type User without project members.
     */
    static public class UserWithoutProjectMembers extends Basic { }

    /**
     * The type User with github token.
     */
    static public class UserWithGithubToken extends UserWithoutProjectMembers { }

    /**
     * The type Task with project members.
     */
    static public class TaskWithProjectMembers extends TaskComplete { }
}