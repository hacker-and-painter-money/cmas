create table chat_group
(
	id int auto_increment
		primary key,
	name varchar(255) not null comment '名称',
	type int not null comment '类型（0：单聊/好友。1：群聊）',
	status int default 0 not null comment '状态（0：正常，1：删除）',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime null
);

create table chat_group_user_relation
(
	id int auto_increment
		primary key,
	group_id int not null comment '聊天组id',
	user_id int not null comment '用户id',
	identity int not null comment '身份（仅限群聊，0：普通，1：管理员，2：所有者）',
	status int default 0 null comment '1：正常、2：删除',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime null
);

create index group_id
	on chat_group_user_relation (group_id);

create index user_id
	on chat_group_user_relation (user_id);

alter table chat_group_user_relation
	add constraint chat_group_user_relation_ibfk_1
		foreign key (group_id) references chat_group (id);

create table chat_msg
(
	id int auto_increment
		primary key,
	group_id int not null comment '聊天组id',
	sender_id int not null comment '发送人id',
	content longtext not null comment '内容',
	parent_msg_id int null comment '引用消息id',
	status int default 0 not null comment '状态（0：正常，1：删除）',
	created_at datetime default CURRENT_TIMESTAMP not null comment '发送时间',
	updated_at datetime null
);

create index group_id
	on chat_msg (group_id);

create index parent_msg_id
	on chat_msg (parent_msg_id);

create index sender_id
	on chat_msg (sender_id);

alter table chat_msg
	add constraint chat_msg_ibfk_1
		foreign key (group_id) references chat_group (id);

alter table chat_msg
	add constraint chat_msg_ibfk_3
		foreign key (parent_msg_id) references chat_msg (id);

create table point
(
	id int auto_increment
		primary key,
	user_id int not null,
	total_points int default 0 not null comment '用户当前总积分',
	status int not null comment '0：正常、1：删除'
);

create table point_history
(
	id int auto_increment
		primary key,
	user_id int not null,
	change_amount int not null comment '积分变动数量，正数为增加，负数为减少',
	reason int not null comment '变动原因 1：提出问题 2：回答问题 3：其他',
	status int default 0 null comment '0：正常、1：失效',
	created_at datetime default CURRENT_TIMESTAMP not null comment '变动时间'
);

create index user_id
	on point_history (user_id);

create table question
(
	id int auto_increment
		primary key,
	title varchar(255) not null comment '标题',
	content longtext not null comment '内容',
	sender_id int not null comment '发送人id',
	status int default 0 not null comment '状态（0：待解决，1：删除，2：已解决）',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime null
);

create index sender_id
	on question (sender_id);

create table reply
(
	id int auto_increment
		primary key,
	content longtext not null comment '内容',
	question_id int not null comment '问题id',
	parent_reply_id int null comment '父回复id，可为空',
	status int default 0 not null comment '状态（0：正常，1：删除）',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime null
);

create index parent_reply_id
	on reply (parent_reply_id);

create index question_id
	on reply (question_id);

alter table reply
	add constraint reply_ibfk_1
		foreign key (question_id) references question (id);

alter table reply
	add constraint reply_ibfk_2
		foreign key (parent_reply_id) references reply (id);

create table resource
(
	id int auto_increment
		primary key,
	title varchar(255) not null comment '标题',
	introduction varchar(255) null comment '简介',
	tag varchar(255) null comment '标签',
	file_path varchar(255) not null comment '文件路径',
	owner_id int not null comment '所有者id',
	status int default 0 null comment '0：正常、1：删除、2：隐藏',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime null
);

create index owner_id
	on resource (owner_id);

create table user
(
	id int auto_increment
		primary key,
	username varchar(255) not null comment '用户名',
	password varchar(255) not null comment '密码',
	identity int not null comment '身份（0：学生，1：管理员）',
	status int default 0 not null comment '状态（0：正常，1：注销，2：封禁）',
	created_at datetime default CURRENT_TIMESTAMP not null,
	updated_at datetime not null
);

alter table chat_group_user_relation
	add constraint chat_group_user_relation_ibfk_2
		foreign key (user_id) references user (id);

alter table chat_msg
	add constraint chat_msg_ibfk_2
		foreign key (sender_id) references user (id);

alter table point
	add constraint points_user_id_fk
		foreign key (user_id) references user (id);

alter table point_history
	add constraint point_history_ibfk_1
		foreign key (user_id) references user (id);

alter table question
	add constraint question_ibfk_1
		foreign key (sender_id) references user (id);

alter table resource
	add constraint resource_ibfk_1
		foreign key (owner_id) references user (id);


