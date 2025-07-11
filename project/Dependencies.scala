import sbt.*
import sbt.Keys.scalaVersion

object Dependencies {
  private def COMPILE(modules: ModuleID*): Seq[ModuleID] = deps(None, modules: _*)

  private def PROVIDED(modules: ModuleID*): Seq[ModuleID] = deps(Some(Provided), modules: _*)

  private def TEST(modules: ModuleID*): Seq[ModuleID] = deps(Some(Test), modules: _*)

  private def deps(scope: Option[Configuration], modules: ModuleID*): Seq[ModuleID] = {
    scope.map(s => modules.map(_ % s)).getOrElse(modules)
  }

  private val testcontainersVersion = "1.21.1"
  private val seleniumVersion = "2.53.1"
  private val slf4jVersion = "2.0.17"
  private val scalaTestVersion = "3.2.9"
  private val scalaTestMockitoVersion = "3.2.9.0"
  private val scalaTestSeleniumVersion_scala2 = "3.2.2.0"
  private val scalaTestSeleniumVersion_scala3 = "3.2.9.0"
  private val junitVersion = "4.13.2"
  private val munitVersion = "1.1.1"
  private val mysqlConnectorVersion = "5.1.42"
  private val neo4jConnectorVersion = "4.0.0"
  private val oracleDriverVersion = "21.18.0.0"
  private val cassandraDriverVersion = "4.0.1"
  private val postgresqlDriverVersion = "42.7.7"
  private val kafkaDriverVersion = "2.2.0"
  private val mockitoVersion = "3.12.4"
  private val restAssuredVersion = "4.0.0"
  private val groovyVersion = "2.5.16"
  private val awsV1Version = "1.11.479"
  private val awsV2Version = "2.31.66"
  private val sttpVersion = "3.3.14"
  private val firestoreConnectorVersion = "3.0.11"
  private val bigtableVersion = "2.45.0"
  private val pubsubVersion = "1.139.4"
  private val redisTestcontainersVersion = "2.2.4"
  private val jedisVersion = "6.0.0"
  private val wireMockTestcontainersVersion = "1.0-alpha-13"
  private val milvusSdkVersion = "2.4.1"
  private val quadrantClientVersion = "1.14.0"
  private val yugabyteJdbcVersion = "42.3.5-yb-6"
  private val yugabyteJavaDriverVersion = "4.15.0-yb-2-TESTFIX.0"
  private val opensearchTestcontainersVersion = "1.0.0"

  val allOld = Def.setting(
    PROVIDED(
      "org.scalatest" %% "scalatest" % scalaTestVersion
    )
  )

