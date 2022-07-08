const entityDefault = {
    recordDate: "",
    weekday: "",
    weekend: "",
    holiday: "",
    vacationLowerSaxony: "",
    plantHoliday: "",
    carCount: "",
    id: undefined
};

const entityFields = [
    {
        name: "recordDate",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "weekday",
        type: "integer",
        regex: null,
        notNull: false
    },
    {
        name: "weekend",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "holiday",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "vacationLowerSaxony",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "plantHoliday",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "carCount",
        type: "integer",
        regex: null,
        notNull: false
    },
];

const trafficRecordOverviewFields = [
    {name: "recordDate", type: "string", regex: null},
    {name: "weekday", type: "integer", regex: null},
    {name: "weekend", type: "string", regex: null},
    {name: "holiday", type: "string", regex: null},
    {name: "vacationLowerSaxony", type: "string", regex: null},
    {name: "plantHoliday", type: "string", regex: null},
    {name: "carCount", type: "integer", regex: null}
];

export {
    entityDefault,
    entityFields,
    trafficRecordOverviewFields
};
