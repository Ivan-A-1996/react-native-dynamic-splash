import {IConfigs, ElementTypes} from "./types";

const RNDynamicSplash = require("react-native").NativeModules.RNDynamicSplash;

class DynamicSplash {
    static hide() {
        RNDynamicSplash.hide();
    }

    static async getConfigs(): Promise<IConfigs> {
        return JSON.parse(await RNDynamicSplash.getConfigs());
    }

    static async setConfigs(configs: IConfigs): Promise<void> {
        await RNDynamicSplash.setConfigs(JSON.stringify(configs));
    }

    static async downloadImage(imageUri: string): Promise<void> {
        await RNDynamicSplash.downloadImage(imageUri);
    }

    static async deleteFiles(): Promise<void> {
        await RNDynamicSplash.deleteFiles();
    }
}

export {DynamicSplash, ElementTypes, IConfigs};
