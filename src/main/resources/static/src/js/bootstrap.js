window.Popper = require('popper.js').default;

try {
    window.$ = window.jQuery = require('jquery');

    require('bootstrap');
    require("bootstrap-fileinput/js/fileinput");
    require("bootstrap-fileinput/themes/fas/theme");
    require("mediaelement/build/mediaelement-and-player");
} catch (e) {
    console.error(e);
}