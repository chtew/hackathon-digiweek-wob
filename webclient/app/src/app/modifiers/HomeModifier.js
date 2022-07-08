import {produce} from "immer";

const RegexConfig = {
    prop1: /^[A-Z][a-zA-Z0-9]{1,100}$/
};

const newHome =
    {id: null, prop1: null};

function toDatabase(home) {
    const data = {};
    data.id = home.id;
    data.prop1 = home.prop1;
    return data;
}

function updateHome(home, data) {
    return produce(home, draft => {
        draft.id = data.id;
        draft.prop1 = data.prop1;
        draft.isValid = isValid(draft);
    });
}

function updateProp1(home, prop1) {
    return produce(home, draft => {
        draft.prop1 = prop1;
    });
}

function isValid(data) {
    return RegexConfig.prop1.test(data.prop1);
}

export {
    newHome,
    updateHome,
    updateProp1,
    isValid,
    toDatabase};
