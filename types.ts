export interface IConfigs {
    themeResId?: string;
    layoutResId?: string;
    lang?: string;
    data: {
        elementId: string;
        type: ElementTypes;
        values: {
          startDate?: string;
          endDate?: string;
          lang?: string;
          value: string;
        }[];
    }[];
}

export enum ElementTypes {
    Image,
    Text,
}
