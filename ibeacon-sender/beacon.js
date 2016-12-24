/* beacon.js
 *
 * Requirement
 *  node v0.10.24
 *  bleacon (`npm install -g bleacon`)
 *
 * Description
 *  OS X 10.11上で動作確認済です．
 *  受信はスマホアプリをダウンロードして確認してください...
 */

var Bleacon = require('bleacon');

var uuid = 'e2c56db5dffb48d2b060d0f5a71096e0';
var major = 0; // 0 - 65535
var minor = 0; // 0 - 65535
var measuredPower = -59; // -128 - 127 (measured RSSI at 1 meter)

Bleacon.startAdvertising(uuid, major, minor, measuredPower);
