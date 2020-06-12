var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { ElementTypes } from "./types";
const RNDynamicSplash = require("react-native").NativeModules.RNDynamicSplash;
class DynamicSplash {
    static hide() {
        RNDynamicSplash.hide();
    }
    static getConfigs() {
        return __awaiter(this, void 0, void 0, function* () {
            return JSON.parse(yield RNDynamicSplash.getConfigs());
        });
    }
    static setConfigs(configs) {
        return __awaiter(this, void 0, void 0, function* () {
            yield RNDynamicSplash.setConfigs(JSON.stringify(configs));
        });
    }
    static downloadImage(imageUri) {
        return __awaiter(this, void 0, void 0, function* () {
            yield RNDynamicSplash.downloadImage(imageUri);
        });
    }
    static deleteFiles() {
        return __awaiter(this, void 0, void 0, function* () {
            yield RNDynamicSplash.deleteFiles();
        });
    }
}
export { DynamicSplash, ElementTypes };
