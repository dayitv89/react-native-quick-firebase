import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  sendOTP(phone: string): Promise<string>;
  validateOTP(otp: string): Promise<string>;
  signOut(): Promise<boolean>;
  setAnalyticsEnabled(enabled: boolean): void;
  setUserId(id: string): void;
  setUserProperty(name: string, property: string): void;
  logEvent(name: string, params: Object): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RNQuickFirebase');
