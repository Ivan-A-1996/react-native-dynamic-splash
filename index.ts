import {IConfigs, ElementTypes} from "./types";

const RNDynamicSplash = require("react-native").RNDynamicSplash;

class DynamicSplash {
    hide() {
        RNDynamicSplash.hide();
    }

    async getConfigs(): Promise<IConfigs> {
        return JSON.parse(await RNDynamicSplash.getConfigs());
    }

    async setConfigs(configs: IConfigs): Promise<void> {
        await RNDynamicSplash.setConfigs(JSON.stringify(configs));
    }
}

export {DynamicSplash, ElementTypes, IConfigs};
