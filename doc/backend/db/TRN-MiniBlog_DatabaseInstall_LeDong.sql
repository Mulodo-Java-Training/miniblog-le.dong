
create schema Miniblog;

use Miniblog;

/*===========TABLE============*/

create table Account
(
	id_acc int(11) unsigned auto_increment not null ,
	acc_username varchar(50) not null,
	acc_password varchar(50) not null,
	acc_firstname varchar(20) not null,
	acc_lastname varchar(20) not null,
	acc_image varchar(50) ,
	acc_gender tinyint(1) not null, -- 1: male 0:female 2: others
	acc_email varchar(50) not null,
	acc_birthday timestamp not null,
	acc_status tinyint(1) not null, -- default 1: active, 0 : hide
	acc_role tinyint(1) not null, -- default 0 : user 1: admin
	primary key(id_acc)
);

create table Posts
(
	id_posts int(11) unsigned auto_increment not null,
	posts_title varchar(100) not null,
	posts_description varchar(200) not null,
	posts_create_date timestamp not null,
	posts_modified_date timestamp not null,
	posts_status tinyint(1) not null,
	id_acc int(11) unsigned not null,
	primary key(id_posts)
);


create table Comments
(
	id_comments int(11) unsigned auto_increment not null,
	comments_title varchar(100) not null,
	comments_description varchar(200) not null,
	comments_create_date timestamp not null,
	comments_modified_date timestamp not null,
	comments_status tinyint(1) not null,
	id_acc int(11) unsigned not null,
	id_posts int(11) unsigned not null,
	primary key(id_comments)
);

/*===========FOREIGN KEY============*/

alter table Posts
add constraint fk_posts_account foreign key(id_acc) references Account(id_acc);

alter table Comments
add constraint fk_comments_account foreign key(id_acc) references Account(id_acc);

alter table Comments
add constraint fk_comments_posts foreign key(id_posts) references Posts(id_posts);

