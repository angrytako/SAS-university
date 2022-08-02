  -- MySQL dump 10.13  Distrib 5.7.26, for osx10.10 (x86_64)
  --
  -- Host: 127.0.0.1    Database: catering
  -- ------------------------------------------------------
  -- Server version	5.7.26

  /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
  /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
  /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
  /*!40101 SET NAMES utf8 */;
  /*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
  /*!40103 SET TIME_ZONE='+00:00' */;
  /*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
  /*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
  /*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
  /*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

  --
  -- Table structure for table `Events`
  --

  DROP TABLE IF EXISTS `Events`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `Events` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(128) DEFAULT NULL,
    `date_start` date DEFAULT NULL,
    `date_end` date DEFAULT NULL,
    `expected_participants` int(11) DEFAULT NULL,
    `organizer_id` int(11) NOT NULL,
    `chef_id` int(11) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `Events`
  --

  LOCK TABLES `Events` WRITE;
  /*!40000 ALTER TABLE `Events` DISABLE KEYS */;
  INSERT INTO `Events` VALUES (1,'Convegno Agile Community','2020-09-25','2020-09-25',100,2, 4),(2,'Compleanno di Manuela','2020-08-13','2020-08-13',25,2, 4),(3,'Fiera del Sedano Rapa','2020-10-02','2020-10-04',400,1,2);
  /*!40000 ALTER TABLE `Events` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `MenuFeatures`
  --

  DROP TABLE IF EXISTS `MenuFeatures`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `MenuFeatures` (
    `menu_id` int(11) NOT NULL,
    `name` varchar(128) NOT NULL DEFAULT '',
    `value` tinyint(1) DEFAULT '0'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `MenuFeatures`
  --

  LOCK TABLES `MenuFeatures` WRITE;
  /*!40000 ALTER TABLE `MenuFeatures` DISABLE KEYS */;
  INSERT INTO `MenuFeatures` VALUES (80,'Richiede cuoco',0),(80,'Buffet',0),(80,'Richiede cucina',0),(80,'Finger food',0),(80,'Piatti caldi',0),(82,'Richiede cuoco',0),(82,'Buffet',0),(82,'Richiede cucina',0),(82,'Finger food',0),(82,'Piatti caldi',0),(86,'Richiede cuoco',0),(86,'Buffet',0),(86,'Richiede cucina',0),(86,'Finger food',0),(86,'Piatti caldi',0);
  /*!40000 ALTER TABLE `MenuFeatures` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `MenuItems`
  --

  DROP TABLE IF EXISTS `MenuItems`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `MenuItems` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `menu_id` int(11) NOT NULL,
    `section_id` int(11) DEFAULT NULL,
    `description` tinytext,
    `recipe_id` int(11) NOT NULL,
    `position` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `MenuItems`
  --

  LOCK TABLES `MenuItems` WRITE;
  /*!40000 ALTER TABLE `MenuItems` DISABLE KEYS */;
  INSERT INTO `MenuItems` VALUES (96,80,3,'Croissant vuoti',9,0),(97,80,0,'Croissant alla marmellata',9,1),(98,80,0,'Pane al cioccolato mignon',10,2),(100,80,0,'Panini al latte con prosciutto cotto',12,5),(102,80,0,'Girelle all uvetta mignon',11,3),(103,82,0,'Biscotti',13,1),(104,82,0,'Lingue di gatto',14,2),(105,82,0,'Bigné alla crema',15,3),(106,82,0,'Bigné al caffè',15,4),(107,82,0,'Pizzette',16,5),(108,82,0,'Croissant al prosciutto crudo mignon',9,6),(109,82,0,'Tramezzini tonno e carciofini mignon',17,7),(112,86,41,'Vitello tonnato',1,0),(113,86,41,'Carpaccio di spada',2,1),(114,86,41,'Alici marinate',3,2),(115,86,42,'Penne alla messinese',5,0),(116,86,42,'Risotto alla zucca',20,1),(117,86,43,'Salmone al forno',8,0),(118,86,44,'Sorbetto al limone',18,0),(119,86,44,'Torta Saint Honoré',19,1);
  /*!40000 ALTER TABLE `MenuItems` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `MenuSections`
  --

  DROP TABLE IF EXISTS `MenuSections`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `MenuSections` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `menu_id` int(11) NOT NULL,
    `name` tinytext,
    `position` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `MenuSections`
  --

  LOCK TABLES `MenuSections` WRITE;
  /*!40000 ALTER TABLE `MenuSections` DISABLE KEYS */;
  INSERT INTO `MenuSections` VALUES (41,86,'Antipasti',0),(42,86,'Primi',1),(43,86,'Secondi',2),(44,86,'Dessert',3),(45,87,'Antipasti',0);
  /*!40000 ALTER TABLE `MenuSections` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `Menus`
  --

  DROP TABLE IF EXISTS `Menus`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `Menus` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` tinytext,
    `owner_id` int(11) DEFAULT NULL,
    `published` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `Menus`
  --

  LOCK TABLES `Menus` WRITE;
  /*!40000 ALTER TABLE `Menus` DISABLE KEYS */;
  INSERT INTO `Menus` VALUES (80,'Coffee break mattutino',2,1),(82,'Coffee break pomeridiano',2,1),(86,'Cena di compleanno pesce',3,1);
  /*!40000 ALTER TABLE `Menus` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `CookingItems`
  --

  DROP TABLE IF EXISTS `CookingItems`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `CookingItems` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `type` enum("recipe", "preparation") not null,	
    `name` tinytext,
    `description` varchar(512),
    `concreteTime` bigint,
    `totalTime` bigint,
    `finishTime` bigint,
    `isPublished` bit not null,

    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `CookingItems`
  --

  LOCK TABLES `CookingItems` WRITE;

  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (1,"recipe",'Vitello tonnato',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (2,"recipe",'Carpaccio di spada',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (3,"recipe",'Alici marinate',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (4,"recipe",'Insalata di riso',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (5,"recipe",'Penne al sugo di baccalà',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (6,"recipe",'Pappa al pomodoro',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (7,"recipe",'Hamburger con bacon e cipolla caramellata',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (8,"recipe",'Salmone al forno',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (9,"recipe",'Croissant',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (10,"recipe",'Pane al cioccolato',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (11,"recipe",'Girelle all uvetta',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (12,"recipe",'Panini al latte',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (13,"recipe",'Biscotti di pasta frolla',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (14,"recipe",'Lingue di gatto',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (15,"recipe",'Bigné farciti',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (16,"recipe",'Pizzette',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (17,"recipe",'Tramezzini',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (18,"recipe",'Sorbetto al limone',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (19,"recipe",'Torta Saint Honoré',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (20,"recipe",'Risotto alla zucca',1);


  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (21,"preparation",'impasto',1);
  INSERT INTO CookingItems (id,`type`,name,isPublished) VALUES (22,"preparation",'crema chantilie',1);

  UNLOCK TABLES;

  --
  -- Table structure for table `Roles`
  --

  DROP TABLE IF EXISTS `Roles`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `Roles` (
    `id` char(1) NOT NULL,
    `role` varchar(128) NOT NULL DEFAULT 'servizio',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `Roles`
  --

  LOCK TABLES `Roles` WRITE;
  /*!40000 ALTER TABLE `Roles` DISABLE KEYS */;
  INSERT INTO `Roles` VALUES ('c','cuoco'),('h','chef'),('o','organizzatore'),('s','servizio');
  /*!40000 ALTER TABLE `Roles` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `Services`
  --

  DROP TABLE IF EXISTS `Services`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `Services` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `event_id` int(11) NOT NULL,
    `name` varchar(128) DEFAULT NULL,
    `proposed_menu_id` int(11) NOT NULL DEFAULT '0',
    `approved_menu_id` int(11) DEFAULT '0',
    `service_date` date DEFAULT NULL,
    `time_start` time DEFAULT NULL,
    `time_end` time DEFAULT NULL,
    `expected_participants` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `Services`
  --

  LOCK TABLES `Services` WRITE;
  /*!40000 ALTER TABLE `Services` DISABLE KEYS */;
  INSERT INTO `Services` VALUES (1,2,'Cena',86,0,'2020-08-13','20:00:00','23:30:00',25),(2,1,'Coffee break mattino',0,80,'2020-09-25','10:30:00','11:30:00',100),(3,1,'Colazione di lavoro',0,0,'2020-09-25','13:00:00','14:00:00',80),(4,1,'Coffee break pomeriggio',0,82,'2020-09-25','16:00:00','16:30:00',100),(5,1,'Cena sociale',0,0,'2020-09-25','20:00:00','22:30:00',40),(6,3,'Pranzo giorno 1',0,0,'2020-10-02','12:00:00','15:00:00',200),(7,3,'Pranzo giorno 2',0,0,'2020-10-03','12:00:00','15:00:00',300),(8,3,'Pranzo giorno 3',0,0,'2020-10-04','12:00:00','15:00:00',400);
  /*!40000 ALTER TABLE `Services` ENABLE KEYS */;
  UNLOCK TABLES;


  DROP TABLE IF EXISTS `TaskSummarySheet`;
  CREATE TABLE `TaskSummarySheet` (
    `service` int(11) NOT NULL REFERENCES `Services`(`id`),
    PRIMARY KEY (`service`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `TaskSummarySheet` VALUES (1),(3),(4),(5),(6),(7),(8);

  







  --
  -- Table structure for table `UserRoles`
  --

  DROP TABLE IF EXISTS `UserRoles`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `UserRoles` (
    `user_id` int(11) NOT NULL,
    `role_id` char(1) NOT NULL DEFAULT 's'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `UserRoles`
  --

  LOCK TABLES `UserRoles` WRITE;
  /*!40000 ALTER TABLE `UserRoles` DISABLE KEYS */;
  INSERT INTO `UserRoles` VALUES (1,'o'),(2,'o'),(2,'h'),(3,'h'),(4,'h'),(4,'c'),(5,'c'),(6,'c'),(7,'c'),(8,'s'),(9,'s'),(10,'s'),(7,'s');
  /*!40000 ALTER TABLE `UserRoles` ENABLE KEYS */;
  UNLOCK TABLES;

  --
  -- Table structure for table `Users`
  --

  DROP TABLE IF EXISTS `Users`;
  /*!40101 SET @saved_cs_client     = @@character_set_client */;
  /*!40101 SET character_set_client = utf8 */;
  CREATE TABLE `Users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(128) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
  /*!40101 SET character_set_client = @saved_cs_client */;

  --
  -- Dumping data for table `Users`
  --

  LOCK TABLES `Users` WRITE;
  /*!40000 ALTER TABLE `Users` DISABLE KEYS */;
  INSERT INTO `Users` VALUES (1,'Carlin'),(2,'Lidia'),(3,'Tony'),(4,'Marinella'),(5,'Guido'),(6,'Antonietta'),(7,'Paola'),(8,'Silvia'),(9,'Marco'),(10,'Piergiorgio');
  /*!40000 ALTER TABLE `Users` ENABLE KEYS */;
  UNLOCK TABLES;
  /*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

  /*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
  /*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
  /*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
  /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
  /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
  /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
  /*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

  -- Dump completed on 2020-06-05 15:04:25


  DROP TABLE IF EXISTS `turns`;
  CREATE TABLE `turns` (
    `id` int(11) NOT NULL,
    `date` DATE NOT NULL,
    `location` varchar(256) NOT NULL,
    `timeslot` int(11) NOT NULL,
    PRIMARY KEY (`id`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `turns` VALUES (1,'2020-09-25','Dubay',8),(2,'2020-09-25','Dubay',15) ,(3,'2020-09-26','Dubay',8) ;


  DROP TABLE IF EXISTS `TurnsCooks`;
  CREATE TABLE `TurnsCooks` (
    `turn` int(11) NOT NULL REFERENCES  `turns`(`id`),
    `user` int(11) NOT NULL REFERENCES `users`(`id`),
    PRIMARY KEY (`turn`,`user`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `TurnsCooks` VALUES (1,3),(1,4),(2,3),(3,4);


  DROP TABLE IF EXISTS `dosages`;
  CREATE TABLE `dosages` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `quantity` bigint NOT NULL,
    `label` varchar(256) NOT NULL,
    PRIMARY KEY (`id`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `dosages` VALUES (1,5,'Kg'),(2,5000,'g'),(3,8,'pz');





  DROP TABLE IF EXISTS `KitchenTask`;
  CREATE TABLE `KitchenTask` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `estimatedTime` bigint,
    `isComplete` bit,

    `TaskSummarySheet` int(11) NOT NULL REFERENCES  `TaskSummarySheet`(`service`),
    `turn` int(11) REFERENCES `turns`(`id`),
    `dosage` int(11) REFERENCES `dosages`(`id`),
    `cook` int(11) REFERENCES `users`(`id`),
    `CookingItem` int(11) NOT NULL REFERENCES `CookingItems`(`id`),
    `position` int(11) NOT NULL,


    PRIMARY KEY (`id`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


  INSERT INTO KitchenTask (id,estimatedTime,isComplete,TaskSummarySheet,CookingItem,position) VALUES (1,120,0,4,14,1);
  INSERT INTO KitchenTask (id,estimatedTime,isComplete,TaskSummarySheet,CookingItem,position) VALUES (2,120,0,4,16,2);
  INSERT INTO KitchenTask (id,estimatedTime,isComplete,TaskSummarySheet,CookingItem,position) VALUES (3,120,0,2,12,1);



  DROP TABLE IF EXISTS `step`;
  CREATE TABLE `step` (
    `id` int(11) NOT NULL,
    `description` varchar(512) NOT NULL,
    PRIMARY KEY (`id`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `step` VALUES (1,'porta l acqua in ebbollizione'),(2,'butta la pasta'),(3,'scola');



  DROP TABLE IF EXISTS `BasicIngredient`;
  CREATE TABLE `BasicIngredient` (
    `name` varchar(512) NOT NULL,
    PRIMARY KEY (`name`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO `BasicIngredient` VALUES ("pane"),("sale"),("acqua"),("farina"),("zucchine");





  DROP TABLE IF EXISTS `IngredientWithDosage`;
  CREATE TABLE `IngredientWithDosage` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `CookingItem` int(11) NOT NULL REFERENCES  `CookingItem`(`id`),
    `dosage` int(11) REFERENCES  `dosages`(`id`),
    `BasicIngredient` varchar(512) REFERENCES  `BasicIngredient`(`name`),
    `preparationIngredient` int(11) REFERENCES  `CookingItem`(`id`),

    PRIMARY KEY (`id`)
    
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

  INSERT INTO IngredientWithDosage (id,CookingItem,dosage,BasicIngredient) VALUES (1,12,1,"pane");
  INSERT INTO IngredientWithDosage (id,CookingItem,dosage,BasicIngredient) VALUES (2,9,1,"farina");
  INSERT INTO IngredientWithDosage (id,CookingItem,dosage,BasicIngredient) VALUES (3,9,2,"acqua");

  INSERT INTO IngredientWithDosage (id,CookingItem,dosage,preparationIngredient) VALUES (4,9,2,21);
  INSERT INTO IngredientWithDosage (id,CookingItem,dosage,preparationIngredient) VALUES (5,9,2,22);


