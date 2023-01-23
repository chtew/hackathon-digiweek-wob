CREATE SEQUENCE IF NOT EXISTS "trafficrecorder_id_seq";

CREATE TABLE "trafficrecorder"
(
    "externalid" VARCHAR(255),
    "citydirection" VARCHAR(255),
    "neighbor" VARCHAR(255),
    "specialty" VARCHAR(255),
    "location" VARCHAR(255),
    "latitude" DECIMAL(19,2),
    "longitude" DECIMAL(19,2),
    "id" BIGINT NOT NULL DEFAULT nextval('trafficrecorder_id_seq'),
    CONSTRAINT "trafficrecorder_pkey" PRIMARY KEY ("id")
);

CREATE SEQUENCE IF NOT EXISTS "trafficrecord_id_seq";

CREATE TABLE "trafficrecord"
(
    "recorddate" DATE,
    "weekday" INTEGER,
    "holiday" BOOLEAN,
    "vacationlowersaxony" BOOLEAN,
    "weekend" BOOLEAN,
    "plantholiday" BOOLEAN,
    "carcount" INTEGER,
    "trafficrecorder_id" BIGINT,
    "id" BIGINT NOT NULL DEFAULT nextval('trafficrecord_id_seq'),
    CONSTRAINT "trafficrecord_pkey" PRIMARY KEY ("id")
);

ALTER TABLE "trafficrecord"
    ADD CONSTRAINT "fk_trafficrecord_trafficrecorder"
    FOREIGN KEY ("trafficrecorder_id")
    REFERENCES "trafficrecorder" ("id");