  val core = Def.setting(
    COMPILE(
      "org.testcontainers" % "testcontainers" % testcontainersVersion
    ) ++ PROVIDED(
      "org.slf4j" % "slf4j-simple" % slf4jVersion
    ) ++ TEST(
      "junit" % "junit" % junitVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion,
      "org.scalatestplus" %% "mockito-3-4" % scalaTestMockitoVersion,
      "org.scalatestplus" %% "selenium-3-141" % (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) => scalaTestSeleniumVersion_scala2
        case _ => scalaTestSeleniumVersion_scala3
      }),
      "org.testcontainers" % "selenium" % testcontainersVersion,
      "org.postgresql" % "postgresql" % postgresqlDriverVersion,
      "org.mockito" % "mockito-core" % mockitoVersion
    )
  )

  val scalatest = Def.setting(
    PROVIDED(
      "org.scalatest" %% "scalatest" % scalaTestVersion
    )
  )

  val munit = Def.setting(
    PROVIDED(
      "org.scalameta" %% "munit" % munitVersion
    )
  )

  val scalatestSelenium = Def.setting(
    COMPILE(
      "org.testcontainers" % "selenium" % testcontainersVersion,
      "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion
    )
  )

  val jdbc = Def.setting(
    COMPILE(
      "org.testcontainers" % "jdbc" % testcontainersVersion
    )
  )

  val moduleMysql = Def.setting(
    COMPILE(
      "org.testcontainers" % "mysql" % testcontainersVersion
    ) ++ TEST(
      "mysql" % "mysql-connector-java" % mysqlConnectorVersion
    )
  )

  val moduleNeo4j = Def.setting(
    COMPILE(
      "org.testcontainers" % "neo4j" % testcontainersVersion
    ) ++ TEST(
      "org.neo4j.driver" % "neo4j-java-driver" % neo4jConnectorVersion
    )
  )
  val modulePostgres = Def.setting(
    COMPILE(
      "org.testcontainers" % "postgresql" % testcontainersVersion
    ) ++ TEST(
      "org.postgresql" % "postgresql" % postgresqlDriverVersion
    )
  )

  val moduleOracle = Def.setting(
    COMPILE(
      "org.testcontainers" % "oracle-xe" % testcontainersVersion
    ) ++ TEST(
      "com.oracle.database.jdbc" % "ojdbc8" % oracleDriverVersion
    )
  )

  val moduleCassandra = Def.setting(
    COMPILE(
      "org.testcontainers" % "cassandra" % testcontainersVersion
    ) ++ TEST(
      "com.datastax.oss" % "java-driver-core" % cassandraDriverVersion
    )
  )

  val moduleKafka = Def.setting(
    COMPILE(
      "org.testcontainers" % "kafka" % testcontainersVersion
    ) ++ TEST(
      "org.apache.kafka" % "kafka-clients" % kafkaDriverVersion
    )
  )

  val moduleVault = Def.setting(
    COMPILE(
      "org.testcontainers" % "vault" % testcontainersVersion
    ) ++ TEST(
      ("io.rest-assured" % "scala-support" % restAssuredVersion)
        .exclude("org.codehaus.groovy", "groovy"),
      "org.codehaus.groovy" % "groovy" % groovyVersion
    )
  )

  val moduleMssqlserver = Def.setting(
    COMPILE(
      "org.testcontainers" % "mssqlserver" % testcontainersVersion
    )
  )

  val moduleClickhouse = Def.setting(
    COMPILE(
      "org.testcontainers" % "clickhouse" % testcontainersVersion
    ) ++ TEST(
      "ru.yandex.clickhouse" % "clickhouse-jdbc" % "0.3.2",
    )
  )

  val moduleCockroachdb = Def.setting(
    COMPILE(
      "org.testcontainers" % "cockroachdb" % testcontainersVersion
    )
  )

  val moduleCouchbase = Def.setting(
    COMPILE(
      "org.testcontainers" % "couchbase" % testcontainersVersion
    )
  )

  val moduleDb2 = Def.setting(
    COMPILE(
      "org.testcontainers" % "db2" % testcontainersVersion
    )
  )

  val moduleDynalite = Def.setting(
    COMPILE(
      "org.testcontainers" % "dynalite" % testcontainersVersion
    ) ++ PROVIDED(
      "com.amazonaws" % "aws-java-sdk-dynamodb" % awsV1Version
    )
  )

  val moduleElasticsearch = Def.setting(
    COMPILE(
      "org.testcontainers" % "elasticsearch" % testcontainersVersion
    )
  )

  val moduleInfluxdb = Def.setting(
    COMPILE(
      "org.testcontainers" % "influxdb" % testcontainersVersion
    ) ++ PROVIDED(
      "org.influxdb" % "influxdb-java" % "2.24"
    )
  )

  val moduleLocalstack = Def.setting(
    COMPILE(
      "org.testcontainers" % "localstack" % testcontainersVersion
    ) ++ PROVIDED(
      "com.amazonaws" % "aws-java-sdk-s3" % awsV1Version,
      "com.amazonaws" % "aws-java-sdk-sqs" % awsV1Version
    )
  )

  val moduleLocalstackV2 = Def.setting(
    COMPILE(
      "org.testcontainers" % "localstack" % testcontainersVersion
    ) ++ PROVIDED(
      "software.amazon.awssdk" % "s3" % awsV2Version,
      "software.amazon.awssdk" % "sqs" % awsV2Version
    )
  )

  val moduleMariadb = Def.setting(
    COMPILE(
      "org.testcontainers" % "mariadb" % testcontainersVersion
    )
  )

  val moduleMilvus = Def.setting(
    COMPILE(
      "org.testcontainers" % "milvus" % testcontainersVersion
    ) ++ TEST(
      "io.milvus" % "milvus-sdk-java" % milvusSdkVersion
    )
  )

  val moduleMockserver = Def.setting(
    COMPILE(
      "org.testcontainers" % "mockserver" % testcontainersVersion
    ) ++ TEST(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
      "org.mock-server" % "mockserver-client-java" % "5.13.2"
    )
  )

  val moduleNginx = Def.setting(
    COMPILE(
      "org.testcontainers" % "nginx" % testcontainersVersion
    )
  )

  val modulePulsar = Def.setting(
    COMPILE(
      "org.testcontainers" % "pulsar" % testcontainersVersion
    )
  )

  val moduleQuadrant = Def.setting(
    COMPILE(
      "org.testcontainers" % "qdrant" % testcontainersVersion
    ) ++ TEST(
      "io.qdrant" % "client" % quadrantClientVersion
    )
  )

  val moduleRabbitmq = Def.setting(
    COMPILE(
      "org.testcontainers" % "rabbitmq" % testcontainersVersion
    ) ++ TEST(
      "org.scalatest" %% "scalatest" % scalaTestVersion
    ) ++ PROVIDED(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion
    )
  )

  val moduleRedis = Def.setting(
    COMPILE(
      "com.redis" % "testcontainers-redis" % redisTestcontainersVersion
    ) ++ TEST(
      "org.scalatest" %% "scalatest" % scalaTestVersion,
      "redis.clients" % "jedis" % jedisVersion
    )
  )

  val moduleToxiproxy = Def.setting(
    COMPILE(
      "org.testcontainers" % "toxiproxy" % testcontainersVersion
    )
  )

  val moduleOrientdb = Def.setting(
    COMPILE(
      "org.testcontainers" % "orientdb" % testcontainersVersion
    )
  )

  val modulePresto = Def.setting(
    COMPILE(
      "org.testcontainers" % "presto" % testcontainersVersion
    )
  )

  val moduleTrino = Def.setting(
    COMPILE(
      "org.testcontainers" % "trino" % testcontainersVersion
    )
  )

  val moduleMongodb = Def.setting(
    COMPILE(
      "org.testcontainers" % "mongodb" % testcontainersVersion
    )
  )

  val moduleSolr = Def.setting(
    COMPILE(
      "org.testcontainers" % "solr" % testcontainersVersion
    )
  )

  val moduleGcloud = Def.setting(
    COMPILE(
      "org.testcontainers" % "gcloud" % testcontainersVersion
    ) ++
      PROVIDED(
        "com.google.cloud" % "google-cloud-firestore" % firestoreConnectorVersion,
        "com.google.cloud" % "google-cloud-bigtable" % bigtableVersion,
        "com.google.cloud" % "google-cloud-pubsub" % pubsubVersion
      )
  )

  val moduleRedpanda = Def.setting(
    COMPILE(
      "org.testcontainers" % "redpanda" % testcontainersVersion
    ) ++ TEST(
      "org.apache.kafka" % "kafka-clients" % kafkaDriverVersion
    )
  )

  val moduleMinIO = Def.setting(
    COMPILE(
      "org.testcontainers" % "minio" % testcontainersVersion
    )
  )

  val moduleWireMock = Def.setting(
    COMPILE(
      "org.wiremock.integrations.testcontainers" % "wiremock-testcontainers-module" % wireMockTestcontainersVersion
    ) ++ TEST(
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion
    )
  )

  val moduleYugabytedb = Def.setting(
    COMPILE(
      "org.testcontainers" % "yugabytedb" % testcontainersVersion
    ) ++ TEST(
      "com.yugabyte" % "jdbc-yugabytedb" % yugabyteJdbcVersion,
      "com.yugabyte" % "java-driver-core" % yugabyteJavaDriverVersion
    )
  )

  val moduleOpensearch = Def.setting(
    COMPILE(
      "org.opensearch" % "opensearch-testcontainers" % opensearchTestcontainersVersion
    ) ++ TEST(
      "org.scalatest" %% "scalatest" % scalaTestVersion,
      "com.softwaremill.sttp.client3" %% "core" % sttpVersion
    )
  )

  val moduleK3s = Def.setting(
    COMPILE(
      "org.testcontainers" % "k3s" % testcontainersVersion
    )
  )
}
