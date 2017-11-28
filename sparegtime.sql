create database sparegtime;
#-------------------------------
use sparegtime;
#------------------------------
create table category(
main varchar(15),
content varchar(15));
#------------------------------
create table contents(
content varchar(15),
name varchar(25),
star numeric(2,1),
address varchar(50),
distance INT);
#----------------------------
create table roomlist(
id INT,
name varchar(20),
maximum INT,
spare_time varchar(11),
content varchar(15));
#------------------------------
insert into category
values('Dessert','Ice');
insert into category
values('Dessert','Coffee');
insert into category
values('Dessert','Bakery');
insert into category
values('Entertainment','Billard');
insert into category
values('Entertainment','Pcroom');
insert into category
values('Entertainment','Sing');
insert into category
values('Events','Event');
insert into category
values('Events','Advertise');
#--------------------------------
insert into contents
values('Event','간식행사',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Event','선착순!!',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Event','보물찾기',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Event','선거',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Advertise','봉구스 1+1',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Advertise','올리브영 50%할인',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Advertise','토마토도시락 무료 size up!',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Advertise','Ediya 아메리카노 무료',0,'경기 성남시 수정구 복정로 7 승룡빌딩',0);
insert into contents
values('Sing','노리터오락실',3.0,'경기 성남시 수정구 복정로 7 승룡빌딩',8);
insert into contents
values('Coffee','ormak coffee',3.5,'경기 성남시 수정구 복정로 7 승룡빌딩',8);
insert into contents
values('Billiard','가천대당구장',3.0,'경기 성남시 수정구 복정로 7 승룡빌딩',8);
insert into contents
values('Bakery','뚜레쥬르',5.0,'경기 성남시 수정구 성남대로 1390번길 5 나라빌딩',9);
insert into contents
values('Sing','짱노래연습장',2.0,'경기 성남시 수정구 복정로 15',10);
insert into contents
values('Pcroom','여우비pc방',4.0,'경기 성남시 수정구 복정동 711-1번지 여우비 pc방',10);
insert into contents
values('Coffee','Coffee Camper',4.0,'경기 성남시 수정구 복정동 711-1번지',12);
insert into contents
values('Pcroom','바닐라pc방',5.0,'경기 성남시 수정구 복정로 21 늘푸른빌딩',12);
insert into contents
values('Billiard','K2당구클럽',4.0,'경기도 성남시 수정구 복정로 25-1',13);
insert into contents
values('Sing','로또노래연습장',4.0,'경기 성남시 수정구 복정로 53',15);
insert into contents
values('Sing','콜노래연습장',4.0,'경기 성남시 수정구 복정로 45',15);
insert into contents
values('Coffee','The Hans',5.0,'경기 성남시 수정구 복정로 49',18);
insert into contents
values('Pcroom','갤러리pc방',5.0,'경기도 성남시 수정구 복정로 35',18);
insert into contents
values('Billiard','태양당구장',2.0,'경기 성남시 수정구 복정로 57 지산빌딩',20);
insert into contents
values('Sing','락휴노래방',3.0,'경기 성남시 수정구 복정로 57 지산빌딩',20);
insert into contents
values('Sing','씨밀레노래방',1.0,'경기 성남시 수정구 복정로 61 미주빌딩',22);
insert into contents
values('Sing','레몬트리노래방',1.0,'경기 성남시 수정구 복정로 63',22);
insert into contents
values('Pcroom','와우pc방',3.0,'경기 성남시 수정구 복정로 63',24);
insert into contents
values('Coffee','Coffea Coffee',4.0,'경기 성남시 수정구 복정로 65',26);
insert into contents
values('Billiard','캐롬당구장',2.0,'경기 성남시 수정구 복정로 68',27);
insert into contents
values('Coffee','Ediya coffee(동서울대점)',5.0,'경기 성남시 수정구 복정로 68',25);
insert into contents
values('Billiard','홀릭당구장',2.0,'경기 성남시 수정구 복정로 64',23);
insert into contents
values('Billiard','복정당구장',3.0,'경기 성남시 수정구 복정로 68',21);
insert into contents
values('Sing','드루와노래연습장',4.0,'경기도 성남시 수정구 복정로 58',19);
insert into contents
values('Coffee','콩더하기콩 커피볶는집',5.0,'경기 성남시 수정구 복정로 20',17);
insert into contents
values('Coffee','Cityracoon Cafe R42',5.0,'경기 성남시 수정구 복정로 20',16);
insert into contents
values('Coffee','Joyful coffee',5.0,'경기 성남시 수정구 복정로20번길 3',14);
insert into contents
values('Sing','펭귄노래방',2.0,'경기도 성남시 수정구 복정로 18 지산빌딩',11);
insert into contents
values('Coffee','The brown',5.0,'경기 성남시 수정구 복정로20번길 3',9);
insert into contents
values('Coffee','Sleep walk',5.0,'경기 성남시 수정구 성남대로 1334',6);
insert into contents
values('Billiard','월드당구장',3.0,'경기 성남시 수정구 성남대로 1334',6);
insert into contents
values('Billiard','스핀당구장',3.0,'경기 성남시 수정구 성남대로 1334',6);
insert into contents
values('Sing','경원노래방',3.0,'경기 성남시 수정구 성남대로 1334',6);
insert into contents
values('Coffee','Ediya coffee(가천대점)','경기 성남시 수정구 성남대로 1334',5);
insert into contents
values('Coffee','떼루와',5.0,'경기 성남시 수정구 성남대로 1334',5);
insert into contents
values('Coffee','커피만',4.0,'경기 성남시 수정구 성남대로 1334',5);
insert into contents
values('Coffee','Dr.juice',3.0,'경기도 성남시 수정구 성남대로 1330',4);
insert into contents
values('Coffee','빽다방',5.0,'경기 성남시 수정구 성남대로 1330',3);
insert into contents
values('Coffee','Gong cha',5.0,'경기 성남시 수정구 성남대로 1330',2);
insert into contents
values('Coffee','Mammoth coffee',5.0,'경기 성남시 수정구 성남대로 1330',1);
insert into contents
values('Billiard','아카데미 당구장',4.0,'경기 성남시 수정구 성남대로 1332 덕영빌딩',0);
insert into contents
values('Ice','설빙',5.0,'경기 성남시 수정구 성남대로 1332 덕영빌딩',0);
insert into contents
values('Ice','Ediya coffee(동서울대점)',5.0,'경기 성남시 수정구 복정로 68',25);
insert into contents
values('Ice','Ediya coffee(가천대점)',5.0,'경기 성남시 수정구 성남대로 1334',5);
insert into contents
values('Bakery','The brown',5.0,'경기 성남시 수정구 복정로20번길 3',9);
insert into contents
values('Bakery','Cityracoon Cafe R42',5.0,'경기 성남시 수정구 복정로 20',16);
insert into contents
values('Bakery','Gong cha',5.0,'경기 성남시 수정구 성남대로 1330',2);
insert into contents
values('Ice','빽다방',5.0,'경기 성남시 수정구 성남대로 1330',3);
insert into contents
values('Coffee','Cafero',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Coffee','투썸플레이스',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Bakery','투썸플레이스',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Ice','투썸플레이스',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Bakery','던킨도넛',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Coffee','Caffe-pascucci',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Ice','Caffe-pascucci',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Bakery','ormak coffee',5.0,'경기 성남시 수정구 복정로 7 승룡빌딩',8);
insert into contents
values('Billiard','큐당구장',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Ice','Joyful coffee',5.0,'경기 성남시 수정구 복정로20번길 3',10);
insert into contents
values('Bakery','Joyful coffee',5.0,'경기 성남시 수정구 복정로20번길 3',14);
insert into contents
values('Bakery','콩더하기콩 커피볶는집',3.0,'경기도 성남시 수정구 복정로32번길 1',17);
insert into contents
values('Bakery','The Hans',5.0,'경기 성남시 수정구 복정로 49',18);
insert into contents
values('Bakery','Coffea Coffee',5.0,'경기 성남시 수정구 복절로 65',26);
insert into contents
values('Bakery','Caffe-pascucci',5.0,'경기 성남시 수정구 성남대로 1342',7);
insert into contents
values('Bakery','파리바게트',5.0,'경기 성남시 수정구 성남대로 1342',7);