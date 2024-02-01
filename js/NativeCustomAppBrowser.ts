import { TurboModule, TurboModuleRegistry } from "react-native";

export type CloseType = "cancel" | "dismiss";

export interface Spec extends TurboModule {
  open(url: string, options?: {}): Promise<CloseType>;
  close(): void;
}

export default TurboModuleRegistry.get<Spec>("RTNCustomAppBrowser") as Spec | null;
