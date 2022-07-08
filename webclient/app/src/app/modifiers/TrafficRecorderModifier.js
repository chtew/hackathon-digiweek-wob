const entityDefault = {
    externalId: "",
    cityDirection: "",
    neighbor: "",
    specialty: "",
    location: "",
    latitude: "",
    longitude: "",
    id: undefined
};

const entityFields = [
    {
        name: "externalId",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "cityDirection",
        type: "enum",
        regex: null,
        notNull: false,
        enumName: "cityDirection",
        selectList: [
            "in",
            "out"
        ]
    },
    {
        name: "neighbor",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "specialty",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "location",
        type: "string",
        regex: null,
        notNull: false
    },
    {
        name: "latitude",
        type: "bigdecimal",
        regex: null,
        notNull: false
    },
    {
        name: "longitude",
        type: "bigdecimal",
        regex: null,
        notNull: false
    },
    {
        name: "trafficRecord",
        type: "OneToMany",
        regex: null,
        selectList: [],
        display: [
            "recordDate",
            "weekday",
            "weekend",
            "holiday",
            "vacationLowerSaxony",
            "plantHoliday",
            "carCount"
        ],
        selectedIds: []
    }
];

const trafficRecorderOverviewFields = [
    {name: "externalId", type: "string", regex: null},
    {name: "cityDirection", type: "enum", regex: null},
    {name: "neighbor", type: "string", regex: null},
    {name: "specialty", type: "string", regex: null},
    {name: "location", type: "string", regex: null},
    {name: "latitude", type: "bigdecimal", regex: null},
    {name: "longitude", type: "bigdecimal", regex: null}
];

export {
    entityDefault,
    entityFields,
    trafficRecorderOverviewFields
};
