import { TurboModule, TurboModuleRegistry } from "react-native";

export type CloseType = "cancel" | "dismiss";

export interface Spec extends TurboModule {
  open(url: string): Promise<CloseType>;
  close(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>("RTNCustomAppBrowser") as Spec | null;
