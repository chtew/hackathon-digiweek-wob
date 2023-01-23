CREATE SEQUENCE IF NOT EXISTS "trafficrecorder_id_seq";

CREATE TABLE "trafficrecorder"
(
    "externalid" VARCHAR(255) DEFAULT NULL,
    "citydirection" VARCHAR(255) DEFAULT NULL,
    "neighbor" VARCHAR(255) DEFAULT NULL,
    "specialty" VARCHAR(255) DEFAULT NULL,
    "location" VARCHAR(255) DEFAULT NULL,
    "latitude" DECIMAL(22,19) DEFAULT NULL,
    "longitude" DECIMAL(22,19) DEFAULT NULL,
    "id" BIGINT NOT NULL DEFAULT nextval('trafficrecorder_id_seq'),
    CONSTRAINT "trafficrecorder_pkey" PRIMARY KEY ("id")
);

CREATE SEQUENCE IF NOT EXISTS "trafficrecord_id_seq";

CREATE TABLE "trafficrecord"
(
    "recorddate" DATE DEFAULT NULL,
    "weekday" INTEGER DEFAULT NULL,
    "holiday" BOOLEAN DEFAULT NULL,
    "vacationlowersaxony" BOOLEAN DEFAULT NULL,
    "weekend" BOOLEAN DEFAULT NULL,
    "plantholiday" BOOLEAN DEFAULT NULL,
    "carcount" INTEGER DEFAULT NULL,
    "trafficrecorder_id" BIGINT DEFAULT NULL,
    "id" BIGINT NOT NULL DEFAULT nextval('trafficrecord_id_seq'),
    CONSTRAINT "trafficrecord_pkey" PRIMARY KEY ("id")
);

ALTER TABLE "trafficrecord"
    ADD CONSTRAINT "fk_trafficrecord_trafficrecorder"
    FOREIGN KEY ("trafficrecorder_id")
    REFERENCES "trafficrecorder" ("id");

