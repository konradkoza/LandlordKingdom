declare module '@loadable/component' {
  import { ComponentType } from 'react';

  export interface LoadableComponent<T = {}> extends ComponentType<T> {
    preload: () => void;
    load: () => Promise<any>;
  }

  export interface LoadableComponentMethods<T = {}> {
    preload: () => void;
    load: () => Promise<any>;
  }

  export default function loadable<T = {}>(
    loadFn: () => Promise<{ default: ComponentType<T> }>,
    options?: any
  ): ComponentType<T>;
}