/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';

const testFunction = async () => {
  console.log('Function called from REACT NATIVE');
};

AppRegistry.registerHeadlessTask('HeadlessTask', () => testFunction);
AppRegistry.registerComponent(appName, () => App);
