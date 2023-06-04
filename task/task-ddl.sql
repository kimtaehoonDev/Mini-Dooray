DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS Tags_Tasks;
DROP TABLE IF EXISTS Task_Managers;
DROP TABLE IF EXISTS MileStones;
DROP TABLE IF EXISTS Tags;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Color_Code;
DROP TABLE IF EXISTS Members;


CREATE TABLE `Projects` (
	`project_id`	BIGINT	NOT NULL,
	`admin_id`	int	NULL,
	`name`	varchar(255)	NULL,
	`description`	text	NULL
);

CREATE TABLE `Tasks` (
	`task_id`	BIGINT	NOT NULL,
	`project_id`	bigint	NOT NULL,
	`milestone_id`	bigint	NOT NULL,
	`index_in_project`	int	NULL,
	`title`	varchar(255)	NULL,
	`contents`	text	NULL,
	`writer_id`	int	NULL,
	`writer_name`	varchar(255)	NULL,
	`email`	varchar(255)	NULL,
	`created_at`	datetime	NULL
);

CREATE TABLE `Comments` (
	`comment_id`	BIGINT	NOT NULL,
	`task_id`	bigint	NOT NULL,
	`created_at`	datetime	NULL,
	`writer_id`	int	NULL,
	`writer_name`	varchar(255)	NULL,
	`contents`	text	NULL
);

CREATE TABLE `Tags` (
	`tag_id`	BIGINT	NOT NULL,
	`project_id`	bigint	NOT NULL,
	`name`	varchar(255)	NULL,
	`color_id`	tinyint	NOT NULL
);

CREATE TABLE `MileStones` (
	`milestone_id`	BIGINT	NOT NULL,
	`project_id`	bigint	NOT NULL,
	`name`	varchar(255)	NULL,
	`start_date`	DATETIME	NULL,
	`end_date`	DATETIME	NULL
);

CREATE TABLE `Members` (
	`member_id`	INT	NOT NULL,
	`name`	varchar(255)	NULL,
	`email`	varchar(255)	NULL,
	`phone_num`	varchar(255)	NULL
);

CREATE TABLE `Tags_Tasks` (
	`tags_tasks_id`	BIGINT	NOT NULL,
	`tag_id`	bigint	NOT NULL,
	`task_id`	bigint	NOT NULL
);

CREATE TABLE `Task_Managers` (
	`task_managers_id`	BIGINT	NOT NULL,
	`task_id`	bigint	NOT NULL,
	`manager_id`	int	NULL,
	`manager_name`	varchar(255)	NULL
);

CREATE TABLE `Color_Code` (
	`color_id`	tinyint	NOT NULL,
	`color_code`	varchar(255)	NULL
);

ALTER TABLE `Projects` ADD CONSTRAINT `PK_PROJECTS` PRIMARY KEY (
	`project_id`
);

ALTER TABLE `Tasks` ADD CONSTRAINT `PK_TASKS` PRIMARY KEY (
	`task_id`
);

ALTER TABLE `Comments` ADD CONSTRAINT `PK_COMMENTS` PRIMARY KEY (
	`comment_id`
);

ALTER TABLE `Tags` ADD CONSTRAINT `PK_TAGS` PRIMARY KEY (
	`tag_id`
);

ALTER TABLE `MileStones` ADD CONSTRAINT `PK_MILESTONES` PRIMARY KEY (
	`milestone_id`
);

ALTER TABLE `Members` ADD CONSTRAINT `PK_MEMBERS` PRIMARY KEY (
	`member_id`
);

ALTER TABLE `Tags_Tasks` ADD CONSTRAINT `PK_TAGS_TASKS` PRIMARY KEY (
	`tags_tasks_id`
);

ALTER TABLE `Task_Managers` ADD CONSTRAINT `PK_TASK_MANAGERS` PRIMARY KEY (
	`task_managers_id`
);

ALTER TABLE `Color_Code` ADD CONSTRAINT `PK_COLOR_CODE` PRIMARY KEY (
	`color_id`
);

