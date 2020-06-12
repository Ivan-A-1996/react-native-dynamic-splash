export interface IConfigs {
    currentLang?: string;
    currentCountry?: string;
    currentTheme?: "light" | "night";
    splashesData: SplashData[];
}

export interface ElementData {
    elementId: string;
    type: ElementTypes;
    value: string; //text, url or imageName without extension
}

export interface SplashData {
    showCriteria?: {
        startDate?: string;
        endDate?: string;
        lang?: string;
        country?: string;
        theme?: "light" | "night";
    },
    themeName?: string; //for android only
    layoutName?: string;
    elementsData: ElementData[];
}

export enum ElementTypes {
    Image,
    Text,
}
