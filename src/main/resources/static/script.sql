CREATE DATABASE hubstream_online_api;

INSERT INTO plans(id_plan,duree,duree_extension,prix,titre,type,points,livraison) VALUES
(0,1,'Jour',50,'All-J1','All',9,0),
(0,3,'Jours',125,'All-J3','All',12,1),
(0,7,'Jours',295,'All-J7','All',17,2),
(0,30,'Jours',1150,'All-J30','All',24,8);


UPDATE plans set livraison=0 WHERE id_plan=1;
UPDATE plans set livraison=1 WHERE id_plan=2;
UPDATE plans set livraison=2 WHERE id_plan=3;
UPDATE plans set livraison=8 WHERE id_plan=4;