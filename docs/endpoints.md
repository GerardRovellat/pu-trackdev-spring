# API Rest 

## Authentication
Methods to handle authentication

Method|Path|Description|Status
------|-----|---------|---
POST | /auth/login | Logins. Create JWT and adds needed cookie | 
POST | /auth/logout | Expire session | 
GET | /auth/check | Returns if session is still active | 
GET | /auth/self | Logged in user profile | 💡 Move to /users/self?

## Invites

Method|Path|Description|Status
------|-----|---------|---
POST | /invites | Creates an invite to register to the application with specific roles | 
GET | /invites | List invites created by the user | 
DELETE | /invites/{inviteId} | Delete created invite | 

### Invites to logged in user

Method|Path|Description|Status
------|-----|---------|---
GET   | /users/self/invites | List invites sent to the user | 
PATCH  | /users/self/invites/{inviteId} | Update invite by accepting it | 

## Users

Method|Path|Description|Status
------|-----|---------|--- 
GET | /users/{username} | View public profile of user |
POST | /register | Sign up into the application using an invite | 

## Courses

Method|Path|Description|Status
------|-----|---------|--- 
GET | /courses | View courses created by the user |
GET | /courses/{id} | View subject |
POST | /courses | Create a new subject |
PUT | /courses/{id} | Update general properties of a subject |
DELETE | /courses/{id} | Delete subject. Note: only works for courses without projects. |

Comments:
- Only professors can create courses
- Only creators can manage them

## Course year

Method|Path|Description|Status
------|-----|---------|---
GET | /courses/years | View enrolled subject years as student | ✨ New
GET | /courses/years/{yearId} | View one subject year | ✨ New
POST | /courses/{courseId}/years | Create new academic year for a subject |
DELETE | /courses/years/{yearId} | Delete an academic year of a subject |

### Invites to subject year

Method|Path|Description|Status
------|-----|---------|---
POST | /courses/years/{yearId}/invites | Invite user by email to a subject year | 

### Students
Method|Path|Description|Status
------|-----|---------|---
GET | /courses/years/{yearId}/students | View enrolled students of a subject year |
DELETE | /courses/years/{yearId}/students/{username} | Remove a student from a subject year |

### Groups
Method|Path|Description|Status
------|-----|---------|---
GET | /courses/years/{yearId}/projects | View projects of a subject year | 
POST | /courses/years/{yearId}/projects | Create new project inside a subject year | 
GET | /projects/{groupId} | View a project. | ✨ New
PATCH | /projects/{groupId} | Modify general data of a project | ✨ New
DELETE | /projects/{groupId} | Delete a project | ✨ New