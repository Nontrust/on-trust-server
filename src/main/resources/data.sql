-- insert account
insert ignore into account(id, email, name, password, created_Date, updated_Date)
    values (1, 'Nontrust', '이충호', '1234',now(), now());


-- insert post
insert ignore into post(id, title ,contents, created_Date, updated_Date)
    value(1, 'test title','test contents',now(), now());
