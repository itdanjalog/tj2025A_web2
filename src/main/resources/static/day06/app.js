// math.js import
import sum from './math.js';
console.log("sum(2,3) =", sum(2, 3));


// config.js import
import settings from './config.js';
console.log("API URL:", settings.apiUrl);

// util.js import (default + named)
import greet, { PI, E } from './util.js';
console.log(greet("ITdanja"));
console.log("PI =", PI);
console.log("E  =", E);
