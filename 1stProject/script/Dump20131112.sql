CREATE SCHEMA sdm_ac;
USE  sdm_ac ;
DROP TABLE IF EXISTS sdm_ac.company ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.company  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   location  varchar(45) NOT NULL,
   contact  varchar(45) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  company 
--

LOCK TABLES  sdm_ac.company  WRITE;
/*!40000 ALTER TABLE  company  DISABLE KEYS */;
/*!40000 ALTER TABLE  company  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  doctor 
--

DROP TABLE IF EXISTS  sdm_ac.doctor ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.doctor  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   surname  varchar(45) NOT NULL,
   department  varchar(45) NOT NULL,
   id_hospital  varchar(45) DEFAULT NULL,
   id_healthclub  varchar(45) DEFAULT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  doctor 
--

LOCK TABLES  sdm_ac.doctor  WRITE;
/*!40000 ALTER TABLE  doctor  DISABLE KEYS */;
/*!40000 ALTER TABLE  doctor  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  employer 
--

DROP TABLE IF EXISTS  sdm_ac.employer ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.employer  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   surname  varchar(45) NOT NULL,
   id_company  int(11) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  employer 
--

LOCK TABLES  sdm_ac.employer  WRITE;
/*!40000 ALTER TABLE  employer  DISABLE KEYS */;
/*!40000 ALTER TABLE  employer  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  health_club 
--

DROP TABLE IF EXISTS  sdm_ac.health_club ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.health_club  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   location  varchar(45) NOT NULL,
   contact  varchar(45) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  health_club 
--

LOCK TABLES  sdm_ac.health_club  WRITE;
/*!40000 ALTER TABLE  health_club  DISABLE KEYS */;
/*!40000 ALTER TABLE  health_club  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  hospital 
--

DROP TABLE IF EXISTS  sdm_ac.hospital ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.hospital  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   location  varchar(45) NOT NULL,
   contact  varchar(45) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  hospital 
--

LOCK TABLES  sdm_ac.hospital  WRITE;
/*!40000 ALTER TABLE  hospital  DISABLE KEYS */;
/*!40000 ALTER TABLE  hospital  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  insurance 
--

DROP TABLE IF EXISTS  sdm_ac.insurance ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.insurance  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   location  varchar(45) NOT NULL,
   contact  varchar(45) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  insurance 
--

LOCK TABLES  sdm_ac.insurance  WRITE;
/*!40000 ALTER TABLE  insurance  DISABLE KEYS */;
/*!40000 ALTER TABLE  insurance  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  patient_health_data 
--

DROP TABLE IF EXISTS  sdm_ac.patient_health_data ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.patient_health_data  (
   id  int(11) NOT NULL,
   id_patient  int(11) NOT NULL,
   id_hospital  int(11) NOT NULL,
   id_doctor  int(11) NOT NULL,
   disease  varchar(255) NOT NULL,
   surgery  binary(1) NOT NULL,
   date  date NOT NULL,
   treatment  varchar(255) NOT NULL,
   drug_prescription  varchar(255) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  patient_health_data 
--

LOCK TABLES  sdm_ac.patient_health_data  WRITE;
/*!40000 ALTER TABLE  patient_health_data  DISABLE KEYS */;
/*!40000 ALTER TABLE  patient_health_data  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  personal_health_data 
--

DROP TABLE IF EXISTS  sdm_ac.personal_health_data ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.personal_health_data  (
   id  int(11) NOT NULL,
   name  varchar(45) NOT NULL,
   surname  varchar(45) NOT NULL,
   birthdate  date NOT NULL,
   gender  varchar(1) NOT NULL,
   address  varchar(45) NOT NULL,
   id_doctor  int(11) NOT NULL,
   id_employer  int(11) DEFAULT NULL,
   id_insurance  int(11) NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  personal_health_data 
--

LOCK TABLES  sdm_ac.personal_health_data  WRITE;
/*!40000 ALTER TABLE  personal_health_data  DISABLE KEYS */;
/*!40000 ALTER TABLE  personal_health_data  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table  training_related_health_data 
--

DROP TABLE IF EXISTS  sdm_ac.training_related_health_data ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  sdm_ac.training_related_health_data  (
   id  int(11) NOT NULL,
   id_patient  varchar(45) NOT NULL,
   id_health_club  varchar(45) NOT NULL,
   disease  varchar(45) NOT NULL,
   surgery  binary(1) NOT NULL,
   training  varchar(255) NOT NULL,
   period_start  date NOT NULL,
   period_end  date NOT NULL,
  PRIMARY KEY ( id ),
  UNIQUE KEY  id_UNIQUE  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table  training_related_health_data 
--

LOCK TABLES  sdm_ac.training_related_health_data  WRITE;
/*!40000 ALTER TABLE  training_related_health_data  DISABLE KEYS */;
/*!40000 ALTER TABLE  training_related_health_data  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-12 10:55:33
