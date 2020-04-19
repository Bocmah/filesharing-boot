require('./bootstrap');
import '../css/app.scss';
import RegistrationForm from "./modules/RegistrationForm";
import LoginForm from "./modules/LoginForm";
import DropBoxOverlay from "./modules/DropBoxOverlay";
import DetailsTable from "./modules/DetailsTable";
import CommentForm from "./modules/CommentForm";

const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

$.ajaxSetup({
    headers: {
        [csrfHeader]: csrfToken
    }
});

const registrationForm = new RegistrationForm();
const loginForm = new LoginForm();

if ($(".details-table").length) {
    const detailsTable = new DetailsTable();
}

if ($(".comment-form").length) {
    const commentForm = new CommentForm();
}

const isAdvancedUpload = function() {
    const div = document.createElement("div");
    return (('draggable' in div) ||
        ('ondragstart' in div && 'ondrop' in div))
        && 'FormData' in window
        && 'FileReader' in window;
}();

if ($(".dropbox-overlay").length && isAdvancedUpload) {
    const dropBoxOverlay = new DropBoxOverlay();
}

$("#file-input").fileinput({
    theme: "fas",
    uploadUrl: "/upload",
    showUploadedThumbs: false,
    showPreview: false,
    showAjaxErrorDetails: true,
    elErrorContainer: ".file-upload-errors",
    maxFileCount: 1
});

$("#avatar-upload").fileinput({
    theme: "fas",
    uploadUrl: "/avatars",
    dropZoneEnabled: false,
    allowedFileExtensions: ["jpg", "png", "jpeg"],
    maxFileCount: 1
});

$("#avatar-upload").on("fileuploaded", () => {
    location.reload();
});

$("audio").mediaelementplayer({
    alwaysShowControls: true,
    audioVolume: 'horizontal',
    audioHeight: 40,
    audioWidth: "100%"
});

$("video").mediaelementplayer({
    stretching: "fill"
});